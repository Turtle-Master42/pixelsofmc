package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.*;
import net.turtlemaster42.pixelsofmc.gui.screen.*;

public class POMmenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, PixelsOfMc.MOD_ID);

    //machines
    public static final RegistryObject<MenuType<PixelSplitterMenu>> PIXEL_SPLITTER_MENU =
            registerMenu(PixelSplitterMenu::new, "pixel_splitter_menu");
    public static final RegistryObject<MenuType<PixelAssemblerMenu>> PIXEL_ASSEMBLER_MENU =
            registerMenu(PixelAssemblerMenu::new, "pixel_assembler_menu");
    public static final RegistryObject<MenuType<BallMillMenu>> BALL_MILL_MENU =
            registerMenu(BallMillMenu::new, "ball_mill_menu");
    public static final RegistryObject<MenuType<GrinderMenu>> GRINDER_MENU =
            registerMenu(GrinderMenu::new, "grinder_menu");
    public static final RegistryObject<MenuType<HotIsostaticPressMenu>> HOT_ISOTOPIC_PRESS_MENU =
            registerMenu(HotIsostaticPressMenu::new, "hot_isotopic_press_menu");
    public static final RegistryObject<MenuType<ChemicalSeparatorMenu>> CHEMICAL_SEPARATOR_MENU =
            registerMenu(ChemicalSeparatorMenu::new, "chemical_separator_menu");
    public static final RegistryObject<MenuType<ChemicalCombinerMenu>> CHEMICAL_COMBINER_MENU =
            registerMenu(ChemicalCombinerMenu::new, "chemical_combiner_menu");
    public static final RegistryObject<MenuType<ChemicalMixerMenu>> CHEMICAL_MIXER_MENU =
            registerMenu(ChemicalMixerMenu::new, "chemical_mixer_menu");
    public static final RegistryObject<MenuType<PixelBombarderMenu>> PIXEL_BOMBARDER_MENU =
            registerMenu(PixelBombarderMenu::new, "pixel_bombarder_menu");

    //multiblock
    public static final RegistryObject<MenuType<IndustrialHeatExchangerMenu>> INDUSTRIAL_HEAT_EXCHANGER_MENU =
            registerMenu(IndustrialHeatExchangerMenu::new, "industrial_heat_exchanger_menu");
    public static final RegistryObject<MenuType<IndustrialTurbineMenu>> INDUSTRIAL_TURBINE_MENU =
            registerMenu(IndustrialTurbineMenu::new, "industrial_turbine_menu");

    //reactor
    public static final RegistryObject<MenuType<NuclearReactorMenu>> NUCLEAR_REACTOR_MENU =
            registerMenu(NuclearReactorMenu::new, "nuclear_reactor_menu");
    public static final RegistryObject<MenuType<SDSFusionControllerMenu>> SDS_CONTROLLER_MENU =
            registerMenu(SDSFusionControllerMenu::new, "sds_controller_menu");



    public static void registerMenuScreens() {
        MenuScreens.register(PIXEL_SPLITTER_MENU.get(), PixelSplitterScreen::new);
        MenuScreens.register(PIXEL_ASSEMBLER_MENU.get(), PixelAssemblerScreen::new);
        MenuScreens.register(BALL_MILL_MENU.get(), BallMillScreen::new);
        MenuScreens.register(GRINDER_MENU.get(), GrinderScreen::new);
        MenuScreens.register(HOT_ISOTOPIC_PRESS_MENU.get(), HotIsostaticPressScreen::new);
        MenuScreens.register(CHEMICAL_SEPARATOR_MENU.get(), ChemicalSeparatorScreen::new);
        MenuScreens.register(CHEMICAL_COMBINER_MENU.get(), ChemicalCombinerScreen::new);
        MenuScreens.register(CHEMICAL_MIXER_MENU.get(), ChemicalMixerScreen::new);
        MenuScreens.register(NUCLEAR_REACTOR_MENU.get(), NuclearReactorScreen::new);
        MenuScreens.register(SDS_CONTROLLER_MENU.get(), SDSFusionControllerScreen::new);
        MenuScreens.register(INDUSTRIAL_HEAT_EXCHANGER_MENU.get(), IndustrialHeatExchangerScreen::new);
        MenuScreens.register(INDUSTRIAL_TURBINE_MENU.get(), IndustrialTurbineScreen::new);
        MenuScreens.register(PIXEL_BOMBARDER_MENU.get(), PixelBombarderScreen::new);
    }


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenu(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
