package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.AbstractMultiBlock;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.function.Function;

public class POMblockModelProvider extends BlockStateProvider {
    public POMblockModelProvider(PackOutput pack, ExistingFileHelper exFileHelper) {
        super(pack, PixelsOfMc.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (Element m : Element.values()) {
            if (m.shouldAddBlock())
                blockWithItem(POMblocks.Elements.BLOCKS.get(m));
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

        blockWithItem(POMblocks.BLACK_DIAMOND_BLOCK);
        blockWithItem(POMblocks.PERFECT_DIAMOND_BLOCK);
        blockWithItem(POMblocks.VIOLET_DIAMOND_BLOCK);
        blockWithItem(POMblocks.RED_DIAMOND_BLOCK);
        blockWithItem(POMblocks.SIMPLE_CASING_1);
        blockWithItem(POMblocks.ADVANCED_CASING_1);
        blockWithItem(POMblocks.PERFECTED_CASING_1);
        blockWithItem(POMblocks.SIMPLE_CASING_2);
        blockWithItem(POMblocks.ADVANCED_CASING_2);
        blockWithItem(POMblocks.PERFECTED_CASING_2);
        blockWithItem(POMblocks.STRONG_CASING);
        blockWithItem(POMblocks.STRENGTHENED_CASING);
        blockWithItem(POMblocks.REINFORCED_CASING);

        multiBlockWithItem(POMblocks.MACHINE_CASING);
        multiBlockWithItem(POMblocks.ARMORED_MACHINE_CASING);
        multiBlockWithItem(POMblocks.FISSION_CASING);

        blockWithItem(POMblocks.TITANIUM_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TITANIUM_PLATING_SLAB.get(), Util.resourceLocation("block/titanium_plating_block"), Util.resourceLocation("block/titanium_plating_block"));
        stairsBlock((StairBlock) POMblocks.TITANIUM_PLATING_STAIRS.get(), Util.resourceLocation("block/titanium_plating_block"));
        blockWithItem(POMblocks.NETHERITE_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.NETHERITE_PLATING_SLAB.get(), Util.resourceLocation("block/netherite_plating_block"), Util.resourceLocation("block/netherite_plating_block"));
        stairsBlock((StairBlock) POMblocks.NETHERITE_PLATING_STAIRS.get(), Util.resourceLocation("block/netherite_plating_block"));
        blockWithItem(POMblocks.TITANIUM_GOLD_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TITANIUM_GOLD_PLATING_SLAB.get(), Util.resourceLocation("block/titanium_gold_plating_block"), Util.resourceLocation("block/titanium_gold_plating_block"));
        stairsBlock((StairBlock) POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get(), Util.resourceLocation("block/titanium_gold_plating_block"));
        blockWithItem(POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get(), Util.resourceLocation("block/titanium_diboride_plating_block"), Util.resourceLocation("block/titanium_diboride_plating_block"));
        stairsBlock((StairBlock) POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get(), Util.resourceLocation("block/titanium_diboride_plating_block"));
        blockWithItem(POMblocks.LEAD_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.LEAD_PLATING_SLAB.get(), Util.resourceLocation("block/lead_plating_block"), Util.resourceLocation("block/lead_plating_block"));
        stairsBlock((StairBlock) POMblocks.LEAD_PLATING_STAIRS.get(), Util.resourceLocation("block/lead_plating_block"));
        blockWithItem(POMblocks.TUNGSTEN_PLATING_BLOCK);
        slabBlock((SlabBlock) POMblocks.TUNGSTEN_PLATING_SLAB.get(), Util.resourceLocation("block/tungsten_plating_block"), Util.resourceLocation("block/tungsten_plating_block"));
        stairsBlock((StairBlock) POMblocks.TUNGSTEN_PLATING_STAIRS.get(), Util.resourceLocation("block/tungsten_plating_block"));
        blockWithItem(POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK);
        slabBlock((SlabBlock) POMblocks.PYROLYTIC_CARBON_SHEET_SLAB.get(), Util.resourceLocation("block/pyrolytic_carbon_sheet_block"), Util.resourceLocation("block/pyrolytic_carbon_sheet_block"));
        stairsBlock((StairBlock) POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS.get(), Util.resourceLocation("block/pyrolytic_carbon_sheet_block"));

        slabBlock((SlabBlock) POMblocks.MACHINE_CASING_SLAB.get(), "machine_casing");
        stairsBlock((StairBlock) POMblocks.MACHINE_CASING_STAIRS.get(), "machine_casing");
        slabBlock((SlabBlock) POMblocks.FISSION_CASING_SLAB.get(), "fission_casing");
        stairsBlock((StairBlock) POMblocks.FISSION_CASING_STAIRS.get(), "fission_casing");
        slabBlock((SlabBlock) POMblocks.ARMORED_MACHINE_CASING_SLAB.get(), "armored_machine_casing");
        stairsBlock((StairBlock) POMblocks.ARMORED_MACHINE_CASING_STAIRS.get(), "armored_machine_casing");



        logBlock(POMblocks.COPPER_SPOOL.get());
        logBlock(POMblocks.SILVER_SPOOL.get());
        logBlock(POMblocks.TUNGSTEN_SPOOL.get());
        logBlock(POMblocks.REDSTONE_LAYERED_COPPER_SPOOL.get());
        logBlock(POMblocks.RED_SILVER_SPOOL.get());
        logBlock(POMblocks.ROYAL_TUNGSTEN_SPOOL.get());
        logBlock(POMblocks.SUPERCONDUCTIVE_SPOOL.get());

        liquidBlock(POMblocks.MERCURY_BLOCK);
        liquidBlock(POMblocks.LIQUID_LEAD_BLOCK);
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
        liquidBlock(POMblocks.LEAD_GAS_BLOCK);
        liquidBlock(POMblocks.MERCURY_GAS_BLOCK);
        liquidBlock(POMblocks.BLAZING_STEAM_BLOCK);
        liquidBlock(POMblocks.AMMONIA_GAS_BLOCK);
        liquidBlock(POMblocks.NITRIC_ACID_BLOCK);
        liquidBlock(POMblocks.PUREX_SOLUTION_BLOCK);
        liquidBlock(POMblocks.NUCLEAR_WASTE_SOLUTION_BLOCK);
        liquidBlock(POMblocks.NUCLEAR_WASTE_BLOCK);
        liquidBlock(POMblocks.URANIUM_SOLUTION_BLOCK);
        liquidBlock(POMblocks.PLUTONIUM_SOLUTION_BLOCK);
        liquidBlock(POMblocks.RED_OIL_BLOCK);
        liquidBlock(POMblocks.DIRTY_WATER_BLOCK);
        liquidBlock(POMblocks.HYDROFLUORIC_ACID_BLOCK);
        liquidBlock(POMblocks.URANIUM_HEXAFLUORIDE_GAS_BLOCK);
        liquidBlock(POMblocks.ENRICHED_URANIUM_SOLUTION_BLOCK);
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
        simpleBlock(blockRegistryObject.get(), models().withExistingParent(name, "block/water"));
    }

    private void stairsBlock(StairBlock stairBlock, String base) {
        String name = ForgeRegistries.BLOCKS.getKey(stairBlock).toString();
        ModelFile stairs = models().withExistingParent(name, Util.resourceLocation("block/stairs"))
                .texture("corner", Util.resourceLocation("block/" + base + "_corner"))
                .texture("half", Util.resourceLocation("block/" + base + "_half"))
                .texture("bottom", Util.resourceLocation("block/" + base));
        ModelFile stairsInner = models().withExistingParent(name +"_inner", Util.resourceLocation("block/inner_stairs"))
                .texture("corner", Util.resourceLocation("block/" + base + "_corner"))
                .texture("bottom", Util.resourceLocation("block/" + base))
                .texture("quarter", Util.resourceLocation("block/" + base + "_quarter"));
        ModelFile stairsOuter = models().withExistingParent(name + "_outer", Util.resourceLocation("block/outer_stairs"))
                .texture("corner", Util.resourceLocation("block/" + base + "_corner"))
                .texture("half", Util.resourceLocation("block/" + base + "_half"))
                .texture("bottom", Util.resourceLocation("block/" + base))
                .texture("quarter", Util.resourceLocation("block/" + base + "_quarter"));
        getVariantBuilder(stairBlock)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(StairBlock.FACING);
                    Half half = state.getValue(StairBlock.HALF);
                    StairsShape shape = state.getValue(StairBlock.SHAPE);
                    return ConfiguredModel.builder()
                            .modelFile(shape == StairsShape.STRAIGHT ? stairs : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairsInner : stairsOuter)
                            .rotationX(half == Half.BOTTOM ? 0 : 180)
                            .rotationY(getYRot(facing, shape, half))
                            .build();
                }, StairBlock.WATERLOGGED);
    }

    private static int getYRot(Direction facing, StairsShape shape, Half half) {
        int yRot = (int) facing.getClockWise().toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
        if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
            yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
        }
        if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
            yRot += 90; // Top stairs are rotated 90 degrees clockwise
        }
        yRot %= 360;
        return yRot;
    }

    private void slabBlock(SlabBlock slabBlock, String base) {
        models().cubeColumn(base + "_slab_double",
                Util.resourceLocation("block/" + base + "_half"),
                Util.resourceLocation("block/" + base));
        slabBlock(slabBlock,
                Util.resourceLocation("block/" + base + "_slab_double"),
                Util.resourceLocation("block/" + base + "_half"),
                Util.resourceLocation("block/" + base),
                Util.resourceLocation("block/" + base)
        );
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + block.getAge(state),
                Util.resourceLocation("block/" + textureName + block.getAge(state))));

        return models;
    }
}
