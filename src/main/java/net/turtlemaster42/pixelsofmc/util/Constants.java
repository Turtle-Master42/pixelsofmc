package net.turtlemaster42.pixelsofmc.util;

public class Constants {

    //public final double name = 0;
    public static final double c = 2.99792458E8;
    public static final double cSquared = 8.987551787E16;
    public static final double e = 1.602176565E-19;
    public static final float D_T_EnergyRelease = 334.8979766804f; // Amount of energy released by fusing 1 Deuterium and 1 Tritium Pixel, based of information from Mekanism and the blockPixelDensity constant.
    public static final double fusionEnergyReleaseConstant = 7.472065672E13; // The constant to go from energy released in the real world from fusing 2 atoms to fusing 2 pixels in minecraft.
    public static final float fusionTemperatureDivider = 37.29243342f; // The constant to go from the required temperature for quantum-less fusion in the real world to the required temperature in minecraft, based of information from Mekanism.
    public static final int blockPixelDensity = 2_985_984; //amount of pixels in a solid block of 1 element
    public static final int plasmaHeatingPerJoule = 10; // in plasma 1 K = 10 J
    public static final float normalHeatingPerJoule = 166.6666667f; // in air or other 1 K = 166.6666667 J
    public static final float J_FE_Constant = 0.4f; // 1 J = 0.4 FE

    public static final int FE_waterToSteam = 10_000; // 10_000 FE needed to heat Water (290 K) to Steam (440 K)
    public static final int FE_waterToBlazingSteam = 50_000; // 50_000 FE needed to heat Water (290 K) to Blazing Steam (1040 K)
    public static final int FE_SteamToBlazingSteam = 40_000; // 40_000 FE needed to heat Steam (440 K) to Blazing Steam (1040 K)

    public static final float plasmaTemperatureToNormal = 16.66666667f; // increasing 1 K in air costs 16.667 K of plasma

    public static final int FusionPowerToSteam = 2500; // you consume 2500 Fusion power to create one bucket of Steam
    public static final int FusionPowerToBlazingSteam = 12_500; // you consume 12_500 Fusion power to create one bucket of Blazing Steam
    public static final float fusionPowerPerMbSteam = 2.5f; // fusion power required to heat 1 mb of Water to Steam
    public static final float fusionPowerPerMbBlazingSteam = 12.5f; // fusion power required to heat 1 mb of Water to Blazing Steam

    public static final float FE_turbineSteam = 8500; // 1 bucket of steam creates 8500 FE, 1500 is lost because the water outputted is 40°C, (85% efficiency)
    public static final float FE_turbineBlazingSteam = 31_500; // 1 bucket of blazing steam creates 31_500 FE, 9500 is lost because the steam outputted is 300°C (78.75% efficiency)
}

//fusion power == kelvin in plasma