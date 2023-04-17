package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class POMblocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PixelsOfMc.MOD_ID);

    public static final RegistryObject<Block> STRONG_CASING = registerBlock("strong_casing",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.NETHERITE_BLOCK)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), true);
    public static final RegistryObject<Block> REINFORCED_CASING = registerBlock("reinforced_casing",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.NETHERITE_BLOCK)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), true);



    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE)
                    .strength(5.5f, 6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f, 6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ENDSTONE_TITANIUM_ORE = registerBlock("endstone_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE)
                    .strength(5f, 10f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f, 12f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RAW_TITANIUM_BLOCK = registerBlock("raw_titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f, 10f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TITANIUM_DIBORIDE_BLOCK = registerBlock("titanium_diboride_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), true);

    public static final RegistryObject<Block> ALUMINIUM_SCRAP_BLOCK = registerBlock("aluminium_scrap_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.ANCIENT_DEBRIS)
                    .strength(4f, 6.0F).requiresCorrectToolForDrops()) );

    public static final RegistryObject<Block> SAND_MINERAL_DEPOSIT = registerBlock("sand_mineral_deposit",
            () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).sound(SoundType.SAND)
                    .strength(1f, 0.7F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ACANTHITE = registerBlock("acanthite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> MACHINE_BLOCK = BLOCKS.register("machine_block", DummyMachineBlock::new);
    public static final RegistryObject<Block> MACHINE_ENERGY_BLOCK = BLOCKS.register("machine_energy_block", DummyMachineEnergyBlock::new);
    public static final RegistryObject<Block> MACHINE_ITEM_BLOCK = BLOCKS.register("machine_item_block", DummyMachineItemBlock::new);

	public static final RegistryObject<Block> PIXEL_SPLITTER = registerBlock("pixel_splitter",
            () -> new PixelSplitterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), "tooltip.pixelsofmc.block.pixel_splitter.shift", "§7This block is just a normal block\nnothing special\nwy are you still reading this\nstop\nI said stop\nSTOP PLEASE!!!\ndon't do it\nit's not worth it\nNO\nNOOOOOOOO PLEASE!\nlook away\nit's for the better\nJUST STOP LOOKING", "");
    public static final RegistryObject<Block> BALL_MILL = registerBlock("ball_mill",
            () -> new BallMillBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), "tooltip.pixelsofmc.block.ball_mill.shift", "", "tooltip.pixelsofmc.block.ball_mill.alt");
    public static final RegistryObject<Block> GRINDER = registerBlock("grinder",
            () -> new GrinderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), "tooltip.pixelsofmc.block.grinder.shift", "", "tooltip.pixelsofmc.block.grinder.alt");
    public static final RegistryObject<Block> HOT_ISOSTATIC_PRESS = registerBlock("hot_isostatic_press",
            () -> new HotIsostaticPressBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), "tooltip.pixelsofmc.block.hot_isostatic_press.shift", "", "tooltip.pixelsofmc.block.hot_isostatic_press.alt");
    public static final RegistryObject<Block> CHEMICAL_SEPERATOR = registerBlock("chemical_seperator",
            () -> new ChemicalSeperatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), "tooltip.pixelsofmc.block.chemical_seperator.shift", "", "");




    public static final RegistryObject<Block> SDS_CONTROLLER = registerBlock("sds_controller",
            () -> new SDSFusionControllerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()));

    public static final RegistryObject<Block> FUSION_CORE = registerBlock("fusion_core",
            () -> new FusionCore(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()));


    public static final RegistryObject<Block> SIMPLE_CASING_1 = registerBlock("simple_casing_1",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f, 12f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ADVANCED_CASING_1 = registerBlock("advanced_casing_1",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f, 12f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PERFECTED_CASING_1 = registerBlock("perfected_casing_1",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f, 12f).requiresCorrectToolForDrops()));
    public static final RegistryObject<RotatedPillarBlock> COPPER_SPOOL = registerBlock("copper_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<RotatedPillarBlock> SILVER_SPOOL = registerBlock("silver_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<RotatedPillarBlock> TUNGSTEN_SPOOL = registerBlock("tungsten_spool",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));





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
        return POMitems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()) {
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
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
        return POMitems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean fireProof) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, fireProof);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Boolean fireProof) {
        if (fireProof) {
            return POMitems.ITEMS.register(name, () -> new BlockItem(block.get(),
                    new Item.Properties().fireResistant()));
        } else {
            return POMitems.ITEMS.register(name, () -> new BlockItem(block.get(),
                    new Item.Properties()));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
