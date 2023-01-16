package net.turtlemaster42.pixelsofmc.datagen;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class POMblockModelProvider extends BlockStateProvider {
    public POMblockModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, PixelsOfMc.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(POMblocks.TITANIUM_BLOCK.get());
        simpleBlock(POMblocks.ENDSTONE_TITANIUM_ORE.get());
        simpleBlock(POMblocks.DEEPSLATE_TITANIUM_ORE.get());
        simpleBlock(POMblocks.TITANIUM_ORE.get());
        simpleBlock(POMblocks.RAW_TITANIUM_BLOCK.get());
        simpleBlock(POMblocks.TITANIUM_DIBORIDE_BLOCK.get());
        simpleBlock(POMblocks.SAND_MINERAL_DEPOSIT.get());
        simpleBlock(POMblocks.ACANTHITE.get());

        simpleBlock(POMblocks.SIMPLE_CASING_1.get());
        simpleBlock(POMblocks.ADVANCED_CASING_1.get());
        simpleBlock(POMblocks.PERFECTED_CASING_1.get());
        simpleBlock(POMblocks.STRONG_CASING.get());
        simpleBlock(POMblocks.REINFORCED_CASING.get());
        logBlock(POMblocks.COPPER_SPOOL.get());

        simpleBlock(POMblocks.SDS_CONTROLLER.get());
        simpleBlock(POMblocks.HOT_ISOTOPIC_PRESS.get());

    }

    public ModelFile flowerPotCross(String name) {
        return models().withExistingParent(name, "flower_pot_cross");
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(block.getAgeProperty()),
                new ResourceLocation(PixelsOfMc.MOD_ID, "block/" + textureName + state.getValue(block.getAgeProperty()))));

        return models;
    }
}
