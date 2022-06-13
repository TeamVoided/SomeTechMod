package team.voided.sometechmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.voided.quiltenergy.block.entity.EnergizedBlockEntity;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.IEnergyContainer.Operation;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class WireEntity extends EnergizedBlockEntity {
	private WireState wireState = WireState.NEUTRAL;
	private List<EnergizedBlockEntity> connected = new LinkedList<>();

	public WireEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, container, energyTransferAllowed);
	}

	public WireEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, double maxCapacity, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, unit, maxCapacity, energyTransferAllowed);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, WireEntity self) {
		self.connected = new LinkedList<>();

		BlockEntity b0 = level.getBlockEntity(pos.north());
		BlockEntity b1 = level.getBlockEntity(pos.south());
		BlockEntity b2 = level.getBlockEntity(pos.east());
		BlockEntity b3 = level.getBlockEntity(pos.west());

		connect(pos, pos.north(), b0, self);
		connect(pos, pos.south(), b1, self);
		connect(pos, pos.east(), b2, self);
		connect(pos, pos.west(), b3, self);

		if (self.wireState == WireState.NEUTRAL) {
			self.connected.forEach(entity -> {
				if (entity instanceof WireEntity) self.getContainer().transferEnergy(entity.getContainer(), 2, Operation.GIVE);
			});
			self.connected.forEach(entity -> {
				if (entity instanceof WireEntity) self.getContainer().transferEnergy(entity.getContainer(), 2, Operation.RECEIVE);
			});
			return;
		}
		if (self.wireState == WireState.SENDING) {
			self.connected.forEach(entity -> self.getContainer().transferEnergy(entity.getContainer(), 2, Operation.GIVE));
			return;
		}
		if (self.wireState == WireState.RECEIVING) {
			self.connected.forEach(entity -> self.getContainer().transferEnergy(entity.getContainer(), 2, Operation.RECEIVE));
		}
	}

	public static boolean connect(BlockPos self, BlockPos other, BlockEntity entity, WireEntity selfE) {
		if (!canConnect(self, other, entity)) return false;

		selfE.connected.add((EnergizedBlockEntity) entity);

		return true;
	}

	public static boolean canConnect(BlockPos selfP, BlockPos other, BlockEntity entity) {
		Direction direction = checkDirection(other, selfP);

		if (entity == null) return false;
		if (!(entity instanceof EnergizedBlockEntity)) return false;
		if (direction == null) return false;
		if (direction == Direction.UP) return false;
		if (direction == Direction.DOWN) return false;

		return ((EnergizedBlockEntity) entity).energyTransferAllowedForDirection(direction);
	}

	@Nullable
	public static Direction checkDirection(BlockPos self, BlockPos other) {
		if (self.north().equals(other)) return Direction.NORTH;
		if (self.south().equals(other)) return Direction.SOUTH;
		if (self.east().equals(other)) return Direction.EAST;
		if (self.west().equals(other)) return Direction.WEST;
		if (self.above().equals(other)) return Direction.UP;
		if (self.below().equals(other)) return Direction.DOWN;

		return null;
	}

	public void setWireState(WireState wireState) {
		this.wireState = wireState;
	}

	public WireState getWireState() {
		return wireState;
	}

	public List<EnergizedBlockEntity> getConnected() {
		return connected;
	}

	public enum WireState { NEUTRAL, SENDING, RECEIVING }
}
