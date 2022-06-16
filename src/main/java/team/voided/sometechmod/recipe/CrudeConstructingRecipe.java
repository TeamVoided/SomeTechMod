package team.voided.sometechmod.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import team.voided.sometechmod.SomeTechMod;

import javax.annotation.ParametersAreNonnullByDefault;

public class CrudeConstructingRecipe implements Recipe<Container> {
	private final ResourceLocation location;
	private final Ingredient slot1;
	private final Ingredient slot2;
	private final ItemStack result;

	public CrudeConstructingRecipe(ResourceLocation location, Ingredient slot1, Ingredient slot2, ItemStack result) {
		this.location = location;
		this.slot1 = slot1;
		this.slot2 = slot2;
		this.result = result;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean matches(Container inventory, Level world) {
		return slot1.test(inventory.getItem(0)) && slot2.test(inventory.getItem(1));
	}

	@Override
	@ParametersAreNonnullByDefault
	public ItemStack assemble(Container inventory) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return result;
	}

	@Override
	public ResourceLocation getId() {
		return location;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ingredients = NonNullList.create();

		ingredients.add(slot1);
		ingredients.add(slot2);

		return ingredients;
	}

	public static class Type implements RecipeType<CrudeConstructingRecipe> {
		private Type() {}

		public static final ResourceLocation LOCATION = SomeTechMod.modLoc("crude_constructing");
		public static final Type INSTANCE = new Type();
	}

	public static class Serializer implements RecipeSerializer<CrudeConstructingRecipe> {
		public static final Serializer INSTANCE = new Serializer();

		@Override
		@ParametersAreNonnullByDefault
		public CrudeConstructingRecipe fromJson(ResourceLocation id, JsonObject json) {
			Ingredient slot1 = Ingredient.fromJson(json.get("slot1"));
			Ingredient slot2 = Ingredient.fromJson(json.get("slot2"));
			ItemStack result = itemStackFromJson(json.getAsJsonObject("result"));

			return new CrudeConstructingRecipe(id, slot1, slot2, result);
		}

		public static ItemStack itemStackFromJson(JsonObject json) {
			Item item = itemFromJson(json);
			if (json.has("data")) {
				throw new JsonParseException("Disallowed data tag found");
			} else {
				int i = GsonHelper.getAsInt(json, "count", 1);
				if (i < 1) {
					throw new JsonSyntaxException("Invalid output count: " + i);
				} else {
					return new ItemStack(item, i);
				}
			}
		}

		public static Item itemFromJson(JsonObject json) {
			String string = GsonHelper.getAsString(json, "item");
			Item item = Registry.ITEM.getOptional(new ResourceLocation(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
			if (item == Items.AIR) {
				throw new JsonSyntaxException("Invalid item: " + string);
			} else {
				return item;
			}
		}

		@Override
		@ParametersAreNonnullByDefault
		public CrudeConstructingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			Ingredient slot1 = Ingredient.fromNetwork(buf);
			Ingredient slot2 = Ingredient.fromNetwork(buf);
			ItemStack result = buf.readItem();

			return new CrudeConstructingRecipe(id, slot1, slot2, result);
		}

		@Override
		@ParametersAreNonnullByDefault
		public void toNetwork(FriendlyByteBuf buf, CrudeConstructingRecipe recipe) {
			recipe.slot1.toNetwork(buf);
			recipe.slot2.toNetwork(buf);
			buf.writeItem(recipe.result);
		}
	}
}
