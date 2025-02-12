package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.item.AtomItem;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FusionRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final Element element;
    private final ItemStack output;
    private final boolean x512;
    private final int protonCount;
    private final int neutronCount;
    private int extraNeutrons;
    public FusionRecipe(ResourceLocation id, Element element, int protonCount, int neutronCount, boolean x512) {
        this.id = id;
        this.element = element;
        this.protonCount = protonCount;
        this.neutronCount = neutronCount;
        this.x512 = x512;
        if (x512) {
            this.output = new ItemStack(element.atom512());
        } else {
            this.output = new ItemStack(element.atom64());
        }
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
        if (this.protonCount == protonCount && this.neutronCount <= neutronCount && filledSlotCount > 1) {
            extraNeutrons = neutronCount - this.neutronCount;
            return true;
        }
        return false;
    }


    public int getOutputCount() {
        return output.getCount();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    public ItemStack getResultItems(int index) {
        return List.of(output, new ItemStack(Element.HYDROGEN.atom64(), extraNeutrons)).get(index);
    }

    public List<CountedIngredient> getOutputs() {
        if (extraNeutrons > 0) {
            return List.of(CountedIngredient.of(output), CountedIngredient.of(extraNeutrons, Element.HYDROGEN.atom64()));
        }
        return List.of(CountedIngredient.of(output));
    }

    @Override
    public ItemStack getBaseOutput() {
        return output;
    }

    public int getOutputsCount(int index) {
        return getOutputs().get(index).count();
    }

    public int getProtonCount() {return protonCount;}
    public int getNeutronCount() {return neutronCount;}
    public ItemStack getInput(int input) {
        return ItemStack.EMPTY;
    }

    public Element getElement() {
        return element;
    }

    public boolean x512() {
        return x512;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
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

    public static class Serializer implements RecipeSerializer<FusionRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"fusing");

        public @NotNull FusionRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            Element element = Element.fromJson(json);
            boolean x512 = json.get("x512").getAsBoolean();
            int proton = json.get("proton").getAsInt();
            int neutron = json.get("neutron").getAsInt();

            return new FusionRecipe(id, element, proton, neutron, x512);
        }

        public FusionRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            try {
                Element element = Element.fromNetwork(buf);
                boolean x512 = buf.readBoolean();
                int proton = buf.readInt();
                int neutron = buf.readInt();

                return new FusionRecipe(id, element, proton, neutron, x512);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading fusing recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull FusionRecipe recipe) {
            try {
                recipe.element.toNetwork(buf);
                buf.writeBoolean(recipe.x512);
                buf.writeInt(recipe.protonCount);
                buf.writeInt(recipe.neutronCount);

            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading fusing recipe from packet.", ex);
                throw ex;
            }
        }

        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        public ResourceLocation getRegistryName() {
            return ID;
        }

        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}


