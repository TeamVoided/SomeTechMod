package team.voided.sometechmod.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CrudeConstructingMenuConstructor implements MenuConstructor {
	private final Level level;
	private final BlockPos pos;

	public CrudeConstructingMenuConstructor(Level level, BlockPos pos) {
		this.level = level;
		this.pos = pos;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new CrudeConstructingMenu(i, inventory, ContainerLevelAccess.create(level, pos));
	}
}
