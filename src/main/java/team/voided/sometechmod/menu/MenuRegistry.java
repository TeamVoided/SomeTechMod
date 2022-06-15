package team.voided.sometechmod.menu;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import team.voided.sometechmod.SomeTechMod;

public final class MenuRegistry {

	public static final ExtendedScreenHandlerType<CrudeConstructingMenu> CRUDE_CONSTRUCTING_MENU_TYPE = new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new CrudeConstructingMenu(syncId, inventory));

	public static void register() {
		Registry.register(Registry.MENU, SomeTechMod.modLoc("crude_constructing_menu"), CRUDE_CONSTRUCTING_MENU_TYPE);
	}
}
