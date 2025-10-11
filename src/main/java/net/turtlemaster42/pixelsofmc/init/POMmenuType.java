package net.turtlemaster42.pixelsofmc.init;

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

public class POMmenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, PixelsOfMc.MOD_ID);

    //machines
    public static final RegistryObject<MenuType<PixelSplitterMenu>> PIXEL_SPLITTER_MENU =
            registerMenuType(PixelSplitterMenu::new, "pixel_splitter_menu");
    public static final RegistryObject<MenuType<PixelAssemblerMenu>> PIXEL_ASSEMBLER_MENU =
            registerMenuType(PixelAssemblerMenu::new, "pixel_assembler_menu");
    public static final RegistryObject<MenuType<BallMillMenu>> BALL_MILL_MENU =
            registerMenuType(BallMillMenu::new, "ball_mill_menu");
    public static final RegistryObject<MenuType<GrinderMenu>> GRINDER_MENU =
            registerMenuType(GrinderMenu::new, "grinder_menu");
    public static final RegistryObject<MenuType<HotIsostaticPressMenu>> HOT_ISOTOPIC_PRESS_MENU =
            registerMenuType(HotIsostaticPressMenu::new, "hot_isotopic_press_menu");
    public static final RegistryObject<MenuType<ChemicalSeparatorMenu>> CHEMICAL_SEPARATOR_MENU =
            registerMenuType(ChemicalSeparatorMenu::new, "chemical_separator_menu");
    public static final RegistryObject<MenuType<ChemicalCombinerMenu>> CHEMICAL_COMBINER_MENU =
            registerMenuType(ChemicalCombinerMenu::new, "chemical_combiner_menu");
    public static final RegistryObject<MenuType<ChemicalMixerMenu>> CHEMICAL_MIXER_MENU =
            registerMenuType(ChemicalMixerMenu::new, "chemical_mixer_menu");
    public static final RegistryObject<MenuType<PixelBombarderMenu>> PIXEL_BOMBARDER_MENU =
            registerMenuType(PixelBombarderMenu::new, "pixel_bombarder_menu");


    //multiblock
    public static final RegistryObject<MenuType<IndustrialHeatExchangerMenu>> INDUSTRIAL_HEAT_EXCHANGER_MENU =
            registerMenuType(IndustrialHeatExchangerMenu::new, "industrial_heat_exchanger_menu");

    //reactor
    public static final RegistryObject<MenuType<NuclearReactorMenu>> NUCLEAR_REACTOR_MENU =
            registerMenuType(NuclearReactorMenu::new, "nuclear_reactor_menu");
    public static final RegistryObject<MenuType<SDSFusionControllerMenu>> SDS_CONTROLLER_MENU =
            registerMenuType(SDSFusionControllerMenu::new, "sds_controller_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
