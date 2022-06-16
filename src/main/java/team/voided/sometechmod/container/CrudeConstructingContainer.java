package team.voided.sometechmod.container;

import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;

public class CrudeConstructingContainer extends CraftingContainer {

	public CrudeConstructingContainer(AbstractContainerMenu abstractContainerMenu, int width, int height) {
		super(abstractContainerMenu, width, height);
	}

	@Override
	public void stopOpen(Player player) {
		Containers.dropContents(player.level, player, this);
	}
}
