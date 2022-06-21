package net.turtlemaster42.pixelsofmc.init;

import net.turtlemaster42.pixelsofmc.PixelsOfMcMod;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelSplitterGuiMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PixelsOfMcModMenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, PixelsOfMcMod.MOD_ID);

    public static final RegistryObject<MenuType<PixelSplitterGuiMenu>> PIXEL_SPLITTER_MENU =
            registerMenuType(PixelSplitterGuiMenu::new, "pixel_splitter_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
