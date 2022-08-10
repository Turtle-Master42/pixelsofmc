package net.turtlemaster42.pixelsofmc.datagen;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
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
        simpleItem(POMitems.URANIUM_DUST.get());
        simpleItem(POMitems.SULFUR.get());
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
        simpleItem(POMitems.TITANIUM_CIRCLE_SAW.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get());
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
        simpleItem(POMitems.URANIUM_INGOT.get());
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
        //elements
        simpleItem(POMitems.BORON_CUBE.get());
        simpleItem(POMitems.CARBON_CUBE.get());
        simpleItem(POMitems.DENSE_CARBON_CUBE.get());
        simpleItem(POMitems.SILICON_CUBE.get());
        simpleItem(POMitems.SULFUR_CUBE.get());
        simpleItem(POMitems.HYDROGEN_CANISTER.get());
        simpleItem(POMitems.HELIUM_CANISTER.get());
        simpleItem(POMitems.OXYGEN_CANISTER.get());

        simpleItem(POMitems.ALUMINIUM_INGOT.get());
        simpleItem(POMitems.BERYLLIUM_INGOT.get());
        simpleItem(POMitems.CALCIUM_INGOT.get());
        simpleItem(POMitems.COBALT_INGOT.get());
        simpleItem(POMitems.CHROMIUM_INGOT.get());
        simpleItem(POMitems.GALLIUM_INGOT.get());
        simpleItem(POMitems.LITHIUM_INGOT.get());
        simpleItem(POMitems.MANGANESE_INGOT.get());
        simpleItem(POMitems.MAGNESIUM_INGOT.get());
        simpleItem(POMitems.NICKEL_INGOT.get());
        simpleItem(POMitems.POTASSIUM_INGOT.get());
        simpleItem(POMitems.SCANDIUM_INGOT.get());
        simpleItem(POMitems.SODIUM_INGOT.get());
        simpleItem(POMitems.VANADIUM_INGOT.get());
        simpleItem(POMitems.ZINC_INGOT.get());
        simpleItem(POMitems.RUBIDIUM_INGOT.get());
        simpleItem(POMitems.STRONTIUM_INGOT.get());
        simpleItem(POMitems.YTTRIUM_INGOT.get());
        simpleItem(POMitems.ZIRCONIUM_INGOT.get());
        simpleItem(POMitems.NIOBIUM_INGOT.get());
        simpleItem(POMitems.MOLYBDENUM_INGOT.get());
        simpleItem(POMitems.TECHNETIUM_INGOT.get());
        simpleItem(POMitems.RUTHENIUM_INGOT.get());
        simpleItem(POMitems.RHODIUM_INGOT.get());
        simpleItem(POMitems.PALLADIUM_INGOT.get());
        simpleItem(POMitems.SILVER_INGOT.get());
        simpleItem(POMitems.CADMIUM_INGOT.get());
        simpleItem(POMitems.INDIUM_INGOT.get());
        simpleItem(POMitems.TIN_INGOT.get());
        simpleItem(POMitems.ANTIMONY_INGOT.get());
        simpleItem(POMitems.CAESIUM_INGOT.get());
        simpleItem(POMitems.BARIUM_INGOT.get());
        simpleItem(POMitems.LANTHANUM_INGOT.get());
        simpleItem(POMitems.CERIUM_INGOT.get());
        simpleItem(POMitems.PRASEODYMIUM_INGOT.get());
        simpleItem(POMitems.NEODYMIUM_INGOT.get());
        simpleItem(POMitems.PROMETHIUM_INGOT.get());
        simpleItem(POMitems.SAMARIUM_INGOT.get());
        simpleItem(POMitems.EUROPIUM_INGOT.get());
        simpleItem(POMitems.GADOLINIUM_INGOT.get());
        simpleItem(POMitems.TERBIUM_INGOT.get());
        simpleItem(POMitems.DYSPROSIUM_INGOT.get());
        simpleItem(POMitems.HOLMIUM_INGOT.get());
        simpleItem(POMitems.ERBIUM_INGOT.get());
        simpleItem(POMitems.THULIUM_INGOT.get());
        simpleItem(POMitems.YTTERBIUM_INGOT.get());
        simpleItem(POMitems.LUTETIUM_INGOT.get());
        simpleItem(POMitems.HAFNIUM_INGOT.get());
        simpleItem(POMitems.TANTALUM_INGOT.get());
        simpleItem(POMitems.TUNGSTEN_INGOT.get());
        simpleItem(POMitems.RHENIUM_INGOT.get());
        simpleItem(POMitems.OSMIUM_INGOT.get());
        simpleItem(POMitems.IRIDIUM_INGOT.get());
        simpleItem(POMitems.PLATINUM_INGOT.get());
        simpleItem(POMitems.MERCURY_INGOT.get());
        simpleItem(POMitems.THALLIUM_INGOT.get());
        simpleItem(POMitems.LEAD_INGOT.get());
        simpleItem(POMitems.BISMUTH_INGOT.get());
        simpleItem(POMitems.POLONIUM_INGOT.get());



        simpleItem(POMitems.BIT_PIXEL.get());


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
