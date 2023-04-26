package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
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
import net.turtlemaster42.pixelsofmc.item.ElementItem;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class FusionRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final int protonCount;
    private final int neutronCount;
    private final int electronCount;
    public FusionRecipe(ResourceLocation id, ItemStack output, int protonCount, int neutronCount, int electronCount) {
        this.id = id;
        this.output = output;
        this.protonCount = protonCount;
        this.neutronCount = neutronCount;
        this.electronCount = electronCount;
    }

    //credits EnderIO
    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide) return false;
        if (output.is(POMtags.Items.MDS) || output.is(POMtags.Items.MNS)) return false;
        int protonCount = 0;
        int neutronCount = 0;
        int electronCount = 0;
        
        for (int i = 0; i < 4; i++) {
            if (container.getItem(i).getItem() instanceof AtomItem item) {
                ItemStack stack = new ItemStack(item);
                int multiplier = 1;
                if (stack.is(POMtags.Items.ATOM512)) multiplier = 8;
                if (output.is(POMtags.Items.ATOM512) && !stack.is(POMtags.Items.ATOM512) && item.getProtonCount() < Element.values().length) return false;

                protonCount = protonCount + (item.getProtonCount()*multiplier);
                neutronCount = neutronCount + (item.getNeutronCount()*multiplier);
                electronCount = electronCount + (item.getElectronCount()*multiplier);
            }
        }

        return this.protonCount == protonCount && this.neutronCount <= neutronCount && this.electronCount <= electronCount;
    }


    public int getOutputCount() {
        return output.getCount();
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public List<CountedIngredient> getInputs() {
        return Collections.singletonList(CountedIngredient.EMPTY);
    }

    public int getProtonCount() {return protonCount;}
    public int getNeutronCount() {return neutronCount;}
    public int getElectronCount() {return electronCount;}
    public ItemStack getInput(int input) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.SDS_CONTROLLER.get());
    }

    public static class Type implements RecipeType<FusionRecipe> {
        private Type() { }
        public static final FusionRecipe.Type INSTANCE = new FusionRecipe.Type();
        public static final String ID = "fusing";
    }

    public static class Serializer implements RecipeSerializer<FusionRecipe> {
        public static final FusionRecipe.Serializer INSTANCE = new FusionRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"fusing");

        public FusionRecipe fromJson(ResourceLocation id, JsonObject json) {
            //output
            CountedIngredient out = CountedIngredient.fromJson(json.getAsJsonObject("output"));
            ItemStack output = new ItemStack(out.asItem(), out.count());
            int proton = json.get("proton").getAsInt();
            int neutron = json.get("neutron").getAsInt();
            int electron = json.get("electron").getAsInt();
            
            return new FusionRecipe(id, output, proton, neutron, electron);
        }

        public FusionRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            try {
                ItemStack output = buf.readItem();
                int outputCount = buf.readInt();
                int proton = buf.readInt();
                int neutron = buf.readInt();
                int electron = buf.readInt();

                return new FusionRecipe(id, output, proton, neutron, electron);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading fusing recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(FriendlyByteBuf buf, FusionRecipe recipe) {
            try {
                buf.writeItem(recipe.output);
                buf.writeInt(recipe.getOutputCount());
                buf.writeInt(recipe.protonCount);
                buf.writeInt(recipe.neutronCount);
                buf.writeInt(recipe.electronCount);

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
            return FusionRecipe.Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}


