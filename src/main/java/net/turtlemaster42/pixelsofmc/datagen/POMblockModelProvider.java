package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.AbstractFusionCasing;
import net.turtlemaster42.pixelsofmc.block.AbstractMultiBlock;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.util.Element;

import java.util.function.Function;

public class POMblockModelProvider extends BlockStateProvider {
    public POMblockModelProvider(PackOutput pack, ExistingFileHelper exFileHelper) {
        super(pack, PixelsOfMc.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for(Element m : Element.values()) {
            if (m.shouldAddBlock())
                blockWithItem(POMblocks.Metals.BLOCKS.get(m));
        }

        blockWithItem(POMblocks.ENDSTONE_TITANIUM_ORE);
        blockWithItem(POMblocks.DEEPSLATE_TITANIUM_ORE);
        blockWithItem(POMblocks.TITANIUM_ORE);
        blockWithItem(POMblocks.RAW_TITANIUM_BLOCK);
        blockWithItem(POMblocks.TITANIUM_DIBORIDE_BLOCK);
        blockWithItem(POMblocks.SAND_MINERAL_DEPOSIT);
        blockWithItem(POMblocks.ACANTHITE);

        blockWithItem(POMblocks.SIMPLE_CASING_1);
        blockWithItem(POMblocks.ADVANCED_CASING_1);
        blockWithItem(POMblocks.PERFECTED_CASING_1);
        blockWithItem(POMblocks.STRONG_CASING);
        blockWithItem(POMblocks.REINFORCED_CASING);

        multiBlockWithItem(POMblocks.REINFORCED_GLASS);
        multiBlockWithItem(POMblocks.REINFORCED_THING);

        logBlock(POMblocks.COPPER_SPOOL.get());
        logBlock(POMblocks.SILVER_SPOOL.get());
        logBlock(POMblocks.TUNGSTEN_SPOOL.get());
    }

    public ModelFile flowerPotCross(String name) {
        return models().withExistingParent(name, "flower_pot_cross");
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockWithItem(POMblocks.BlockRegObject<? extends Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void multiBlockWithItem(RegistryObject<? extends AbstractMultiBlock> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void block(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(block.getAgeProperty()),
                new ResourceLocation(PixelsOfMc.MOD_ID, "block/" + textureName + state.getValue(block.getAgeProperty()))));

        return models;
    }
}
