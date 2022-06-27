package team.voided.sometechmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.block.entity.EnergizedBlockEntity;
import team.voided.quiltenergy.energy.EnergyContainer;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.EnergyUnits;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.sometechmod.block.BlockRegistry;
import team.voided.sometechmod.menu.CrudeConstructingMenu;

public class CrudeConstructorEntity extends EnergizedBlockEntity implements MenuProvider {
	private final Container inv = new SimpleContainer(3);

	public CrudeConstructorEntity(BlockPos pos, BlockState state, Direction... energyTransferAllowed) {
		this(BlockRegistry.CRUDE_CONSTRUCTOR_ENTITY_TYPE, pos, state, new EnergyContainer(EnergyUnits.QUILT_ENERGY, 500_000), energyTransferAllowed);
	}

	public CrudeConstructorEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, container, energyTransferAllowed);
	}

	public CrudeConstructorEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, double maxCapacity, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, unit, maxCapacity, energyTransferAllowed);
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, CrudeConstructorEntity blockEntity) {

	}

	public static CrudeConstructorEntity create(BlockPos pos, BlockState state) {
		return new CrudeConstructorEntity(pos, state);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new CrudeConstructingMenu(i, inv, inventory, ContainerLevelAccess.create(level, worldPosition));
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("container.crude_constructor.title");
	}

	public Container getInv() {
		return inv;
	}
}
