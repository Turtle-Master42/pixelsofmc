package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.item.AtomItem;
import net.turtlemaster42.pixelsofmc.recipe.POMRecipeSerializer;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FusionRecipe extends BaseItemRecipe {
    private final Element element;
    private final CountedIngredient output;
    private final boolean x512;
    private final int protonCount;
    private final int neutronCount;
    public FusionRecipe(ResourceLocation id, CountedIngredient output, Element element, int protonCount, int neutronCount, boolean x512) {
        super(id);
        this.element = element;
        this.protonCount = protonCount;
        this.neutronCount = neutronCount;
        this.x512 = x512;
        this.output = output;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide) return false;
        int protonCount = 0;
        int neutronCount = 0;
        int filledSlotCount = 0;
        
        for (int i = 0; i < 2; i++) {
            if (container.getItem(i).getItem() instanceof AtomItem item) {
                filledSlotCount++;
                ItemStack stack = new ItemStack(item);
                int multiplier = 1;
                if (stack.is(POMtags.Items.ATOM512)) multiplier = 8;

                protonCount += item.getProtonCount() * multiplier;
                neutronCount += item.getNeutronCount() * multiplier;
            }
        }
        return this.protonCount == protonCount && this.neutronCount == neutronCount && filledSlotCount > 1;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer simpleContainer, @NotNull RegistryAccess registryAccess) {
        return output.asItemStack();
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.asItemStack().copy();
    }

    public ItemStack getResultItems(int index) {
        return List.of(output.asItemStack()).get(index);
    }

    public List<CountedIngredient> getOutputs() {
        return List.of(output);
    }

    @Override
    public ItemStack getBaseOutput() {
        return output.asItemStack();
    }

    public int getProtonCount() {return protonCount;}
    public int getNeutronCount() {return neutronCount;}
    public Element getElement() {
        return element;
    }
    public boolean x512() {
        return x512;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.SDS_CONTROLLER.get());
    }

    public static class Type implements RecipeType<FusionRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fusing";
    }

    public static class Serializer implements POMRecipeSerializer<FusionRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull FusionRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            CountedIngredient output = JsonRecipeUtils.CIFromJson(json, "output");
            Element element = Element.fromJson(json);
            boolean x512 = JsonRecipeUtils.booleanFromJson(json, "x512");
            int proton = JsonRecipeUtils.intFromJson(json, "proton");
            int neutron = JsonRecipeUtils.intFromJson(json, "neutron");

            return new FusionRecipe(id, output, element, proton, neutron, x512);
        }

        public FusionRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            CountedIngredient output = CountedIngredient.fromNetwork(buf);
            Element element = Element.fromNetwork(buf);
            boolean x512 = buf.readBoolean();
            int proton = buf.readInt();
            int neutron = buf.readInt();

            return new FusionRecipe(id, output, element, proton, neutron, x512);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull FusionRecipe recipe) {
            recipe.output.toNetwork(buf);
            recipe.element.toNetwork(buf);
            buf.writeBoolean(recipe.x512);
            buf.writeInt(recipe.protonCount);
            buf.writeInt(recipe.neutronCount);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}


