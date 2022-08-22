package net.turtlemaster42.pixelsofmc.init;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.*;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public class POMblocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PixelsOfMc.MOD_ID);
	//public static final RegistryObject<Block> MOLUCULAR_DEFORMER = REGISTRY.register("molucular_deformer", () -> new MolucularDeformerBlock());

    public static final RegistryObject<Block> HEAT_RESISTANT_CASING = registerBlock("heat_resistant_casing",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.NETHERITE_BLOCK)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), POMtabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE)
                    .strength(5.5f, 6f).requiresCorrectToolForDrops()), POMtabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f, 6f).requiresCorrectToolForDrops()), POMtabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> ENDSTONE_TITANIUM_ORE = registerBlock("endstone_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE)
                    .strength(5f, 10f).requiresCorrectToolForDrops()), POMtabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f, 12f).requiresCorrectToolForDrops()), POMtabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> RAW_TITANIUM_BLOCK = registerBlock("raw_titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f, 10f).requiresCorrectToolForDrops()), POMtabs.PIXELS_OF_MINECRAFT_TAB);

    public static final RegistryObject<Block> TITANIUM_DIBORIDE_BLOCK = registerBlock("titanium_diboride_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), POMtabs.PIXELS_OF_MINECRAFT_TAB);

	public static final RegistryObject<Block> MACHINE_BLOCK = BLOCKS.register("machine_block", () -> new MachineBlock());

    public static final RegistryObject<Block> CHISELED_STONE_BRICK_CORNER = registerBlock("chiseled_stone_brick_corner",
            () -> new ChiseledStoneBricksCornerBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)), POMtabs.PIXELS_OF_MINECRAFT_TAB);

    public static final RegistryObject<Block> CHISELED_STONE_BRICK_PILLAR = registerBlock("chiseled_stone_brick_pillar",
            () -> new ChiseledStoneBrickPillar(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)), POMtabs.PIXELS_OF_MINECRAFT_TAB);

    public static final RegistryObject<Block> SMOOTH_STONE_BRICKS = registerBlock("smooth_stone_bricks",
            () -> new ChiseledStoneBrickPillar(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)), POMtabs.PIXELS_OF_MINECRAFT_TAB);

    public static final RegistryObject<Block> FIRE_TRAP_BLOCK = registerBlock("fire_trap_block",
            () -> new FireTrapBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)), POMtabs.PIXELS_OF_MINECRAFT_TAB);

	public static final RegistryObject<Block> PIXEL_SPLITTER = registerBlock("pixel_splitter",
            () -> new PixelSplitterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), POMtabs.PIXELS_OF_MINECRAFT_TAB, "tooltip.pixelsofmc.block.pixel_splitter", "tooltip.pixelsofmc.noshift", "tooltip.pixelsofmc.block.pixel_splitter2", "tooltip.pixelsofmc.noctrl", "§7This block is just a normal block\nnothing special\nwy are you still reading this\nstop\nI said stop\nSTOP PLEASE!!!\ndon't do it\nit's not worth it\nNO\nNOOOOOOOO PLEASE!\nlook away\nit's for the better\nJUST STOP LOOKING", "tooltip.pixelsofmc.noalt");

    public static final RegistryObject<Block> BALL_MILL = registerBlock("ball_mill",
            () -> new BallMillBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), POMtabs.PIXELS_OF_MINECRAFT_TAB, "tooltip.pixelsofmc.block.ball_mill.shift", "tooltip.pixelsofmc.noshift", "", "", "tooltip.pixelsofmc.block.ball_mill.alt", "tooltip.pixelsofmc.noalt");



    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
		}
	}

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                                                                     CreativeModeTab tab, String tooltipKeyShift, String noShift, String tooltipKeyCtrl, String noCtrl, String tooltipKeyAlt, String noAlt) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab, tooltipKeyShift, noShift, tooltipKeyCtrl, noCtrl, tooltipKeyAlt, noAlt);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab, String tooltipKey, String shiftTooltip, String tooltipKey2, String ctrlTooltip, String tooltipKey3, String altTooltip) {
        return POMitems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)) {
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                if(Screen.hasShiftDown()) {
                    pTooltip.add(new TranslatableComponent(tooltipKey));
                } else {
                    pTooltip.add(new TranslatableComponent(shiftTooltip));
                }
                if(Screen.hasControlDown()) {
                    pTooltip.add(new TranslatableComponent(tooltipKey2));
                } else {
                    pTooltip.add(new TranslatableComponent(ctrlTooltip));
                }
                if(Screen.hasAltDown()) {
                    pTooltip.add(new TranslatableComponent(tooltipKey3));
                } else {
                    pTooltip.add(new TranslatableComponent(altTooltip));
                }
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return POMitems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
