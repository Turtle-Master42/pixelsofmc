package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.item.FuelCellItem;
import net.turtlemaster42.pixelsofmc.recipe.machines.BaseItemRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DecayRecipe extends BaseItemRecipe {
    private final FuelCellItem fuelCell;

    public DecayRecipe(ResourceLocation id, FuelCellItem fuelCell) {
        super(id);
        this.fuelCell = fuelCell;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, @NotNull Level pLevel) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return new ItemStack(fuelCell.getRemainder());
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return new ItemStack(fuelCell.getRemainder());
    }

    public FuelCellItem getFuelCell() {
        return fuelCell;
    }

    @Override
    public ItemStack getBaseOutput() {
        return new ItemStack(fuelCell.getRemainder());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return DecayRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return DecayRecipe.Type.INSTANCE;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMitems.PLUTONIUM_FUEL_CELL.get());
    }

    public static class Type implements RecipeType<DecayRecipe> {
        public static final DecayRecipe.Type INSTANCE = new DecayRecipe.Type();
        public static final String ID = "decay";
    }

    public static class Serializer implements POMRecipeSerializer<DecayRecipe> {
        public static final DecayRecipe.Serializer INSTANCE = new DecayRecipe.Serializer();

        public @NotNull DecayRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            Item fuelCell = GsonHelper.getAsItem(json, "decay_item");
            return new DecayRecipe(id, (FuelCellItem) fuelCell);
        }

        public DecayRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            return new DecayRecipe(id, (FuelCellItem) buf.readItem().getItem());
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull DecayRecipe recipe) {
            buf.writeItem(new ItemStack(recipe.fuelCell));
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(DecayRecipe.Type.ID);}
    }
}
