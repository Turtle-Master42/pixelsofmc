package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.Util;


public class POMitemModelProvider extends ItemModelProvider {
    public POMitemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for(Element e : Element.validValues()) {
            createElementModels(e);
            createDustModels(e);
            createNuggetModels(e);
            createAtomModels(e);
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
        simpleItem(POMitems.PERFECT_DIAMOND);
        simpleItem(POMitems.VIOLET_DIAMOND);
        simpleItem(POMitems.RED_DIAMOND);
        simpleItem(POMitems.BROWN_DIAMOND);
        simpleItem(POMitems.ORANGE_DIAMOND);
        simpleItem(POMitems.YELLOW_DIAMOND);
        simpleItem(POMitems.GREEN_DIAMOND);
        simpleItem(POMitems.DARK_GREEN_DIAMOND);
        simpleItem(POMitems.BLUE_DIAMOND);
        simpleItem(POMitems.PINK_DIAMOND);

        simpleItem(POMitems.COPPER_WIRE);
        simpleItem(POMitems.SILVER_WIRE);
        simpleItem(POMitems.TUNGSTEN_WIRE);
        simpleItem(POMitems.REDSTONE_LAYERED_COPPER_WIRE);
        simpleItem(POMitems.RED_SILVER_WIRE);
        simpleItem(POMitems.ROYAL_TUNGSTEN_WIRE);
        simpleItem(POMitems.SUPERCONDUCTIVE_WIRE);
        simpleItem(POMitems.REDSTONE_CORE);
        simpleItem(POMitems.CRUDE_POWER_CORE);
        simpleItem(POMitems.POWER_ORB);
        simpleItem(POMitems.OVERCHARGED_POWER_ORB);
        simpleItem(POMitems.SUPERCHARGED_POWER_ORB);
        simpleItem(POMitems.URANIUM_FUEL_PELLET);
        simpleItem(POMitems.URANIUM_FUEL_CORE);
        simpleItem(POMitems.PLUTONIUM_FUEL_PELLET);
        simpleItem(POMitems.PLUTONIUM_FUEL_CORE);
        simpleItem(POMitems.EMPTY_CELL);
        simpleItem(POMitems.MICRO_CHIP);
        simpleItem(POMitems.IMPROVED_MICRO_CHIP);
        simpleItem(POMitems.REDSTONE_COUNTER);
        simpleItem(POMitems.REDSTONE_TIMER);
        simpleItem(POMitems.REDSTONE_DETECTOR);
        simpleItem(POMitems.DRAGON_EYE);
        simpleItem(POMitems.VOID_EYE);
        simpleItem(POMitems.ENDER_SENSOR);
        simpleItem(POMitems.DRAGON_SENSOR);
        simpleItem(POMitems.VOID_SENSOR);
        simpleItem(POMitems.DIAMOND_LENS);
        simpleItem(POMitems.VIOLET_DIAMOND_LENS);
        simpleItem(POMitems.RED_DIAMOND_LENS);
        simpleItem(POMitems.FUSION_LINING);
        simpleItem(POMitems.FUSION_PLATING);
        simpleItem(POMitems.EMPTY_FUEL_CELL);
        simpleItem(POMitems.YELLOWCAKE_URANIUM);
        simpleItem(POMitems.URANIUM_FUEL_CELL);
        simpleItem(POMitems.ENRICHED_URANIUM_FUEL_CELL);
        simpleItem(POMitems.PLUTONIUM_FUEL_CELL);
        simpleItem(POMitems.ENRICHED_PLUTONIUM_FUEL_CELL);
        simpleItem(POMitems.DEPLETED_URANIUM_FUEL_CELL);
        simpleItem(POMitems.DEPLETED_ENRICHED_URANIUM_FUEL_CELL);
        simpleItem(POMitems.DEPLETED_PLUTONIUM_FUEL_CELL);
        simpleItem(POMitems.DEPLETED_ENRICHED_PLUTONIUM_FUEL_CELL);
        simpleItem(POMitems.INFINITE_POWER_CELL);
        simpleItem(POMitems.CARBON_HEAT_SINK);

        simpleItem(POMitems.SIMPLE_CIRCUIT_BOARD_1);
        simpleItem(POMitems.ADVANCED_CIRCUIT_BOARD_1);
        simpleItem(POMitems.PERFECTED_CIRCUIT_BOARD_1);
        simpleItem(POMitems.SIMPLE_CIRCUIT_BOARD_2);
        simpleItem(POMitems.ADVANCED_CIRCUIT_BOARD_2);
        simpleItem(POMitems.PERFECTED_CIRCUIT_BOARD_2);

        simpleItem(POMitems.SOUL_COAL);
        simpleItem(POMitems.PYROLYTIC_CARBON);
        simpleItem(POMitems.RAW_TITANIUM);
        simpleItem(POMitems.TITANIUM_GOLD_INGOT);
        simpleItem(POMitems.TITANIUM_DIBORIDE_INGOT);
        simpleItem(POMitems.SUPERCONDUCTIVE_INGOT);
        simpleItem(POMitems.RED_SILVER_INGOT);
        simpleItem(POMitems.ROYAL_TUNGSTEN_INGOT);
        simpleItem(POMitems.ROYAL_TUNGSTEN_AMALGAMATION);
        simpleItem(POMitems.LITHIUM_FLUORIDE_CLUMP);
        simpleItem(POMitems.MANA_AMALGAMATION);
        simpleItem(POMitems.MANA_SPHERE);
        simpleItem(POMitems.CRIMSON_MANA_SPHERE);
        simpleItem(POMitems.GLEAMING_MANA_SPHERE);

        simpleItem(POMitems.NETHERITE_NUGGET);
        simpleItem(POMitems.TITANIUM_GOLD_NUGGET);
        simpleItem(POMitems.TITANIUM_DIBORIDE_NUGGET);
        simpleItem(POMitems.SUPERCONDUCTIVE_NUGGET);
        simpleItem(POMitems.RED_SILVER_NUGGET);
        simpleItem(POMitems.ROYAL_TUNGSTEN_NUGGET);

        simpleItem(POMitems.TITANIUM_PLATING);
        simpleItem(POMitems.RUSTED_PLATING);
        simpleItem(POMitems.NETHERITE_PLATING);
        simpleItem(POMitems.TITANIUM_GOLD_PLATING);
        simpleItem(POMitems.TITANIUM_DIBORIDE_PLATING);
        simpleItem(POMitems.OBSIDIAN_PLATING);
        simpleItem(POMitems.CRYING_OBSIDIAN_PLATING);
        simpleItem(POMitems.LEAD_PLATING);
        simpleItem(POMitems.TUNGSTEN_PLATING);
        simpleItem(POMitems.PYROLYTIC_CARBON_SHEET);
        simpleItem(POMitems.SILICON_SHEET);

        simpleItem(POMitems.MERCURY_BUCKET);
        simpleItem(POMitems.SULFURIC_ACID_BUCKET);
        simpleItem(POMitems.LIQUID_HYDROGEN_BUCKET);
        simpleItem(POMitems.LIQUID_NITROGEN_BUCKET);
        simpleItem(POMitems.LIQUID_OXYGEN_BUCKET);
        simpleItem(POMitems.LIQUID_CHLORINE_BUCKET);
        simpleItem(POMitems.LIQUID_BROMINE_BUCKET);
        simpleItem(POMitems.HYDROGEN_GAS_BUCKET);
        simpleItem(POMitems.NITROGEN_GAS_BUCKET);
        simpleItem(POMitems.OXYGEN_GAS_BUCKET);
        simpleItem(POMitems.CHLORINE_GAS_BUCKET);
        simpleItem(POMitems.BROMINE_GAS_BUCKET);
        simpleItem(POMitems.STEAM_BUCKET);
        simpleItem(POMitems.BLAZING_STEAM_BUCKET);
        simpleItem(POMitems.AMMONIA_GAS_BUCKET);
        simpleItem(POMitems.NITRIC_ACID_BUCKET);
        simpleItem(POMitems.PUREX_SOLUTION_BUCKET);
        simpleItem(POMitems.NUCLEAR_WASTE_BUCKET);
        simpleItem(POMitems.NUCLEAR_WASTE_SOLUTION_BUCKET);
        simpleItem(POMitems.URANIUM_SOLUTION_BUCKET);
        simpleItem(POMitems.PLUTONIUM_SOLUTION_BUCKET);
        simpleItem(POMitems.RED_OIL_BUCKET);
        simpleItem(POMitems.LIQUID_LEAD_BUCKET);
        simpleItem(POMitems.LEAD_GAS_BUCKET);
        simpleItem(POMitems.MERCURY_GAS_BUCKET);
        simpleItem(POMitems.DIRTY_WATER_BUCKET);
        simpleItem(POMitems.HYDROFLUORIC_ACID_BUCKET);
        simpleItem(POMitems.URANIUM_HEXAFLUORIDE_GAS_BUCKET);
        simpleItem(POMitems.ENRICHED_URANIUM_SOLUTION_BUCKET);
        simpleItem(POMitems.FLUORINE_GAS_BUCKET);
        simpleItem(POMitems.ENRICHED_PLUTONIUM_SOLUTION_BUCKET);

        simpleItem(POMitems.ADVANCED_LASER);
        simpleItem(POMitems.TITANIUM_CIRCLE_SAW);
        simpleItem(POMitems.TITANIUM_GOLD_CIRCLE_SAW);
        simpleItem(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW);
        simpleItem(POMitems.INGOT_CAST);
        simpleItem(POMitems.BALL_CAST);
        simpleItem(POMitems.PLATE_CAST);

        simpleItem(POMitems.TITANIUM_UPGRADE_TEMPLATE);
        simpleItem(POMitems.SPEED_UPGRADE_1);
        simpleItem(POMitems.ENERGY_UPGRADE_1);
        simpleItem(POMitems.HEAT_UPGRADE_1);
        simpleItem(POMitems.TITANIUM_DIBORIDE_UPGRADE_TEMPLATE);
        simpleItem(POMitems.SPEED_UPGRADE_2);
        simpleItem(POMitems.ENERGY_UPGRADE_2);
        simpleItem(POMitems.HEAT_UPGRADE_2);

        handheldItem(POMitems.CLEANING_CLOTH);
        handheldItem(POMitems.SCREWDRIVER);
        handheldItem(POMitems.WIRECUTTER);
        handheldItem(POMitems.HAMMER);

        simpleItem(POMitems.RUBBER_BALL);
        simpleItem(POMitems.FIRE_PROOF_RUBBER_BALL);
        simpleItem(POMitems.REPELLING_RUBBER_BALL);
        simpleItem(POMitems.NETHERITE_BALL);
        simpleItem(POMitems.TITANIUM_BALL);
        simpleItem(POMitems.TITANIUM_GOLD_BALL);
        simpleItem(POMitems.TITANIUM_DIBORIDE_BALL);

        dustItem(POMitems.COAL_DUST);
        dustItem(POMitems.QUARTZ_DUST);
        dustItem(POMitems.TITANIUM_DIBORIDE_DUST);
        dustItem(POMitems.ANCIENT_DEBRIS_DUST);
        dustItem(POMitems.MERCURY_SULFIDE_DUST);
        dustItem(POMitems.ACANTHITE_DUST);
        dustItem(POMitems.OBSIDIAN_DUST);
        dustItem(POMitems.CRYING_OBSIDIAN_DUST);
        dustItem(POMitems.NETHERITE_DUST);
        dustItem(POMitems.TITANIUM_GOLD_DUST);
        dustItem(POMitems.TITANIUM_OXIDE_DUST);
        dustItem(POMitems.SUPERCONDUCTIVE_DUST);
        dustItem(POMitems.RED_SILVER_DUST);
        dustItem(POMitems.ROYAL_TUNGSTEN_DUST);
        dustItem(POMitems.REFINED_REDSTONE);
        dustItem(POMitems.PLUTONIUM_TETRAFLUORIDE_DUST);
        dustItem(POMitems.PLUTONIUM_HEXAFLUORIDE_DUST);
        dustItem(POMitems.ENRICHED_PLUTONIUM_HEXAFLUORIDE_DUST);

        simpleItem(POMitems.TEST_ITEM);
        simpleItem(POMitems.PLACE_HOLDER);
        simpleItem(POMitems.DEBUGIUM_INGOT);

        complexBlock(POMblocks.COPPER_SPOOL);
        complexBlock(POMblocks.SILVER_SPOOL);
        complexBlock(POMblocks.TUNGSTEN_SPOOL);
        complexBlock(POMblocks.REDSTONE_LAYERED_COPPER_SPOOL);
        complexBlock(POMblocks.RED_SILVER_SPOOL);
        complexBlock(POMblocks.ROYAL_TUNGSTEN_SPOOL);
        complexBlock(POMblocks.SUPERCONDUCTIVE_SPOOL);

        complexBlock(POMblocks.POWER_CELL_ARRAY);
        complexBlock(POMblocks.OVERCHARGED_POWER_CELL_ARRAY);
        complexBlock(POMblocks.SUPERCHARGED_POWER_CELL_ARRAY);

        complexBlock(POMblocks.PIXEL_SPLITTER);
        complexBlock(POMblocks.GRINDER);
        complexBlock(POMblocks.CHEMICAL_MIXER);
        complexBlock(POMblocks.SDS_CONTROLLER);
        complexBlock(POMblocks.PIXEL_BOMBARDER);


        complexBlock(POMblocks.FUSION_CASING);
        complexBlock(POMblocks.FUSION_CORNER);
        complexBlock(POMblocks.FUSION_ENERGY_PORT);
        complexBlock(POMblocks.FUSION_FLUID_PORT);
        complexBlock(POMblocks.FUSION_ITEM_PORT);
        complexBlock(POMblocks.FUSION_PLASMA_PORT);
        complexBlock(POMblocks.HEAT_SINK);
        complexBlock(POMblocks.SUPERCONDUCTIVE_FUSION_CASING);

        complexBlock(POMblocks.FUEL_CELL_HOLDER);
        complexBlock(POMblocks.NUCLEAR_REACTOR);
        complexBlock(POMblocks.INDUSTRIAL_HEAT_EXCHANGER);
        complexBlock(POMblocks.INDUSTRIAL_TURBINE);
        complexBlock(POMblocks.FISSION_FLUID_PORT);
        complexBlock(POMblocks.FISSION_ENERGY_PORT);
        complexBlock(POMblocks.FLUID_PORT);
        complexBlock(POMblocks.ENERGY_PORT);
        complexBlock(POMblocks.ITEM_PORT);

        complexBlock(POMblocks.TITANIUM_PLATING_SLAB);
        complexBlock(POMblocks.TITANIUM_PLATING_STAIRS);
        complexBlock(POMblocks.NETHERITE_PLATING_SLAB);
        complexBlock(POMblocks.NETHERITE_PLATING_STAIRS);
        complexBlock(POMblocks.TITANIUM_GOLD_PLATING_SLAB);
        complexBlock(POMblocks.TITANIUM_GOLD_PLATING_STAIRS);
        complexBlock(POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB);
        complexBlock(POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS);
        complexBlock(POMblocks.LEAD_PLATING_SLAB);
        complexBlock(POMblocks.LEAD_PLATING_STAIRS);
        complexBlock(POMblocks.TUNGSTEN_PLATING_SLAB);
        complexBlock(POMblocks.TUNGSTEN_PLATING_STAIRS);
        complexBlock(POMblocks.PYROLYTIC_CARBON_SHEET_SLAB);
        complexBlock(POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS);
        complexBlock(POMblocks.MACHINE_CASING_STAIRS);
        complexBlock(POMblocks.MACHINE_CASING_SLAB);
        complexBlock(POMblocks.FISSION_CASING_STAIRS);
        complexBlock(POMblocks.FISSION_CASING_SLAB);
        complexBlock(POMblocks.ARMORED_MACHINE_CASING_STAIRS);
        complexBlock(POMblocks.ARMORED_MACHINE_CASING_SLAB);
        complexBlock(POMblocks.MACHINE_COIL);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                Util.resourceLocation("minecraft", "item/generated")).texture("layer0",
                Util.resourceLocation("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                Util.resourceLocation("minecraft", "item/generated")).texture("layer0",
                Util.resourceLocation("block/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                Util.resourceLocation("minecraft", "item/handheld")).texture("layer0",
                Util.resourceLocation("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder elementItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                Util.resourceLocation("minecraft", "item/generated")).texture("layer0",
                Util.resourceLocation("item/elements/" + item.getId().getPath()));
    }

    private ItemModelBuilder dustItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                Util.resourceLocation("minecraft", "item/generated")).texture("layer0",
                Util.resourceLocation("item/dusts/" + item.getId().getPath()));
    }

    private ItemModelBuilder atomItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                Util.resourceLocation("minecraft", "item/generated")).texture("layer0",
                Util.resourceLocation("item/atoms/" + item.getId().getPath()));
    }

    private ItemModelBuilder atom64Item(Element element) {
        return withExistingParent(POMitems.Elements.ATOMX64.get(element).getId().getPath(), Util.resourceLocation("minecraft", "item/generated"))
                .texture("layer0", Util.resourceLocation("item/atom/" + element.elementName()+"_atom"))
                .texture("layer1", Util.resourceLocation("item/atom/atom_64"));
    }

    private ItemModelBuilder atom512Item(Element element) {
        return withExistingParent(POMitems.Elements.ATOMX512.get(element).getId().getPath(), Util.resourceLocation("minecraft", "item/generated"))
                .texture("layer0", Util.resourceLocation("item/atom/" + element.elementName()+"_atom"))
                .texture("layer1", Util.resourceLocation("item/atom/atom_512"));
    }

    private ItemModelBuilder isotope64Item(String path, RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), Util.resourceLocation("minecraft", "item/generated"))
                .texture("layer0", Util.resourceLocation("item/atom/" + path))
                .texture("layer1", Util.resourceLocation("item/atom/isotope_64"));
    }

    private ItemModelBuilder isotope512Item(String path, RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), Util.resourceLocation("minecraft", "item/generated"))
                .texture("layer0", Util.resourceLocation("item/atom/" + path))
                .texture("layer1", Util.resourceLocation("item/atom/isotope_512"));
    }

    private ItemModelBuilder simpleBlock(RegistryObject<Block> block) {
        return cubeAll(block.getId().getPath(),
                Util.resourceLocation("block/" + block.getId().getPath()));
    }
    private ItemModelBuilder complexBlock(RegistryObject<?> block) {
        return withExistingParent(block.getId().getPath(), Util.resourceLocation(
                "block/" + block.getId().getPath()));
    }

    private void createElementModels(Element element) {
        if (!element.isVanilla())
            elementItem(POMitems.Elements.ELEMENTS.get(element));
    }
    private void createDustModels(Element element) {
        if (element.shouldAddDust())
            dustItem(POMitems.Elements.DUSTS.get(element));
    }
    private void createNuggetModels(Element element) {
        if (element.shouldAddNugget())
            simpleItem(POMitems.Elements.NUGGETS.get(element));
    }
    private void createAtomModels(Element element) {
        atom64Item(element);
        atom512Item(element);

        if (element.equals(Element.HYDROGEN)) {
            isotope64Item("deuterium_atom", POMitems.Elements.ISOTOPEX64.get("HYDROGEN_2"));
            isotope512Item("deuterium_atom", POMitems.Elements.ISOTOPEX512.get("HYDROGEN_2"));
            isotope64Item("tritium_atom", POMitems.Elements.ISOTOPEX64.get("HYDROGEN_3"));
            isotope512Item("tritium_atom", POMitems.Elements.ISOTOPEX512.get("HYDROGEN_3"));
            return;
        }

        for (int i = 0; i < element.getIsotopes().getNeutrons().length; i++) {
            isotope64Item(element.elementName()+"_atom", POMitems.Elements.ISOTOPEX64.get(element+"_"+(element.getIsotopes().getNeutrons()[i] + element.getElement())));
            isotope512Item(element.elementName()+"_atom", POMitems.Elements.ISOTOPEX512.get(element+"_"+(element.getIsotopes().getNeutrons()[i] + element.getElement())));
        }
    }
}
