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


public class POMitemModelProvider extends ItemModelProvider {
    public POMitemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        simpleItem(POMitems.BOOK_1.get());

        simpleItem(POMitems.BIO_COMPOUND.get());
        simpleItem(POMitems.BIO_PLASTIC.get());
        simpleItem(POMitems.FIRE_PROOF_COMPOUND.get());
        simpleItem(POMitems.FIRE_PROOF_PLASTIC.get());
        simpleItem(POMitems.REPELLING_COMPOUND.get());
        simpleItem(POMitems.REPELLING_PLASTIC.get());

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
        elementItem(POMitems.TITANIUM_INGOT.get());
        simpleItem(POMitems.STEEL_INGOT.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_INGOT.get());

        simpleItem(POMitems.COPPER_NUGGET.get());
        simpleItem(POMitems.SILVER_NUGGET.get());
        simpleItem(POMitems.STEEL_NUGGET.get());
        simpleItem(POMitems.TITANIUM_NUGGET.get());
        simpleItem(POMitems.NETHERITE_NUGGET.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_NUGGET.get());

        simpleItem(POMitems.TITANIUM_GEAR.get());
        simpleItem(POMitems.TITANIUM_PLATING.get());
        simpleItem(POMitems.RUSTED_PLATING.get());
        simpleItem(POMitems.NETHERITE_PLATING.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_PLATING.get());

        simpleItem(POMitems.MOVING_PARTS.get());
        simpleItem(POMitems.POWER_CELL.get());
        simpleItem(POMitems.ADVANCED_LASER.get());
        simpleItem(POMitems.TITANIUM_CIRCLE_SAW.get());
        simpleItem(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get());

        simpleItem(POMitems.SPEED_UPGRADE.get());
        simpleItem(POMitems.ENERGY_UPGRADE.get());

        handheldItem(POMitems.CLEANING_CLOTH.get());
        handheldItem(POMitems.SCREWDRIVER.get());
        handheldItem(POMitems.WIRECUTTER.get());
        handheldItem(POMitems.HAMMER.get());

        dustItem(POMitems.IRON_DUST.get());
        dustItem(POMitems.STEEL_DUST.get());
        dustItem(POMitems.COAL_DUST.get());
        dustItem(POMitems.TITANIUM_DUST.get());
        dustItem(POMitems.TITANIUM_DIBORIDE_DUST.get());
        dustItem(POMitems.URANIUM_DUST.get());

        dustItem(POMitems.ALUMINIUM_DUST.get());
        dustItem(POMitems.BORON_DUST.get());
        dustItem(POMitems.CALCIUM_DUST.get());
        dustItem(POMitems.GOLD_DUST.get());
        dustItem(POMitems.MERCURY_SULFIDE_DUST.get());
        dustItem(POMitems.POTASSIUM_DUST.get());
        dustItem(POMitems.SILICON_DUST.get());
        dustItem(POMitems.SODIUM_DUST.get());
        dustItem(POMitems.SULFUR_DUST.get());


        simpleItem(POMitems.SULFUR.get());



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
        elementItem(POMitems.URANIUM_INGOT.get());
        elementItem(POMitems.NEPTUNIUM_INGOT.get());
        elementItem(POMitems.AMERICIUM_INGOT.get());
        elementItem(POMitems.CURIUM_INGOT.get());
        elementItem(POMitems.BERKELIUM_INGOT.get());
        elementItem(POMitems.CALIFORNIUM_INGOT.get());
        elementItem(POMitems.EINSTEINIUM_INGOT.get());

        simpleItem(POMitems.PIXEL.get());


        
        simpleBlock(POMblocks.TITANIUM_BLOCK.get());
        simpleBlock(POMblocks.ENDSTONE_TITANIUM_ORE.get());
        simpleBlock(POMblocks.DEEPSLATE_TITANIUM_ORE.get());
        simpleBlock(POMblocks.TITANIUM_ORE.get());
        simpleBlock(POMblocks.RAW_TITANIUM_BLOCK.get());
        simpleBlock(POMblocks.TITANIUM_DIBORIDE_BLOCK.get());

        simpleBlock(POMblocks.SIMPLE_CASING_1.get());
        simpleBlock(POMblocks.ADVANCED_CASING_1.get());
        simpleBlock(POMblocks.PERFECTED_CASING_1.get());
        simpleBlock(POMblocks.STRONG_CASING.get());
        simpleBlock(POMblocks.REINFORCED_CASING.get());

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

    private ItemModelBuilder dustItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PixelsOfMc.MOD_ID,"items/dusts/" + item.getRegistryName().getPath()));
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
