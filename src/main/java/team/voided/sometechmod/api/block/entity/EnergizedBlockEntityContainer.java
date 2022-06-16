package team.voided.sometechmod.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.voided.quiltenergy.block.entity.EnergizedBlockEntity;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;

public class EnergizedBlockEntityContainer extends EnergizedBlockEntity implements Container {
	private final NonNullList<ItemStack> inv = NonNullList.of(ItemStack.EMPTY);

	public EnergizedBlockEntityContainer(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, container, energyTransferAllowed);
	}

	public EnergizedBlockEntityContainer(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, double maxCapacity, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, unit, maxCapacity, energyTransferAllowed);
	}

	@Override
	public int getContainerSize() {
		return inv.size();
	}

	@Override
	public boolean isEmpty() {
		return inv.isEmpty();
	}

	@Override
	public ItemStack getItem(int slot) {
		return inv.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		inv.get(slot).shrink(amount);
		return inv.get(slot);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return inv.set(slot, ItemStack.EMPTY);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inv.set(slot, stack);
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {

	}
}
