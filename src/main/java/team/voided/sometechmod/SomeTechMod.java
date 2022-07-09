package team.voided.sometechmod;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.voided.sometechmod.block.BlockRegistry;
import team.voided.sometechmod.item.ItemRegistry;
import team.voided.sometechmod.menu.MenuRegistry;
import team.voided.sometechmod.recipe.ConstructingRecipe;

public class SomeTechMod implements ModInitializer {
	public static final String MOD_ID = "some_tech_mod";

	public static final Logger LOGGER = LoggerFactory.getLogger("Some Tech Mod");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		BlockRegistry.register();
		ItemRegistry.register();
		MenuRegistry.register();

		Registry.register(Registry.RECIPE_TYPE, ConstructingRecipe.Type.LOCATION, ConstructingRecipe.Type.INSTANCE);
		Registry.register(Registry.RECIPE_SERIALIZER, ConstructingRecipe.Type.LOCATION, ConstructingRecipe.Serializer.INSTANCE);
	}

	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static ResourceLocation fromString(String loc) {
		if (loc.contains(":")) {
			String[] split = loc.split(":");
			return new ResourceLocation(split[0], split[1]);
		}

		return new ResourceLocation(loc);
	}
}
