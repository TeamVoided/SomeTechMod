package team.voided.sometechmod.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import team.voided.sometechmod.container.CrudeConstructingContainer;

public class CrudeConstructingMenu extends RecipeBookMenu<CrudeConstructingContainer> {
	private final CrudeConstructingContainer constructingSlots;
	private final ContainerLevelAccess cla;
	private final Player player;

	public CrudeConstructingMenu(int i, Inventory inventory) {
		this(i, inventory, ContainerLevelAccess.NULL);
	}

	public CrudeConstructingMenu(int i, Inventory inventory, ContainerLevelAccess cla) {
		super(MenuRegistry.CRUDE_CONSTRUCTING_MENU_TYPE, i);
		this.constructingSlots = new CrudeConstructingContainer(this, 3, 1);
		this.cla = cla;
		this.player = inventory.player;

		addSlot(new Slot(constructingSlots, 0, 32, 26));
		addSlot(new Slot(constructingSlots, 1, 80, 26));
		addSlot(new ResultSlot(player, constructingSlots, constructingSlots, 2, 128, 26));
		addPlayerSlots(inventory, 0, 0);
	}

	private void addPlayerSlots(Inventory inventory, int k, int j) {
		for(j = 0; j < 3; ++j) {
			for(k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
			}
		}

		for(j = 0; j < 9; ++j) {
			this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();
			if (index == 0) {
				this.cla.execute((world, pos) -> {
					itemStack2.getItem().onCraftedBy(itemStack2, world, player);
				});
				if (!this.moveItemStackTo(itemStack2, 10, 46, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemStack2, itemStack);
			} else if (index >= 10 && index < 46) {
				if (!this.moveItemStackTo(itemStack2, 1, 10, false)) {
					if (index < 37) {
						if (!this.moveItemStackTo(itemStack2, 37, 46, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.moveItemStackTo(itemStack2, 10, 37, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(itemStack2, 10, 46, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemStack2);
			if (index == 0) {
				player.drop(itemStack2, false);
			}
		}

		return itemStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return constructingSlots.stillValid(player);
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents finder) {
		constructingSlots.fillStackedContents(finder);
	}

	@Override
	public void clearCraftingContent() {
		constructingSlots.clearContent();
	}

	@Override
	public boolean recipeMatches(Recipe<? super CrudeConstructingContainer> recipe) {
		return cla.evaluate((level, pos) -> recipe.matches(constructingSlots, level), false);
	}

	@Override
	public int getResultSlotIndex() {
		return 2;
	}

	@Override
	public int getGridWidth() {
		return 3;
	}

	@Override
	public int getGridHeight() {
		return 1;
	}

	@Override
	public int getSize() {
		return 3;
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return RecipeBookType.CRAFTING;
	}

	@Override
	public boolean shouldMoveToInventory(int index) {
		return index != getResultSlotIndex();
	}
}
