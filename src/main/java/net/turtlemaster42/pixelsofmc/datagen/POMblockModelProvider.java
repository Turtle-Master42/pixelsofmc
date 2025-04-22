package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
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

        for (Element m : Element.values()) {
            if (m.shouldAddBlock())
                blockWithItem(POMblocks.Metals.BLOCKS.get(m));
        }

        blockWithItem(POMblocks.ENDSTONE_TITANIUM_ORE);
        blockWithItem(POMblocks.DEEPSLATE_TITANIUM_ORE);
        blockWithItem(POMblocks.TITANIUM_ORE);
        blockWithItem(POMblocks.RAW_TITANIUM_BLOCK);
        blockWithItem(POMblocks.TITANIUM_DIBORIDE_BLOCK);
        blockWithItem(POMblocks.TITANIUM_GOLD_BLOCK);
        blockWithItem(POMblocks.ACANTHITE);
        blockWithItem(POMblocks.ACANTHITE_ORE);
        blockWithItem(POMblocks.LESSER_ACANTHITE_ORE);

        blockWithItem(POMblocks.VIOLET_DIAMOND_BLOCK);
        blockWithItem(POMblocks.SIMPLE_CASING_1);
        blockWithItem(POMblocks.ADVANCED_CASING_1);
        blockWithItem(POMblocks.PERFECTED_CASING_1);
        blockWithItem(POMblocks.SIMPLE_CASING_2);
        blockWithItem(POMblocks.ADVANCED_CASING_2);
        blockWithItem(POMblocks.PERFECTED_CASING_2);
        blockWithItem(POMblocks.STRONG_CASING);
        blockWithItem(POMblocks.STRENGTHENED_CASING);
        blockWithItem(POMblocks.REINFORCED_CASING);

        multiBlockWithItem(POMblocks.MULTIBLOCK_CASING);
        multiBlockWithItem(POMblocks.ARMORED_MULTIBLOCK_CASING);
        multiBlockWithItem(POMblocks.FISSION_CASING);

        blockWithItem(POMblocks.TITANIUM_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TITANIUM_PLATING_SLAB.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_plating_block"), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_plating_block"));
        stairsBlock((StairBlock) POMblocks.TITANIUM_PLATING_STAIRS.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_plating_block"));
        blockWithItem(POMblocks.NETHERITE_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.NETHERITE_PLATING_SLAB.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/netherite_plating_block"), new ResourceLocation(PixelsOfMc.MOD_ID, "block/netherite_plating_block"));
        stairsBlock((StairBlock) POMblocks.NETHERITE_PLATING_STAIRS.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/netherite_plating_block"));
        blockWithItem(POMblocks.TITANIUM_GOLD_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TITANIUM_GOLD_PLATING_SLAB.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_gold_plating_block"), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_gold_plating_block"));
        stairsBlock((StairBlock) POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_gold_plating_block"));
        blockWithItem(POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_diboride_plating_block"), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_diboride_plating_block"));
        stairsBlock((StairBlock) POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/titanium_diboride_plating_block"));
        blockWithItem(POMblocks.LEAD_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.LEAD_PLATING_SLAB.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/lead_plating_block"), new ResourceLocation(PixelsOfMc.MOD_ID, "block/lead_plating_block"));
        stairsBlock((StairBlock) POMblocks.LEAD_PLATING_STAIRS.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/lead_plating_block"));
        blockWithItem(POMblocks.TUNGSTEN_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TUNGSTEN_PLATING_SLAB.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/tungsten_plating_block"), new ResourceLocation(PixelsOfMc.MOD_ID, "block/tungsten_plating_block"));
        stairsBlock((StairBlock) POMblocks.TUNGSTEN_PLATING_STAIRS.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/tungsten_plating_block"));
        blockWithItem(POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK);
        slabBlock((SlabBlock) POMblocks.PYROLYTIC_CARBON_SHEET_SLAB.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/pyrolytic_carbon_sheet_block"), new ResourceLocation(PixelsOfMc.MOD_ID, "block/pyrolytic_carbon_sheet_block"));
        stairsBlock((StairBlock) POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "block/pyrolytic_carbon_sheet_block"));


        logBlock(POMblocks.COPPER_SPOOL.get());
        logBlock(POMblocks.SILVER_SPOOL.get());
        logBlock(POMblocks.TUNGSTEN_SPOOL.get());
        logBlock(POMblocks.REDSTONE_LAYERED_COPPER_SPOOL.get());
        logBlock(POMblocks.RED_SILVER_SPOOL.get());
        logBlock(POMblocks.ROYAL_TUNGSTEN_SPOOL.get());
        logBlock(POMblocks.SUPERCONDUCTIVE_SPOOL.get());

        liquidBlock(POMblocks.MERCURY_BLOCK);
        liquidBlock(POMblocks.SULFURIC_ACID_BLOCK);
        liquidBlock(POMblocks.HYDROGEN_BLOCK);
        liquidBlock(POMblocks.NITROGEN_BLOCK);
        liquidBlock(POMblocks.OXYGEN_BLOCK);
        liquidBlock(POMblocks.CHLORINE_BLOCK);
        liquidBlock(POMblocks.BROMINE_BLOCK);
        liquidBlock(POMblocks.HYDROGEN_GAS_BLOCK);
        liquidBlock(POMblocks.NITROGEN_GAS_BLOCK);
        liquidBlock(POMblocks.OXYGEN_GAS_BLOCK);
        liquidBlock(POMblocks.CHLORINE_GAS_BLOCK);
        liquidBlock(POMblocks.BROMINE_GAS_BLOCK);
        liquidBlock(POMblocks.STEAM_BLOCK);
        liquidBlock(POMblocks.BLAZING_STEAM_BLOCK);
        liquidBlock(POMblocks.AMMONIA_GAS_BLOCK);
        liquidBlock(POMblocks.NITRIC_ACID_BLOCK);
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

    private void liquidBlock(RegistryObject<? extends LiquidBlock> blockRegistryObject) {
        String name = blockRegistryObject.get().getFluid().getSource().getFluidType().toString().split(":")[1];
        PixelsOfMc.LOGGER.info(name);
        simpleBlock(blockRegistryObject.get(), models().withExistingParent(name, "block/water"));
    }

//    private void blockSlabStairSetWithItem(RegistryObject<Block> block, RegistryObject<Block> stair, RegistryObject<Block> slab, String location) {
//        blockWithItem(block);
//        slabBlock((SlabBlock) slab.get(), new ResourceLocation(PixelsOfMc.MOD_ID, location), new ResourceLocation(PixelsOfMc.MOD_ID, location));
//        stairsBlock((StairBlock) stair.get(), new ResourceLocation(PixelsOfMc.MOD_ID, location));
//        simpleBlockItem(stair.get(), );
//    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + block.getAge(state),
                new ResourceLocation(PixelsOfMc.MOD_ID, "block/" + textureName + block.getAge(state))));

        return models;
    }
}
