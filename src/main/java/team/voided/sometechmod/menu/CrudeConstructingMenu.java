package team.voided.sometechmod.menu;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import team.voided.sometechmod.container.CrudeConstructingContainer;
import team.voided.sometechmod.recipe.CrudeConstructingRecipe;

public class CrudeConstructingMenu extends RecipeBookMenu<Container> {
	private final Container constructingSlots;
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
		addSlot(new ConstructedItemSlot(player, constructingSlots, constructingSlots, 2, 128, 26));
		addPlayerSlots(inventory, 0, 0);
	}

	public CrudeConstructingMenu(int i, Container preFilled, Inventory inventory, ContainerLevelAccess cla) {
		super(MenuRegistry.CRUDE_CONSTRUCTING_MENU_TYPE, i);
		this.constructingSlots = preFilled;

		this.cla = cla;
		this.player = inventory.player;

		addSlot(new Slot(constructingSlots, 0, 32, 26));
		addSlot(new Slot(constructingSlots, 1, 80, 26));
		addSlot(new ConstructedItemSlot(player, constructingSlots, constructingSlots, 2, 128, 26));
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
		final NonNullList<Slot> slots = this.slots;
		final Slot clickedSlot = slots.get(index);
		if (!clickedSlot.hasItem()) return ItemStack.EMPTY;

		final var clickedStack = clickedSlot.getItem();

		if (index < 3) {
			if (!moveItemStackTo(clickedStack, 3, slots.size(), false)) return ItemStack.EMPTY;
		} else {
			if (!moveItemStackTo(clickedStack, 0, 3, false)) return ItemStack.EMPTY;
		}

		if (clickedStack.isEmpty()) {
			clickedSlot.set(ItemStack.EMPTY);
		} else {
			clickedSlot.setChanged();
		}

		getSlot(index).onTake(player, clickedStack);

		return clickedStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return constructingSlots.stillValid(player);
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents finder) {
		finder.accountSimpleStack(constructingSlots.getItem(0));
		finder.accountSimpleStack(constructingSlots.getItem(1));
		finder.accountSimpleStack(constructingSlots.getItem(2));
	}

	@Override
	public void clearCraftingContent() {
		constructingSlots.clearContent();
	}

	@Override
	public boolean recipeMatches(Recipe<? super Container> recipe) {
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

	public static class ConstructedItemSlot extends Slot {
		private final Container craftSlots;
		private final Player player;
		private int removeCount;

		public ConstructedItemSlot(Player player, Container craftingContainer, Container container, int i, int j, int k) {
			super(container, i, j, k);
			this.player = player;
			this.craftSlots = craftingContainer;
		}

		public boolean mayPlace(ItemStack stack) {
			return false;
		}

		public ItemStack remove(int amount) {
			if (this.hasItem()) {
				this.removeCount += Math.min(amount, this.getItem().getCount());
			}

			return super.remove(amount);
		}

		protected void onQuickCraft(ItemStack stack, int amount) {
			this.removeCount += amount;
			this.checkTakeAchievements(stack);
		}

		protected void onSwapCraft(int amount) {
			this.removeCount += amount;
		}

		protected void checkTakeAchievements(ItemStack stack) {
			if (this.removeCount > 0) {
				stack.onCraftedBy(this.player.level, this.player, this.removeCount);
			}

			if (this.container instanceof RecipeHolder) {
				((RecipeHolder)this.container).awardUsedRecipes(this.player);
			}

			this.removeCount = 0;
		}

		public void onTake(Player player, ItemStack stack) {
			this.checkTakeAchievements(stack);
			NonNullList<ItemStack> nonNullList = player.level.getRecipeManager().getRemainingItemsFor(CrudeConstructingRecipe.Type.INSTANCE, this.craftSlots, player.level);

			for(int i = 0; i < nonNullList.size(); ++i) {
				ItemStack itemStack = this.craftSlots.getItem(i);
				ItemStack itemStack2 = nonNullList.get(i);
				if (!itemStack.isEmpty()) {
					this.craftSlots.removeItem(i, 1);
					itemStack = this.craftSlots.getItem(i);
				}

				if (!itemStack2.isEmpty()) {
					if (itemStack.isEmpty()) {
						this.craftSlots.setItem(i, itemStack2);
					} else if (ItemStack.isSame(itemStack, itemStack2) && ItemStack.tagMatches(itemStack, itemStack2)) {
						itemStack2.grow(itemStack.getCount());
						this.craftSlots.setItem(i, itemStack2);
					} else if (!this.player.getInventory().add(itemStack2)) {
						this.player.drop(itemStack2, false);
					}
				}
			}
		}
	}
}
