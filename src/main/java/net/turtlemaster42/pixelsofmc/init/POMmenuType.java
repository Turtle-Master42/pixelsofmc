package net.turtlemaster42.pixelsofmc.init;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.BallMillGuiMenu;
import net.turtlemaster42.pixelsofmc.gui.menu.GrinderGuiMenu;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelSplitterGuiMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class POMmenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, PixelsOfMc.MOD_ID);
    public static final RegistryObject<MenuType<PixelSplitterGuiMenu>> PIXEL_SPLITTER_MENU =
            registerMenuType(PixelSplitterGuiMenu::new, "pixel_splitter_menu");
    public static final RegistryObject<MenuType<BallMillGuiMenu>> BALL_MILL_MENU =
            registerMenuType(BallMillGuiMenu::new, "ball_mill_menu");
    public static final RegistryObject<MenuType<GrinderGuiMenu>> GRINDER_MENU =
            registerMenuType(GrinderGuiMenu::new, "grinder_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
