package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.util.Element;


public class POMitemModelProvider extends ItemModelProvider {
    public POMitemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for(Element m : Element.values()) {
            createElementModels(m);
            createDustModels(m);
            createNuggetModels(m);
            createAtomModels(m);
        }

        simpleItem(POMitems.BOOK_1.get());

        simpleItem(POMitems.BIO_COMPOUND.get());
        simpleItem(POMitems.BIO_PLASTIC.get());
        simpleItem(POMitems.FIRE_PROOF_COMPOUND.get());
        simpleItem(POMitems.FIRE_PROOF_PLASTIC.get());
        simpleItem(POMitems.REPELLING_COMPOUND.get());
        simpleItem(POMitems.REPELLING_PLASTIC.get());

        simpleItem(POMitems.CARBONARO_CLUMP.get());
        simpleItem(POMitems.BLACK_DIAMOND.get());
        simpleItem(POMitems.VIOLET_DIAMOND.get());
        simpleItem(POMitems.RED_DIAMOND.get());

        simpleItem(POMitems.COPPER_WIRE.get());
        simpleItem(POMitems.SILVER_WIRE.get());
        simpleItem(POMitems.REDSTONE_LAYERED_COPPER_WIRE.get());
        simpleItem(POMitems.POWER_ORB.get());
        simpleItem(POMitems.MICRO_CHIP.get());
        simpleItem(POMitems.REDSTONE_COUNTER.get());
        simpleItem(POMitems.DRAGON_EYE.get());
        simpleItem(POMitems.VOID_EYE.get());
        simpleItem(POMitems.ENDER_SENSOR.get());
        simpleItem(POMitems.DRAGON_SENSOR.get());
        simpleItem(POMitems.VOID_SENSOR.get());
        simpleItem(POMitems.DIAMOND_LENS.get());
        simpleItem(POMitems.VIOLET_DIAMOND_LENS.get());
        simpleItem(POMitems.RED_DIAMOND_LENS.get());

        simpleItem(POMitems.SIMPLE_CIRCUIT_BOARD_1.get());
        simpleItem(POMitems.ADVANCED_CIRCUIT_BOARD_1.get());
        simpleItem(POMitems.PERFECTED_CIRCUIT_BOARD_1.get());

        simpleItem(POMitems.RAW_TITANIUM.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_INGOT.get());

        simpleItem(POMitems.NETHERITE_NUGGET.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_NUGGET.get());

        simpleItem(POMitems.TITANIUM_PLATING.get());
        simpleItem(POMitems.RUSTED_PLATING.get());
        simpleItem(POMitems.NETHERITE_PLATING.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_PLATING.get());

        simpleItem(POMitems.MOVING_PARTS.get());
        simpleItem(POMitems.POWER_CELL.get());
        simpleItem(POMitems.ADVANCED_LASER.get());
        simpleItem(POMitems.TITANIUM_CIRCLE_SAW.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get());
        simpleItem(POMitems.INGOT_CAST.get());
        simpleItem(POMitems.BALL_CAST.get());
        simpleItem(POMitems.PLATE_CAST.get());

        simpleItem(POMitems.SPEED_UPGRADE.get());
        simpleItem(POMitems.ENERGY_UPGRADE.get());
        simpleItem(POMitems.HEAT_UPGRADE.get());

        handheldItem(POMitems.CLEANING_CLOTH.get());
        handheldItem(POMitems.SCREWDRIVER.get());
        handheldItem(POMitems.WIRECUTTER.get());
        handheldItem(POMitems.HAMMER.get());

        simpleItem(POMitems.RUBBER_BALL.get());
        simpleItem(POMitems.FIRE_PROOF_RUBBER_BALL.get());
        simpleItem(POMitems.REPELLING_RUBBER_BALL.get());
        simpleItem(POMitems.NETHERITE_BALL.get());
        simpleItem(POMitems.TITANIUM_BALL.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_BALL.get());

        dustItem(POMitems.COAL_DUST.get());
        dustItem(POMitems.TITANIUM_DIBORIDE_DUST.get());

        dustItem(POMitems.ANCIENT_DEBRIS_DUST.get());
        dustItem(POMitems.MERCURY_SULFIDE_DUST.get());
        dustItem(POMitems.MINERAL_GRIT.get());
        dustItem(POMitems.NETHERITE_DUST.get());
        dustItem(POMitems.TITANIUM_OXIDE_DUST.get());

        simpleItem(POMitems.SULFUR.get());
        simpleItem(POMitems.DENSE_CARBON_CUBE.get());
        simpleItem(POMitems.ALUMINIUM_SCRAP.get());


        simpleItem(POMitems.PIXEL.get());
        simpleItem(POMitems.TEST_ITEM.get());


        
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
        complexBlock(POMblocks.COPPER_SPOOL.get());

        complexBlock(POMblocks.PIXEL_SPLITTER.get());
        complexBlock(POMblocks.GRINDER.get());

        simpleBlock(POMblocks.SDS_CONTROLLER.get());


    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder elementItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/elements/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder elementItem(Item item, String extra) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/elements/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder dustItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/dusts/" + item.getRegistryName().getPath()));
    }
    private ItemModelBuilder atomItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/atoms/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder simpleBlock(Block block) {
        return cubeAll(block.getRegistryName().getPath(),
                new ResourceLocation(PixelsOfMc.MOD_ID, "block/" + block.getRegistryName().getPath()));
    }
    private ItemModelBuilder complexBlock(Block block) {
        return withExistingParent(block.getRegistryName().getPath(), new ResourceLocation(PixelsOfMc.MOD_ID,
                "block/" + block.getRegistryName().getPath()));
    }

    private void createElementModels(Element element) {
        if (!element.isVanilla())
            elementItem(POMitems.Metals.ELEMENTS.get(element).asItem());
    }
    private void createDustModels(Element element) {
        if (element.shouldAddDust())
            dustItem(POMitems.Metals.DUSTS.get(element).asItem());
    }
    private void createNuggetModels(Element element) {
        if (element.shouldAddNugget())
            simpleItem(POMitems.Metals.NUGGETS.get(element).asItem());
    }
    private void createAtomModels(Element element) {
        atomItem(POMitems.Metals.ATOMX64.get(element).asItem());
        atomItem(POMitems.Metals.ATOMX512.get(element).asItem());
    }
}
