package team.voided.sometechmod.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CrudeConstructingContainer implements Container {
	@Override
	public int getContainerSize() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getItem(int slot) {
		return null;
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		return null;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return null;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {

	}

	@Override
	public void setChanged() {

	}

	@Override
	public boolean stillValid(Player player) {
		return false;
	}

	@Override
	public void clearContent() {

	}
}
