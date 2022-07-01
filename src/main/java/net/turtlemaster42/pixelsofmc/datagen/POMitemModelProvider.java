package net.turtlemaster42.pixelsofmc.datagen;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class POMitemModelProvider extends ItemModelProvider {
    public POMitemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //dust
        simpleItem(POMitems.COAL_DUST.get());
        simpleItem(POMitems.STEEL_DUST.get());
        simpleItem(POMitems.IRON_DUST.get());
        simpleItem(POMitems.TITANIUM_DUST.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_DUST.get());
        //plastic
        simpleItem(POMitems.BIO_COMPOUND.get());
        simpleItem(POMitems.BIO_PLASTIC_SHEET.get());
        simpleItem(POMitems.FIRE_PROOF_COMPOUND.get());
        simpleItem(POMitems.FIRE_PROOF_PLASTIC.get());
        simpleItem(POMitems.REPELLING_COMPOUND.get());
        simpleItem(POMitems.REPELLING_PLASTIC.get());
        //circuit
        simpleItem(POMitems.ADVANCED_CIRCUIT_BOARD.get());
        simpleItem(POMitems.NORMAL_CIRCUIT_BOARD.get());
        simpleItem(POMitems.SIMPLE_CIRCUIT_BOARD.get());
        simpleItem(POMitems.POWER_CELL.get());
        simpleItem(POMitems.POWER_ORB.get());
        simpleItem(POMitems.ADVANCED_LASER.get());
        simpleItem(POMitems.MICRO_CHIP.get());
        simpleItem(POMitems.COPPER_WIRE.get());
        simpleItem(POMitems.CIRCLE_SAW.get());
        //plates
        simpleItem(POMitems.ANCIENT_HEAT_PLATE.get());
        simpleItem(POMitems.HEAT_RESISTANT_PLATING.get());
        simpleItem(POMitems.REFINED_HEAT_RESISTANT_PLATING.get());
        simpleItem(POMitems.TITANIUM_PLATING.get());
        //metals
        simpleItem(POMitems.COPPER_NUGGET.get());
        simpleItem(POMitems.NETHERITE_NUGGET.get());
        simpleItem(POMitems.RAW_TITANIUM.get());
        simpleItem(POMitems.TITANIUM_INGOT.get());
        simpleItem(POMitems.TITANIUM_NUGGET.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_INGOT.get());
        simpleItem(POMitems.STEEL_INGOT.get());
        //jar
        simpleItem(POMitems.EMPTY_JAR.get());
        simpleItem(POMitems.JAR_OF_PIXELS_COPPER.get());
        simpleItem(POMitems.JAR_OF_PIXELS_DIAMOND.get());
        simpleItem(POMitems.JAR_OF_PIXELS_EMERALD.get());
        simpleItem(POMitems.JAR_OF_PIXELS_IRON.get());
        simpleItem(POMitems.JAR_OF_PIXELS_PLANTS.get());
        simpleItem(POMitems.JAR_OF_PIXELS.get());
        //other
        simpleItem(POMitems.SPEED_UPGRADE.get());
        simpleItem(POMitems.ENERGY_UPGRADE.get());
        simpleItem(POMitems.BOOK_1.get());



    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/" + item.getRegistryName().getPath()));
    }
}
