package net.mcreator.pixelsofmc.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.mcreator.pixelsofmc.PixelsOfMcMod;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.util.GsonHelper;


public class MolucularSplitterRecipe implements Recipe<SimpleContainer>{
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public MolucularSplitterRecipe(ResourceLocation id, ItemStack output,
    								NonNullList<Ingredient> recipeItems) {
    		this.id = id;
    		this.output = output;
    		this.recipeItems = recipeItems;
    }

	@Override
	public boolean matches(SimpleContainer pContainer, Level pLevel) {
		// TODO Auto-generated method stub
		return recipeItems.get(0).test(pContainer.getItem(1));
	}

	@Override
	public ItemStack assemble(SimpleContainer pContainer) {
		// TODO Auto-generated method stub
		return output;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		// TODO Auto-generated method stub
		return output.copy();
	}

	@Override
	public ResourceLocation getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		// TODO Auto-generated method stub
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		// TODO Auto-generated method stub
		return Type.INSTANCE;
	}
	
	
	public static class Type implements RecipeType<MolucularSplitterRecipe> {
	        private Type() { }
	        public static final Type INSTANCE = new Type();
	        public static final String ID = "splitter_recipe";
	    }
	 
	 
	 
	public static class Serializer implements RecipeSerializer<MolucularSplitterRecipe> {
	        public static final Serializer INSTANCE = new Serializer();
	        public static final ResourceLocation ID =
	                new ResourceLocation(PixelsOfMcMod.MOD_ID,"splitter_recipe");

	        @Override
	        public MolucularSplitterRecipe fromJson(ResourceLocation id, JsonObject json) {
	            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

	            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
	            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

	            for (int i = 0; i < inputs.size(); i++) {
	                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
	            }

	            return new MolucularSplitterRecipe(id, output, inputs);
	        }

	        @Override
	        public MolucularSplitterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
	            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

	            for (int i = 0; i < inputs.size(); i++) {
	                inputs.set(i, Ingredient.fromNetwork(buf));
	            }

	            ItemStack output = buf.readItem();
	            return new MolucularSplitterRecipe(id, output, inputs);
	        }

	        @Override
	        public void toNetwork(FriendlyByteBuf buf, MolucularSplitterRecipe recipe) {
	            buf.writeInt(recipe.getIngredients().size());
	            for (Ingredient ing : recipe.getIngredients()) {
	                ing.toNetwork(buf);
	            }
	            buf.writeItemStack(recipe.getResultItem(), false);
	        }

	        @Override
	        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
	            return INSTANCE;
	        }

	        @Nullable
	        @Override
	        public ResourceLocation getRegistryName() {
	            return ID;
	        }

	        @Override
	        public Class<RecipeSerializer<?>> getRegistryType() {
	            return Serializer.castClass(RecipeSerializer.class);
	        }

	        @SuppressWarnings("unchecked") // Need this wrapper, because generics
	        private static <G> Class<G> castClass(Class<?> cls) {
	            return (Class<G>)cls;
	        }
	    }

}
