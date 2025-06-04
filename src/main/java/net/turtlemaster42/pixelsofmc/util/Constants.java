package net.turtlemaster42.pixelsofmc.util;

public class Constants {
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

    public static final int FE_waterToSteam = 10; // 10_000 FE needed to heat Water (290 K) to Steam (440 K)
    public static final int FE_waterToBlazingSteam = 50; // 50_000 FE needed to heat Water (290 K) to Blazing Steam (1040 K)
    public static final int FE_SteamToBlazingSteam = 40; // 40_000 FE needed to heat Steam (440 K) to Blazing Steam (1040 K)
    public static final int FE_waterBucketToSteam = 10_000; // 10_000 FE needed to heat Water (290 K) to Steam (440 K)
    public static final int FE_waterBucketToBlazingSteam = 50_000; // 50_000 FE needed to heat Water (290 K) to Blazing Steam (1040 K)
    public static final int FE_SteamBucketToBlazingSteam = 40_000; // 40_000 FE needed to heat Steam (440 K) to Blazing Steam (1040 K)

    // 24 // 24_000 FE needed to heat Mercury (290 K) to Mercury Gas (690 K)
    // 90 // 90_000 FE needed to heat Liquid Lead (330 K) to Lead Gas (1830 K)

    public static final float plasmaTemperatureToNormal = 16.66666667f; // increasing 1 K in air costs 16.667 K of plasma

    public static final int FusionPowerToSteam = 2500; // you consume 2500 Fusion power to create one bucket of Steam
    public static final int FusionPowerToBlazingSteam = 12_500; // you consume 12_500 Fusion power to create one bucket of Blazing Steam
    public static final float fusionPowerPerMbSteam = 2.5f; // fusion power required to heat 1 mb of Water to Steam
    public static final float fusionPowerPerMbBlazingSteam = 12.5f; // fusion power required to heat 1 mb of Water to Blazing Steam

    public static final float FE_turbineSteam = 8500; // 1 bucket of steam creates 8500 FE, 1500 is lost because the water outputted is 40°C, (85% efficiency)
    public static final float FE_turbineBlazingSteam = 31_500; // 1 bucket of blazing steam creates 31_500 FE, 9500 is lost because the steam outputted is 300°C (78.75% efficiency)
}

//fusion power == kelvin in plasma



// mekanism:
// 1000 mb steam -> 2000 FE
// 2 mb fuel per sec
// 8,333 min per 1000 mb fuel
// 1 mb fuel -> 20.000 mb steam

// 1 mb fuel -> 40.000 FE
// 1 yellowcake -> 20.000.000 FE

// 10 fuel mb -> 1 plutonium mb
// 20 yellowcake -> 1 plutonium pellet
// 1000 plutonium mb -> 8000 fuel mb -> 320.000.000 FE


// 2.66 meka yellowcake -> 40.000.000 FE    -> 1 uranium cell
// 8    meka yellowcake -> 160.000.000 FE   -> 1 enriched uranium cell
// 24   meka yellowcake -> 640.000.000 FE   -> 1 plutonium cell
// 72   meka yellowcake -> 2.560.000.000 FE -> 1 enriched plutonium cell

// 1 uranium cell = 500 FE/t
// 1 enriched uranium cell = 2000 FE/t
// 1 plutonium cell = 8.000 FE/t
// 1 enriched plutonium cell = 32.000 FE/t
