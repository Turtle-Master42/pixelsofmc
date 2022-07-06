package net.turtlemaster42.pixelsofmc.datagen;

import net.turtlemaster42.pixelsofmc.PixelsOfMcMod;
import net.turtlemaster42.pixelsofmc.init.PixelsOfMcModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PixelsOfMcModItemModelProvider extends ItemModelProvider {
    public PixelsOfMcModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMcMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //dust
        simpleItem(PixelsOfMcModItems.COAL_DUST.get());
        simpleItem(PixelsOfMcModItems.STEEL_DUST.get());
        simpleItem(PixelsOfMcModItems.IRON_DUST.get());
        simpleItem(PixelsOfMcModItems.TITANIUM_DUST.get());
        simpleItem(PixelsOfMcModItems.TITANIUM_DIBORIDE_DUST.get());
        //plastic
        simpleItem(PixelsOfMcModItems.BIO_COMPOUND.get());
        simpleItem(PixelsOfMcModItems.BIO_PLASTIC_SHEET.get());
        simpleItem(PixelsOfMcModItems.FIRE_PROOF_COMPOUND.get());
        simpleItem(PixelsOfMcModItems.FIRE_PROOF_PLASTIC.get());
        simpleItem(PixelsOfMcModItems.REPELLING_COMPOUND.get());
        simpleItem(PixelsOfMcModItems.REPELLING_PLASTIC.get());
        //circuit
        simpleItem(PixelsOfMcModItems.ADVANCED_CIRCUIT_BOARD.get());
        simpleItem(PixelsOfMcModItems.NORMAL_CIRCUIT_BOARD.get());
        simpleItem(PixelsOfMcModItems.SIMPLE_CIRCUIT_BOARD.get());
        simpleItem(PixelsOfMcModItems.POWER_CELL.get());
        simpleItem(PixelsOfMcModItems.POWER_ORB.get());
        simpleItem(PixelsOfMcModItems.ADVANCED_LASER.get());
        simpleItem(PixelsOfMcModItems.MICRO_CHIP.get());
        simpleItem(PixelsOfMcModItems.COPPER_WIRE.get());
        simpleItem(PixelsOfMcModItems.CIRCLE_SAW.get());
        //plates
        simpleItem(PixelsOfMcModItems.ANCIENT_HEAT_PLATE.get());
        simpleItem(PixelsOfMcModItems.HEAT_RESISTANT_PLATING.get());
        simpleItem(PixelsOfMcModItems.REFINED_HEAT_RESISTANT_PLATING.get());
        simpleItem(PixelsOfMcModItems.TITANIUM_PLATING.get());
        //metals
        simpleItem(PixelsOfMcModItems.COPPER_NUGGET.get());
        simpleItem(PixelsOfMcModItems.NETHERITE_NUGGET.get());
        simpleItem(PixelsOfMcModItems.RAW_TITANIUM.get());
        simpleItem(PixelsOfMcModItems.TITANIUM_INGOT.get());
        simpleItem(PixelsOfMcModItems.TITANIUM_NUGGET.get());
        simpleItem(PixelsOfMcModItems.TITANIUM_DIBORIDE_INGOT.get());
        simpleItem(PixelsOfMcModItems.STEEL_INGOT.get());
        //jar
        simpleItem(PixelsOfMcModItems.EMPTY_JAR.get());
        simpleItem(PixelsOfMcModItems.JAR_OF_PIXELS_COPPER.get());
        simpleItem(PixelsOfMcModItems.JAR_OF_PIXELS_DIAMOND.get());
        simpleItem(PixelsOfMcModItems.JAR_OF_PIXELS_EMERALD.get());
        simpleItem(PixelsOfMcModItems.JAR_OF_PIXELS_IRON.get());
        simpleItem(PixelsOfMcModItems.JAR_OF_PIXELS_PLANTS.get());
        simpleItem(PixelsOfMcModItems.JAR_OF_PIXELS.get());
        //other
        simpleItem(PixelsOfMcModItems.SPEED_UPGRADE.get());
        simpleItem(PixelsOfMcModItems.ENERGY_UPGRADE.get());
        simpleItem(PixelsOfMcModItems.BOOK_1.get());



    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMcMod.MOD_ID,"items/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(PixelsOfMcMod.MOD_ID,"items/" + item.getRegistryName().getPath()));
    }
}
