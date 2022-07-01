package net.turtlemaster42.pixelsofmc.init;

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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.Block;

import net.turtlemaster42.pixelsofmc.PixelsOfMcMod;

import java.util.List;
import java.util.function.Supplier;

public class PixelsOfMcModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, PixelsOfMcMod.MOD_ID);
	//public static final RegistryObject<Block> MOLUCULAR_DEFORMER = REGISTRY.register("molucular_deformer", () -> new MolucularDeformerBlock());

    public static final RegistryObject<Block> HEAT_RESISTANT_CASING = registerBlock("heat_resistant_casing",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.NETHERITE_BLOCK)
                    .strength(30f, 1200f).requiresCorrectToolForDrops()), PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE)
                    .strength(5.5f, 6f).requiresCorrectToolForDrops()), PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f, 6f).requiresCorrectToolForDrops()), PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> ENDSTONE_TITANIUM_ORE = registerBlock("end_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE)
                    .strength(5f, 10f).requiresCorrectToolForDrops()), PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f, 12f).requiresCorrectToolForDrops()), PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB);
    public static final RegistryObject<Block> RAW_TITANIUM_BLOCK = registerBlock("raw_titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f, 10f).requiresCorrectToolForDrops()), PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB);

	public static final RegistryObject<Block> MACHINE_BLOCK = REGISTRY.register("machine_block", () -> new MachineBlockBlock());
	public static final RegistryObject<Block> PIXEL_SPLITTER = registerBlock("pixel_splitter",
            () -> new PixelSplitterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()), PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB, "tooltip.pixelsofmc.block.pixel_splitter", "tooltip.pixelsofmc.noshift", "tooltip.pixelsofmc.block.pixel_splitter2", "tooltip.pixelsofmc.noctrl", "§7This block is just a normal block\nnothing special\nwy are you still reading this\nstop\nI said stop\nSTOP PLEASE!!!\ndon't do it\nit's not worth it\nNO\nNOOOOOOOO PLEASE!\nlook away\nit's for the better\nJUST STOP LOOKING", "tooltip.pixelsofmc.noalt");


	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			MachineBlockBlock.registerRenderLayer();
		}
	}

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return REGISTRY.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                                                                     CreativeModeTab tab, String tooltipKeyShift, String noShift, String tooltipKeyCtrl, String noCtrl, String tooltipKeyAlt, String noAlt) {
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn, tab, tooltipKeyShift, noShift, tooltipKeyCtrl, noCtrl, tooltipKeyAlt, noAlt);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab, String tooltipKey, String shiftTooltip, String tooltipKey2, String ctrlTooltip, String tooltipKey3, String altTooltip) {
        return PixelsOfMcModItems.REGISTRY.register(name, () -> new BlockItem(block.get(),
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
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return PixelsOfMcModItems.REGISTRY.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
