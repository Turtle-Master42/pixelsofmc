package net.turtlemaster42.pixelsofmc.init;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.*;
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
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, PixelsOfMc.MOD_ID);
    public static final RegistryObject<MenuType<PixelSplitterGuiMenu>> PIXEL_SPLITTER_MENU =
            registerMenuType(PixelSplitterGuiMenu::new, "pixel_splitter_menu");
    public static final RegistryObject<MenuType<BallMillGuiMenu>> BALL_MILL_MENU =
            registerMenuType(BallMillGuiMenu::new, "ball_mill_menu");
    public static final RegistryObject<MenuType<GrinderGuiMenu>> GRINDER_MENU =
            registerMenuType(GrinderGuiMenu::new, "grinder_menu");
    public static final RegistryObject<MenuType<HotIsostaticPressGuiMenu>> HOT_ISOTOPIC_PRESS_MENU =
            registerMenuType(HotIsostaticPressGuiMenu::new, "hot_isotopic_press_menu");
    public static final RegistryObject<MenuType<ChemicalSeperatorGuiMenu>> CHEMICAL_SEPERATOR_MENU =
            registerMenuType(ChemicalSeperatorGuiMenu::new, "chemical_seperator_menu");


    public static final RegistryObject<MenuType<SDSFusionControllerGuiMenu>> SDS_CONTROLLER_MENU =
            registerMenuType(SDSFusionControllerGuiMenu::new, "sds_controller_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
