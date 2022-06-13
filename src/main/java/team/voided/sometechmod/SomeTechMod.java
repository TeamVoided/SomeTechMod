package team.voided.sometechmod;

import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.voided.sometechmod.block.BlockRegistry;

public class SomeTechMod implements ModInitializer {
	public static final String MOD_ID = "some_tech_mod";

	public static final Logger LOGGER = LoggerFactory.getLogger("Some Tech Mod");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		BlockRegistry.register();
	}

	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
