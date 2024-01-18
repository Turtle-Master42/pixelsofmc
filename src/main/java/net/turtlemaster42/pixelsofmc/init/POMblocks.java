package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.*;
import net.turtlemaster42.pixelsofmc.block.dummy.DummyMachineBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.DummyMachineEnergyBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.DummyMachineItemBlock;
import net.turtlemaster42.pixelsofmc.fluid.AcidLiquidBlock;
import net.turtlemaster42.pixelsofmc.fluid.SupercooledLiquidBlock;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class POMblocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PixelsOfMc.MOD_ID);



    //fluids
    public static final RegistryObject<LiquidBlock> MERCURY_BLOCK = POMblocks.BLOCKS.register("mercury",
            () -> new LiquidBlock(POMfluids.MERCURY_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.TERRACOTTA_BLUE)));
    public static final RegistryObject<LiquidBlock> HYDROGEN_BLOCK = POMblocks.BLOCKS.register("hydrogen",
            () -> new SupercooledLiquidBlock(POMfluids.HYDROGEN_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.SNOW)));
    public static final RegistryObject<LiquidBlock> NITROGEN_BLOCK = POMblocks.BLOCKS.register("nitrogen",
            () -> new SupercooledLiquidBlock(POMfluids.NITROGEN_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryObject<LiquidBlock> OXYGEN_BLOCK = POMblocks.BLOCKS.register("oxygen",
            () -> new SupercooledLiquidBlock(POMfluids.OXYGEN_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.LAPIS)));
    public static final RegistryObject<LiquidBlock> CHLORINE_BLOCK = POMblocks.BLOCKS.register("chlorine",
            () -> new SupercooledLiquidBlock(POMfluids.CHLORINE_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.TERRACOTTA_LIGHT_GREEN)));
    public static final RegistryObject<LiquidBlock> BROMINE_BLOCK = POMblocks.BLOCKS.register("bromine",
            () -> new SupercooledLiquidBlock(POMfluids.BROMINE_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.TERRACOTTA_PINK)));
    public static final RegistryObject<LiquidBlock> SULFURIC_ACID_BLOCK = POMblocks.BLOCKS.register("sulfuric_acid",
            () -> new AcidLiquidBlock(POMfluids.SULFURIC_ACID_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.TERRACOTTA_YELLOW)));

    //gas
    public static final RegistryObject<LiquidBlock> HYDROGEN_GAS_BLOCK = POMblocks.BLOCKS.register("hydrogen_gas",
            () -> new LiquidBlock(POMfluids.HYDROGEN_GAS_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.SNOW)));
    public static final RegistryObject<LiquidBlock> NITROGEN_GAS_BLOCK = POMblocks.BLOCKS.register("nitrogen_gas",
            () -> new LiquidBlock(POMfluids.NITROGEN_GAS_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryObject<LiquidBlock> OXYGEN_GAS_BLOCK = POMblocks.BLOCKS.register("oxygen_gas",
            () -> new LiquidBlock(POMfluids.OXYGEN_GAS_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.LAPIS)));
    public static final RegistryObject<LiquidBlock> CHLORINE_GAS_BLOCK = POMblocks.BLOCKS.register("chlorine_gas",
            () -> new LiquidBlock(POMfluids.CHLORINE_GAS_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.TERRACOTTA_LIGHT_GREEN)));
    public static final RegistryObject<LiquidBlock> BROMINE_GAS_BLOCK = POMblocks.BLOCKS.register("bromine_gas",
            () -> new LiquidBlock(POMfluids.BROMINE_GAS_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.TERRACOTTA_PINK)));


    public static final RegistryObject<Block> MACHINE_BLOCK = BLOCKS.register("machine_block", DummyMachineBlock::new);
    public static final RegistryObject<Block> MACHINE_ENERGY_BLOCK = BLOCKS.register("machine_energy_block", DummyMachineEnergyBlock::new);
    public static final RegistryObject<Block> MACHINE_ITEM_BLOCK = BLOCKS.register("machine_item_block", DummyMachineItemBlock::new);




    public static final RegistryObject<Block> SIMPLE_CASING_1 = registerBlock("simple_casing_1",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY)
                    .strength(6f, 12f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ADVANCED_CASING_1 = registerBlock("advanced_casing_1",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY)
                    .strength(6f, 12f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PERFECTED_CASING_1 = registerBlock("perfected_casing_1",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY)
                    .strength(6f, 12f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SIMPLE_CASING_2 = registerBlock("simple_casing_2",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_YELLOW)
                    .strength(10f, 24f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ADVANCED_CASING_2 = registerBlock("advanced_casing_2",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_YELLOW)
                    .strength(10f, 24f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PERFECTED_CASING_2 = registerBlock("perfected_casing_2",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_YELLOW)
                    .strength(10f, 24f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> POWER_CELL_ARRAY = registerBlock("power_cell_array",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(5.0F, 25.0F)));
    public static final RegistryObject<Block> OVERCHARGED_POWER_CELL_ARRAY = registerBlock("overcharged_power_cell_array",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(5.0F, 25.0F)));
    public static final RegistryObject<Block> SUPERCHARGED_POWER_CELL_ARRAY = registerBlock("supercharged_power_cell_array",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(5.0F, 25.0F)));


    public static final RegistryObject<Block> TITANIUM_PLATING_BLOCK = registerBlock("titanium_plating_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY).strength(4f, 10f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_PLATING_STAIRS = registerBlock("titanium_plating_stairs",
            () -> new StairBlock(() -> TITANIUM_PLATING_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY).strength(4f, 10f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_PLATING_SLAB = registerBlock("titanium_plating_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY).strength(4f, 10f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHERITE_PLATING_BLOCK = registerBlock("netherite_plating_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(25.0F, 1200.0F)));
    public static final RegistryObject<Block> NETHERITE_PLATING_STAIRS = registerBlock("netherite_plating_stairs",
            () -> new StairBlock(() -> NETHERITE_PLATING_BLOCK.get().defaultBlockState() ,BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(25.0F, 1200.0F)));
    public static final RegistryObject<Block> NETHERITE_PLATING_SLAB = registerBlock("netherite_plating_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(25.0F, 1200.0F)));

    public static final RegistryObject<Block> TITANIUM_GOLD_PLATING_BLOCK = registerBlock("titanium_gold_plating_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_YELLOW).strength(10.0F, 50.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_GOLD_PLATING_STAIRS = registerBlock("titanium_gold_plating_stairs",
            () -> new StairBlock(() -> TITANIUM_GOLD_PLATING_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_YELLOW).strength(10.0F, 50.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_GOLD_PLATING_SLAB = registerBlock("titanium_gold_plating_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_YELLOW).strength(10.0F, 50.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LEAD_PLATING_BLOCK = registerBlock("lead_plating_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_BLUE).strength(10.0F, 50.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LEAD_PLATING_STAIRS = registerBlock("lead_plating_stairs",
            () -> new StairBlock(() -> LEAD_PLATING_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_BLUE).strength(10.0F, 50.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LEAD_PLATING_SLAB = registerBlock("lead_plating_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_BLUE).strength(10.0F, 50.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TUNGSTEN_PLATING_BLOCK = registerBlock("tungsten_plating_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_CYAN).strength(16.0F, 75F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TUNGSTEN_PLATING_STAIRS = registerBlock("tungsten_plating_stairs",
            () -> new StairBlock(() -> TUNGSTEN_PLATING_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_CYAN).strength(16.0F, 75F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TUNGSTEN_PLATING_SLAB = registerBlock("tungsten_plating_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_CYAN).strength(16.0F, 75F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> PYROLYTIC_CARBON_SHEET_BLOCK = registerBlock("pyrolytic_carbon_sheet_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(2.0F, 8F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PYROLYTIC_CARBON_SHEET_STAIRS = registerBlock("pyrolytic_carbon_sheet_stairs",
            () -> new StairBlock(() -> PYROLYTIC_CARBON_SHEET_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(2.0F, 8F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PYROLYTIC_CARBON_SHEET_SLAB = registerBlock("pyrolytic_carbon_sheet_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(2.0F, 8F).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> TITANIUM_DIBORIDE_PLATING_BLOCK = registerBlock("titanium_diboride_plating_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_GRAY).strength(18.0F, 1200.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_DIBORIDE_PLATING_STAIRS = registerBlock("titanium_diboride_plating_stairs",
            () -> new StairBlock(() -> TITANIUM_DIBORIDE_PLATING_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_GRAY).strength(18.0F, 1200.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_DIBORIDE_PLATING_SLAB = registerBlock("titanium_diboride_plating_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_GRAY).strength(18.0F, 1200.0F).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE)
                    .strength(5f, 6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE)
                    .strength(5.5f, 6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ENDSTONE_TITANIUM_ORE = registerBlock("endstone_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).sound(SoundType.STONE)
                    .strength(5f, 10f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ACANTHITE = registerBlock("acanthite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.LAPIS).strength(2.0F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RAW_TITANIUM_BLOCK = registerBlock("raw_titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY)
                    .strength(5f, 10f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ALUMINIUM_SCRAP_BLOCK = registerBlock("aluminium_scrap_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.CLAY).sound(SoundType.ANCIENT_DEBRIS)
                    .strength(4f, 6.0F).requiresCorrectToolForDrops()) );

    public static final RegistryObject<Block> TITANIUM_GOLD_BLOCK = registerBlock("titanium_gold_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_YELLOW).sound(SoundType.NETHERITE_BLOCK)
                    .strength(12f, 50f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TITANIUM_DIBORIDE_BLOCK = registerBlock("titanium_diboride_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_GRAY).sound(SoundType.NETHERITE_BLOCK)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), true);

    public static final RegistryObject<Block> VIOLET_DIAMOND_BLOCK = registerBlock("violet_diamond_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE)
                    .strength(7f, 10f).requiresCorrectToolForDrops().sound(SoundType.METAL)));


    public static final RegistryObject<RotatedPillarBlock> COPPER_SPOOL = registerBlock("copper_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<RotatedPillarBlock> SILVER_SPOOL = registerBlock("silver_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).color(MaterialColor.QUARTZ)));
    public static final RegistryObject<RotatedPillarBlock> TUNGSTEN_SPOOL = registerBlock("tungsten_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).color(MaterialColor.WARPED_STEM)));

    public static final RegistryObject<RotatedPillarBlock> REDSTONE_LAYERED_COPPER_SPOOL = registerBlock("redstone_layered_copper_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryObject<RotatedPillarBlock> REDSTONE_IMBUED_SILVER_SPOOL = registerBlock("redstone_imbued_silver_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).color(MaterialColor.COLOR_RED)));
    public static final RegistryObject<RotatedPillarBlock> RED_TUNGSTEN_SPOOL = registerBlock("red_tungsten_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).color(MaterialColor.TERRACOTTA_MAGENTA)));
    public static final RegistryObject<RotatedPillarBlock> SUPERCONDUCTIVE_SPOOL = registerBlock("superconductive_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).color(MaterialColor.DIAMOND)));



    public static final RegistryObject<Block> STAR = registerBlock("star",
            () -> new StarBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.COLOR_ORANGE)
                    .noOcclusion()));




    public static final RegistryObject<Block> GRINDER = registerBlock("grinder",
            () -> new GrinderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.CLAY)
                    .noOcclusion()), "tooltip.pixelsofmc.block.grinder.shift", "", "tooltip.pixelsofmc.block.grinder.alt");
    public static final RegistryObject<Block> BALL_MILL = registerBlock("ball_mill",
            () -> new BallMillBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.CLAY)
                    .noOcclusion()), "tooltip.pixelsofmc.block.ball_mill.shift", "", "tooltip.pixelsofmc.block.ball_mill.alt");
    public static final RegistryObject<Block> HOT_ISOSTATIC_PRESS = registerBlock("hot_isostatic_press",
            () -> new HotIsostaticPressBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.CLAY)
                    .noOcclusion()), "tooltip.pixelsofmc.block.hot_isostatic_press.shift", "", "tooltip.pixelsofmc.block.hot_isostatic_press.alt");
    public static final RegistryObject<Block> CHEMICAL_SEPARATOR = registerBlock("chemical_separator",
            () -> new ChemicalSeparatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.COLOR_GRAY)
                    .noOcclusion()), "tooltip.pixelsofmc.block.chemical_separator.shift", "", "");
    public static final RegistryObject<Block> CHEMICAL_COMBINER = registerBlock("chemical_combiner",
            () -> new ChemicalCombinerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.COLOR_GRAY)
                    .noOcclusion()), "tooltip.pixelsofmc.block.chemical_combiner.shift", "", "");
    public static final RegistryObject<Block> PIXEL_SPLITTER = registerBlock("pixel_splitter",
            () -> new PixelSplitterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.CLAY)
                    .noOcclusion()), "tooltip.pixelsofmc.block.pixel_splitter.shift", "", "");



    public static final RegistryObject<Block> STRONG_CASING = registerBlock("strong_casing",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK)
                    .strength(25f, 1200f).requiresCorrectToolForDrops()), true);
    public static final RegistryObject<Block> STRENGTHENED_CASING = registerBlock("strengthened_casing",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_YELLOW).sound(SoundType.NETHERITE_BLOCK)
                    .strength(27f, 1200f).requiresCorrectToolForDrops()), true);
    public static final RegistryObject<Block> REINFORCED_CASING = registerBlock("reinforced_casing",
            () -> new AbstractFusionCasing(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_GRAY).sound(SoundType.NETHERITE_BLOCK)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), true);
    public static final RegistryObject<AbstractFusionCasing> REINFORCED_GLASS = registerBlock("reinforced_glass",
            () -> new ReinforcedGlass(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get()).noOcclusion().sound(SoundType.GLASS).destroyTime(15f)));

    public static final RegistryObject<AbstractMultiBlock> REINFORCED_THING = registerBlock("reinforced_thing",
            () -> new AbstractMultiBlock(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get())));

    public static final RegistryObject<AbstractFusionCasing> FUSION_CASING = registerBlock("fusion_casing",
            () -> new AbstractPillarFusionCasing(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get()).destroyTime(20f)));
    public static final RegistryObject<AbstractFusionCasing> SUPERCONDUCTIVE_FUSION_CASING = registerBlock("superconductive_fusion_casing",
            () -> new AbstractPillarFusionCasing(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get()).destroyTime(20f)));
    public static final RegistryObject<AbstractFusionCasing> FUSION_CORNER = registerBlock("fusion_corner",
            () -> new AbstractFusionCasing(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get()).destroyTime(20f)));
    public static final RegistryObject<FusionEnergyPortBlock> FUSION_ENERGY_PORT = registerBlock("fusion_energy_port",
            () -> new FusionEnergyPortBlock(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get()).destroyTime(20f)));
    public static final RegistryObject<FusionItemPortBlock> FUSION_ITEM_PORT = registerBlock("fusion_item_port",
            () -> new FusionItemPortBlock(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get()).destroyTime(20f)));




    public static final RegistryObject<Block> SDS_CONTROLLER = registerBlock("sds_controller",
            () -> new SDSFusionControllerBlock(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get())
                    .noOcclusion()), "tooltip.pixelsofmc.block.sds_controller.shift", "", "tooltip.pixelsofmc.block.sds_controller.alt");
    public static final RegistryObject<Block> MDS_CONTROLLER = registerBlock("mds_controller",
            () -> new MDSFusionControllerBlock(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get())
                    .noOcclusion()), "tooltip.pixelsofmc.block.mds_controller.shift", "", "tooltip.pixelsofmc.block.mds_controller.alt");
    public static final RegistryObject<Block> MNS_CONTROLLER = registerBlock("mns_controller",
            () -> new MNSFusionControllerBlock(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get())
                    .noOcclusion()), "tooltip.pixelsofmc.block.mns_controller.shift", "", "tooltip.pixelsofmc.block.mns_controller.alt");
    public static final RegistryObject<Block> BH_CONTROLLER = registerBlock("bh_controller",
            () -> new BHFusionControllerBlock(BlockBehaviour.Properties.copy(POMblocks.TITANIUM_DIBORIDE_BLOCK.get())
                    .noOcclusion()), "tooltip.pixelsofmc.block.bh_controller.shift", "", "tooltip.pixelsofmc.block.bh_controller.alt");


    public static final class Metals {
        public static final Map<Element, BlockRegObject<BaseBlock>> BLOCKS = new EnumMap<>(Element.class);
            private static void init() {
                for (Element m : Element.values()) {
                    if (m.shouldAddBlock()) {
                        String elementName = m.elementName();
                        BlockRegObject<BaseBlock> block;

                        BlockBehaviour.Properties properties = Block.Properties
                                .of(Material.METAL, MaterialColor.METAL)
                                .strength(6f, 6.0f)
                                .sound(SoundType.METAL)
                                .requiresCorrectToolForDrops();

                        block = register(elementName+"_block", () -> new BaseBlock(properties));

                        PixelsOfMc.LOGGER.info("Registered Metals Blocks");

                        BLOCKS.put(m, block);
                    }
                }
            }
        }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
        }
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, String tooltipKeyShift, String tooltipKeyCtrl, String tooltipKeyAlt) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tooltipKeyShift, tooltipKeyCtrl, tooltipKeyAlt);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, String shiftTooltip, String ctrlTooltip, String altTooltip) {
        return POMitems.BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()) {
            @Override
            public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
                if(Screen.hasShiftDown() && !shiftTooltip.isEmpty()) {
                    pTooltip.add(Component.translatable(shiftTooltip));
                } else if(Screen.hasControlDown() && !ctrlTooltip.isEmpty()) {
                    pTooltip.add(Component.translatable(ctrlTooltip));
                } else if(Screen.hasAltDown() && !altTooltip.isEmpty()) {
                    pTooltip.add(Component.translatable(altTooltip));
                } else {
                    if (!shiftTooltip.isEmpty())
                        pTooltip.add(Component.translatable("tooltip.pixelsofmc.noshift"));
                    if (!ctrlTooltip.isEmpty())
                        pTooltip.add(Component.translatable("tooltip.pixelsofmc.noctrl"));
                    if (!altTooltip.isEmpty())
                        pTooltip.add(Component.translatable("tooltip.pixelsofmc.noalt"));
                }
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return POMitems.BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean fireProof) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, fireProof);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Boolean fireProof) {
        if (fireProof) {
            return POMitems.BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(),
                    new Item.Properties().fireResistant()));
        } else {
            return POMitems.BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(),
                    new Item.Properties()));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        Metals.init();
    }

    //Immersive Engineering
    private static <T extends Block> BlockRegObject<T> register(String name, Supplier<? extends T> make) {
        return new BlockRegObject<>(BLOCKS.register(name, make));
    }

    public static class BlockRegObject<T extends Block> implements Supplier<T>, ItemLike {
        private final RegistryObject<T> regObject;
        //private final Supplier<BlockBehaviour.Properties> properties;
        private BlockRegObject(RegistryObject<T> regObject) {
            //this.properties = properties;
            this.regObject = regObject;
        }
        @Override
        public T get() {
            return regObject.get();
        }

        public BlockState defaultBlockState() {
            return get().defaultBlockState();
        }

        public ResourceLocation getId() {
            return regObject.getId();
        }

        @Nonnull
        @Override
        public Item asItem() {
            return get().asItem();
        }
    }


}
