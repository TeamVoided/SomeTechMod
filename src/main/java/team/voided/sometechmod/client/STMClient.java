package team.voided.sometechmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import team.voided.sometechmod.menu.CrudeConstructingMenuScreen;
import team.voided.sometechmod.menu.MenuRegistry;
@Environment(EnvType.CLIENT)
public class STMClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		MenuScreens.register(MenuRegistry.CRUDE_CONSTRUCTING_MENU_TYPE, CrudeConstructingMenuScreen::new);
	}
}
