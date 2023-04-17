package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
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
    public POMitemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for(Element m : Element.values()) {
            if (m.equals(Element.DEBUGIUM)) continue;
            createElementModels(m);
            createDustModels(m);
            createNuggetModels(m);
            createAtomModels(m);
        }

        simpleItem(POMitems.BOOK_1);
        simpleItem(POMitems.CLEANING_SPONGE);

        simpleItem(POMitems.BIO_COMPOUND);
        simpleItem(POMitems.BIO_PLASTIC);
        simpleItem(POMitems.FIRE_PROOF_COMPOUND);
        simpleItem(POMitems.FIRE_PROOF_PLASTIC);
        simpleItem(POMitems.REPELLING_COMPOUND);
        simpleItem(POMitems.REPELLING_PLASTIC);

        simpleItem(POMitems.CARBONARO_CLUMP);
        simpleItem(POMitems.BLACK_DIAMOND);
        simpleItem(POMitems.VIOLET_DIAMOND);
        simpleItem(POMitems.RED_DIAMOND);

        simpleItem(POMitems.COPPER_WIRE);
        simpleItem(POMitems.SILVER_WIRE);
        simpleItem(POMitems.TUNGSTEN_WIRE);
        simpleItem(POMitems.REDSTONE_LAYERED_COPPER_WIRE);
        simpleItem(POMitems.REDSTONE_IMBUED_SILVER_WIRE);
        simpleItem(POMitems.RED_TUNGSTEN_WIRE);
        simpleItem(POMitems.POWER_ORB);
        simpleItem(POMitems.MICRO_CHIP);
        simpleItem(POMitems.REDSTONE_COUNTER);
        simpleItem(POMitems.RESONANCE_DETECTOR);
        simpleItem(POMitems.DRAGON_EYE);
        simpleItem(POMitems.VOID_EYE);
        simpleItem(POMitems.ENDER_SENSOR);
        simpleItem(POMitems.DRAGON_SENSOR);
        simpleItem(POMitems.VOID_SENSOR);
        simpleItem(POMitems.DIAMOND_LENS);
        simpleItem(POMitems.VIOLET_DIAMOND_LENS);
        simpleItem(POMitems.RED_DIAMOND_LENS);

        simpleItem(POMitems.SIMPLE_CIRCUIT_BOARD_1);
        simpleItem(POMitems.ADVANCED_CIRCUIT_BOARD_1);
        simpleItem(POMitems.PERFECTED_CIRCUIT_BOARD_1);

        simpleItem(POMitems.RAW_TITANIUM);
        simpleItem(POMitems.TITANIUM_DIBORIDE_INGOT);

        simpleItem(POMitems.NETHERITE_NUGGET);
        simpleItem(POMitems.TITANIUM_DIBORIDE_NUGGET);

        simpleItem(POMitems.TITANIUM_PLATING);
        simpleItem(POMitems.RUSTED_PLATING);
        simpleItem(POMitems.NETHERITE_PLATING);
        simpleItem(POMitems.TITANIUM_DIBORIDE_PLATING);
        simpleItem(POMitems.OBSIDIAN_PLATING);
        simpleItem(POMitems.CRYING_OBSIDIAN_PLATING);

        simpleItem(POMitems.MERCURY_BUCKET);

        simpleItem(POMitems.MOVING_PARTS);
        simpleItem(POMitems.POWER_CELL);
        simpleItem(POMitems.ADVANCED_LASER);
        simpleItem(POMitems.TITANIUM_CIRCLE_SAW);
        simpleItem(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW);
        simpleItem(POMitems.INGOT_CAST);
        simpleItem(POMitems.BALL_CAST);
        simpleItem(POMitems.PLATE_CAST);

        simpleItem(POMitems.SPEED_UPGRADE);
        simpleItem(POMitems.ENERGY_UPGRADE);
        simpleItem(POMitems.HEAT_UPGRADE);

        handheldItem(POMitems.CLEANING_CLOTH);
        handheldItem(POMitems.SCREWDRIVER);
        handheldItem(POMitems.WIRECUTTER);
        handheldItem(POMitems.HAMMER);

        simpleItem(POMitems.RUBBER_BALL);
        simpleItem(POMitems.FIRE_PROOF_RUBBER_BALL);
        simpleItem(POMitems.REPELLING_RUBBER_BALL);
        simpleItem(POMitems.NETHERITE_BALL);
        simpleItem(POMitems.TITANIUM_BALL);
        simpleItem(POMitems.TITANIUM_DIBORIDE_BALL);

        dustItem(POMitems.COAL_DUST);
        dustItem(POMitems.TITANIUM_DIBORIDE_DUST);

        dustItem(POMitems.ANCIENT_DEBRIS_DUST);
        dustItem(POMitems.MERCURY_SULFIDE_DUST);
        dustItem(POMitems.ACANTHITE_DUST);
        dustItem(POMitems.OBSIDIAN_DUST);
        dustItem(POMitems.CRYING_OBSIDIAN_DUST);
        dustItem(POMitems.MINERAL_GRIT);
        dustItem(POMitems.NETHERITE_DUST);
        dustItem(POMitems.TITANIUM_OXIDE_DUST);

        simpleItem(POMitems.SULFUR);
        simpleItem(POMitems.DENSE_CARBON_CUBE);
        simpleItem(POMitems.ALUMINIUM_SCRAP);


        simpleItem(POMitems.TEST_ITEM);
        simpleItem(POMitems.PLACE_HOLDER);
        simpleItem(POMitems.DEBUGUIM_INGOT);

        complexBlock(POMblocks.COPPER_SPOOL);
        complexBlock(POMblocks.SILVER_SPOOL);
        complexBlock(POMblocks.TUNGSTEN_SPOOL);

        complexBlock(POMblocks.PIXEL_SPLITTER);
        complexBlock(POMblocks.GRINDER);


    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(POMitems.ItemRegObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder elementItem(POMitems.ItemRegObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"item/elements/" + item.getId().getPath()));
    }

    private ItemModelBuilder dustItem(POMitems.ItemRegObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"item/dusts/" + item.getId().getPath()));
    }
    private ItemModelBuilder dustItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"item/dusts/" + item.getId().getPath()));
    }
    private ItemModelBuilder atomItem(POMitems.ItemRegObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"item/atoms/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlock(RegistryObject<Block> block) {
        return cubeAll(block.getId().getPath(),
                new ResourceLocation(PixelsOfMc.MOD_ID, "block/" + block.getId().getPath()));
    }
    private ItemModelBuilder complexBlock(RegistryObject<?> block) {
        return withExistingParent(block.getId().getPath(), new ResourceLocation(PixelsOfMc.MOD_ID,
                "block/" + block.getId().getPath()));
    }

    private void createElementModels(Element element) {
        if (!element.isVanilla())
            elementItem(POMitems.Metals.ELEMENTS.get(element));
    }
    private void createDustModels(Element element) {
        if (element.shouldAddDust())
            dustItem(POMitems.Metals.DUSTS.get(element));
    }
    private void createNuggetModels(Element element) {
        if (element.shouldAddNugget())
            simpleItem(POMitems.Metals.NUGGETS.get(element));
    }
    private void createAtomModels(Element element) {
        atomItem(POMitems.Metals.ATOMX64.get(element));
        atomItem(POMitems.Metals.ATOMX512.get(element));
    }
}
