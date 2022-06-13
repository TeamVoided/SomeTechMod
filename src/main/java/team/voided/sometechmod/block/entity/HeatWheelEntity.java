package team.voided.sometechmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.voided.quiltenergy.block.entity.EnergizedBlockEntity;
import team.voided.quiltenergy.energy.EnergyContainer;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.sometechmod.block.BlockRegistry;
import team.voided.sometechmod.block.HeatWheelBlock;
import team.voided.sometechmod.energyunit.STMUnits;

public class HeatWheelEntity extends EnergizedBlockEntity {
	private int ticksTilUpdate = 0;
	private int accumulatedHeat;

	public HeatWheelEntity(BlockPos pos, BlockState state, Direction... energyTransferAllowed) {
		this(BlockRegistry.HEAT_WHEEL_ENTITY_TYPE, pos, state, new EnergyContainer(STMUnits.ATMOSPHERIC_EXTRACT, 500_000), energyTransferAllowed);
	}

	public HeatWheelEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, container, energyTransferAllowed);
		getContainer().setReceivability(true);
	}

	public HeatWheelEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, double maxCapacity, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, unit, maxCapacity, energyTransferAllowed);
		getContainer().setReceivability(true);
	}

	public static HeatWheelEntity create(BlockPos pos, BlockState state) {
		Direction direction = Direction.SOUTH;

		switch (state.getValue(HeatWheelBlock.FACING)) {
			case SOUTH -> direction = Direction.NORTH;
			case EAST -> direction = Direction.WEST;
			case WEST -> direction = Direction.EAST;
		}

		return new HeatWheelEntity(pos, state, direction);
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, HeatWheelEntity blockEntity) {
		int heat = 0;
		if (blockEntity.ticksTilUpdate == 0) {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						BlockState otherState = level.getBlockState(blockPos.offset(x, y, z));

						if (otherState.getBlock().equals(Blocks.LAVA)) {
							heat += 2;
						} else if (level.isWaterAt(blockPos.offset(x, y, z))) {
							heat -= 1;
						}
					}
				}
			}

			blockEntity.accumulatedHeat = heat;
			blockEntity.ticksTilUpdate = 20;
		} else {
			blockEntity.ticksTilUpdate--;
		}

		if (blockEntity.accumulatedHeat <= 0) return;

		level.addParticle(DustParticleOptions.REDSTONE, blockPos.getX()+Mth.nextFloat(level.random, 0, 1), blockPos.getY()+Mth.nextFloat(level.random, 0, 1), blockPos.getZ()+Mth.nextFloat(level.random, 0, 1), 0, 0.2, 0);
		blockEntity.getContainer().addEnergy(2 * blockEntity.accumulatedHeat);
	}

	public int getAccumulatedHeat() {
		return accumulatedHeat;
	}
}
