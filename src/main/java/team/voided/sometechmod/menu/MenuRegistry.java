package team.voided.sometechmod.menu;

import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import team.voided.sometechmod.SomeTechMod;

public final class MenuRegistry {
	public static final MenuType<ConstructingMenu> CONSTRUCTING_MENU_TYPE = new MenuType<>(ConstructingMenu::new);

	public static void register() {
		Registry.register(Registry.MENU, SomeTechMod.modLoc("crude_constructing_menu"), CONSTRUCTING_MENU_TYPE);
	}
}
