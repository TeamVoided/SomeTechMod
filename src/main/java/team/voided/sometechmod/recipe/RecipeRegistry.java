package team.voided.sometechmod.recipe;

import net.minecraft.core.Registry;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import team.voided.sometechmod.SomeTechMod;

public final class RecipeRegistry {
	public static final RecipeType<CrudeConstructingRecipe> CRUDE_CONSTRUCTING_RECIPE_TYPE = register("crude_constructing");
	public static final RecipeSerializer<CrudeConstructingRecipe> CRUDE_CONSTRUCTING_RECIPE_SERIALIZER = register("crude_constructing", new CrudeConstructingRecipe.Serializer());

	private static <C extends Container, R extends Recipe<C>> RecipeType<R> register(String id) {
		return Registry.register(Registry.RECIPE_TYPE, SomeTechMod.modLoc(id), new RecipeType<R>() {
			@Override
			public String toString() {
				return SomeTechMod.modLoc(id).toString();
			}
		});
	}

	private static <S extends RecipeSerializer<T>, C extends Container, T extends Recipe<C>> S register(String id, S serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, SomeTechMod.modLoc(id), serializer);
	}
}
