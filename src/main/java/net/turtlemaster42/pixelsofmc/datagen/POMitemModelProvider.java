package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblockEntities;
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
        simpleItem(POMitems.SILVER_WIRE.get());
        simpleItem(POMitems.REDSTONE_COUNTER.get());
        simpleItem(POMitems.REDSTONE_LAYERED_WIRE.get());
        simpleItem(POMitems.DRAGON_EYE.get());
        simpleItem(POMitems.VOID_EYE.get());
        simpleItem(POMitems.DIAMOND_LENS.get());
        simpleItem(POMitems.VIOLET_DIAMOND_LENS.get());
        simpleItem(POMitems.RED_DIAMOND_LENS.get());
        simpleItem(POMitems.ENDER_SENSOR.get());
        simpleItem(POMitems.DRAGON_SENSOR.get());
        simpleItem(POMitems.VOID_SENSOR.get());
        simpleItem(POMitems.TITANIUM_CIRCLE_SAW.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get());
        //plates
        simpleItem(POMitems.ANCIENT_HEAT_PLATE.get());
        simpleItem(POMitems.HEAT_RESISTANT_PLATING.get());
        simpleItem(POMitems.REFINED_HEAT_RESISTANT_PLATING.get());
        simpleItem(POMitems.TITANIUM_PLATING.get());
        //metals
        simpleItem(POMitems.COPPER_NUGGET.get());
        simpleItem(POMitems.SILVER_NUGGET.get());
        simpleItem(POMitems.NETHERITE_NUGGET.get());
        simpleItem(POMitems.RAW_TITANIUM.get());
        elementItem(POMitems.TITANIUM_INGOT.get());
        simpleItem(POMitems.TITANIUM_NUGGET.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_INGOT.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_NUGGET.get());
        simpleItem(POMitems.STEEL_INGOT.get());
        simpleItem(POMitems.STEEL_NUGGET.get());
        elementItem(POMitems.URANIUM_INGOT.get());
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
        elementItem(POMitems.BORON_CUBE.get());
        elementItem(POMitems.CARBON_CUBE.get());
        simpleItem(POMitems.DENSE_CARBON_CUBE.get());
        elementItem(POMitems.SILICON_CUBE.get());
        elementItem(POMitems.SULFUR_CUBE.get());
        elementItem(POMitems.PHOSPHORUS_CUBE.get());
        elementItem(POMitems.HYDROGEN_CANISTER.get());
        elementItem(POMitems.HELIUM_CANISTER.get());
        elementItem(POMitems.OXYGEN_CANISTER.get());
        elementItem(POMitems.RADON_CANISTER.get());
        elementItem(POMitems.KRYPTON_CANISTER.get());
        elementItem(POMitems.ARGON_CANISTER.get());
        elementItem(POMitems.NEON_CANISTER.get());
        elementItem(POMitems.CHLORINE_CANISTER.get());
        elementItem(POMitems.BROMINE_CANISTER.get());
        elementItem(POMitems.NITROGEN_CANISTER.get());
        elementItem(POMitems.XENON_CANISTER.get());

        elementItem(POMitems.ALUMINIUM_INGOT.get());
        elementItem(POMitems.BERYLLIUM_INGOT.get());
        elementItem(POMitems.CALCIUM_INGOT.get());
        elementItem(POMitems.COBALT_INGOT.get());
        elementItem(POMitems.CHROMIUM_INGOT.get());
        elementItem(POMitems.GALLIUM_INGOT.get());
        elementItem(POMitems.LITHIUM_INGOT.get());
        elementItem(POMitems.MANGANESE_INGOT.get());
        elementItem(POMitems.MAGNESIUM_INGOT.get());
        elementItem(POMitems.NICKEL_INGOT.get());
        elementItem(POMitems.POTASSIUM_INGOT.get());
        elementItem(POMitems.SCANDIUM_INGOT.get());
        elementItem(POMitems.SODIUM_INGOT.get());
        elementItem(POMitems.VANADIUM_INGOT.get());
        elementItem(POMitems.ZINC_INGOT.get());
        elementItem(POMitems.RUBIDIUM_INGOT.get());
        elementItem(POMitems.STRONTIUM_INGOT.get());
        elementItem(POMitems.YTTRIUM_INGOT.get());
        elementItem(POMitems.ZIRCONIUM_INGOT.get());
        elementItem(POMitems.NIOBIUM_INGOT.get());
        elementItem(POMitems.MOLYBDENUM_INGOT.get());
        elementItem(POMitems.TECHNETIUM_INGOT.get());
        elementItem(POMitems.RUTHENIUM_INGOT.get());
        elementItem(POMitems.RHODIUM_INGOT.get());
        elementItem(POMitems.PALLADIUM_INGOT.get());
        elementItem(POMitems.SILVER_INGOT.get());
        elementItem(POMitems.CADMIUM_INGOT.get());
        elementItem(POMitems.INDIUM_INGOT.get());
        elementItem(POMitems.TIN_INGOT.get());
        elementItem(POMitems.ANTIMONY_INGOT.get());
        elementItem(POMitems.CAESIUM_INGOT.get());
        elementItem(POMitems.BARIUM_INGOT.get());
        elementItem(POMitems.LANTHANUM_INGOT.get());
        elementItem(POMitems.CERIUM_INGOT.get());
        elementItem(POMitems.PRASEODYMIUM_INGOT.get());
        elementItem(POMitems.NEODYMIUM_INGOT.get());
        elementItem(POMitems.PROMETHIUM_INGOT.get());
        elementItem(POMitems.SAMARIUM_INGOT.get());
        elementItem(POMitems.EUROPIUM_INGOT.get());
        elementItem(POMitems.GADOLINIUM_INGOT.get());
        elementItem(POMitems.TERBIUM_INGOT.get());
        elementItem(POMitems.DYSPROSIUM_INGOT.get());
        elementItem(POMitems.HOLMIUM_INGOT.get());
        elementItem(POMitems.ERBIUM_INGOT.get());
        elementItem(POMitems.THULIUM_INGOT.get());
        elementItem(POMitems.YTTERBIUM_INGOT.get());
        elementItem(POMitems.LUTETIUM_INGOT.get());
        elementItem(POMitems.HAFNIUM_INGOT.get());
        elementItem(POMitems.TANTALUM_INGOT.get());
        elementItem(POMitems.TUNGSTEN_INGOT.get());
        elementItem(POMitems.RHENIUM_INGOT.get());
        elementItem(POMitems.OSMIUM_INGOT.get());
        elementItem(POMitems.IRIDIUM_INGOT.get());
        elementItem(POMitems.PLATINUM_INGOT.get());
        elementItem(POMitems.MERCURY_INGOT.get());
        elementItem(POMitems.THALLIUM_INGOT.get());
        elementItem(POMitems.LEAD_INGOT.get());
        elementItem(POMitems.BISMUTH_INGOT.get());
        elementItem(POMitems.POLONIUM_INGOT.get());
        elementItem(POMitems.FRANCIUM_INGOT.get());
        elementItem(POMitems.RADIUM_INGOT.get());
        elementItem(POMitems.ACTINIUM_INGOT.get());
        elementItem(POMitems.THORIUM_INGOT.get());
        elementItem(POMitems.PROTACTINIUM_INGOT.get());
        elementItem(POMitems.NEPTUNIUM_INGOT.get());
        elementItem(POMitems.AMERICIUM_INGOT.get());
        elementItem(POMitems.CURIUM_INGOT.get());
        elementItem(POMitems.BERKELIUM_INGOT.get());
        elementItem(POMitems.CALIFORNIUM_INGOT.get());
        elementItem(POMitems.EINSTEINIUM_INGOT.get());



        simpleItem(POMitems.BIT_PIXEL.get());


        simpleBlock(POMblocks.TITANIUM_BLOCK.get());
        simpleBlock(POMblocks.ENDSTONE_TITANIUM_ORE.get());
        simpleBlock(POMblocks.DEEPSLATE_TITANIUM_ORE.get());
        simpleBlock(POMblocks.TITANIUM_ORE.get());
        simpleBlock(POMblocks.RAW_TITANIUM_BLOCK.get());
        simpleBlock(POMblocks.TITANIUM_DIBORIDE_BLOCK.get());
        simpleBlock(POMblocks.HEAT_RESISTANT_CASING.get());

        simpleBlock(POMblocks.SMOOTH_STONE_BRICKS.get());

        complexBlock(POMblocks.PIXEL_SPLITTER.get());


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
}
