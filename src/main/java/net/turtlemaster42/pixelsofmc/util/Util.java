package net.turtlemaster42.pixelsofmc.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import org.joml.Vector3f;

import java.util.List;

public class Util {

    private static final List<String> HEX = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f");
    private static final List<Integer> DEC = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    private static final String[] compact = {"", "K", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q"};
    public static int[] hexToRGB(String hex) {
        List<String> aHex = List.of(String.valueOf(hex.toLowerCase().charAt(0)),
                String.valueOf(hex.toLowerCase().charAt(1)),
                String.valueOf(hex.toLowerCase().charAt(2)),
                String.valueOf(hex.toLowerCase().charAt(3)),
                String.valueOf(hex.toLowerCase().charAt(4)),
                String.valueOf(hex.toLowerCase().charAt(5)));

        int R = DEC.get(HEX.indexOf(aHex.get(0))) * 16 + HEX.indexOf(aHex.get(1));
        int G = DEC.get(HEX.indexOf(aHex.get(2))) * 16 + HEX.indexOf(aHex.get(3));
        int B = DEC.get(HEX.indexOf(aHex.get(4))) * 16 + HEX.indexOf(aHex.get(5));
        return new int[]{R, G, B};
    }

    public static BlockPos blockPos(Vec3 vec3) {
        return blockPos(vec3.x(), vec3.y(), vec3.z());
    }

    public static BlockPos blockPos(double x, double y, double z) {
        return new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z));
    }


    public static String[] compactMetricNumber(int energy) {
        return compactMetricNumber(String.valueOf(energy));
    }

    public static String[] compactMetricNumber(long energy) {
        return compactMetricNumber(String.valueOf(energy));
    }

    public static String[] compactMetricNumber(InfiniteNumber energy) {
        return compactMetricNumber(energy.toString());
    }

    public static String[] compactMetricNumber(String energy) {
        String compactName = compact[compact.length - 1];
        String newNumber = energy;
        int thousands = Mth.floor((float) (energy.length()-1) / 3);
        if (thousands <= 10) {
            compactName = compact[thousands];
        }
        if (thousands > 0) {
            int split = energy.length() - 3 * Math.min(thousands, compact.length - 1);
            newNumber = energy.substring(0, split) + "." + energy.substring(split, split + 2);
        }
        return new String[]{newNumber, compactName};
    }

    public static String combinedCompactMetricNumber(long energy) {
        String[] number = compactMetricNumber(energy);
        return number[0] + " " + number[1];
    }

    public static String formatNumber(int number) {
        return baseFormatNumber(String.valueOf(number));
    }
    public static String formatNumber(long number) {
        return baseFormatNumber(String.valueOf(number));
    }
    public static String formatNumber(InfiniteNumber number) {
        return baseFormatNumber(number.toString());
    }
    public static String formatNumber(String number) {
        if (number.contains(".")) {
            String[] splitNumber = number.split("\\.");
            return baseFormatNumber(splitNumber[0])+"."+splitNumber[1];
        }
        return baseFormatNumber(number);
    }

    protected static String baseFormatNumber(String number) {
        StringBuilder fancyNumber = new StringBuilder();

        boolean isNegative = false;
        if (number.startsWith("-")) {
            number =  number.replace("-", "");
            isNegative = true;
        }
        while (number.length() > 3) {
            String numberPart = number.substring(number.length() - 3);
            number = number.substring(0, number.length() - 3);
            fancyNumber.insert(0, numberPart + ",");
        }
        fancyNumber.insert(0, number + ",");

        if (isNegative)
            fancyNumber.insert(0, "-");

        return fancyNumber.deleteCharAt(fancyNumber.length() - 1).toString();
    }

    public static ResourceLocation resourceLocation(String path) {
        return resourceLocation(PixelsOfMc.MOD_ID, path);
    }

    public static ResourceLocation resourceLocation(String name, String path) {
        return new ResourceLocation(name, path);
    }

    public static int formatCodeColor(String code) {
        return switch (code) {
            case "0" -> 0x000000;
            case "1" -> 0x0000AA;
            case "2" -> 0x00AA00;
            case "3" -> 0x00AAAA;
            case "4" -> 0xAA0000;
            case "5" -> 0xAA00AA;
            case "6" -> 0xFFAA00;
            case "7" -> 0xAAAAAA;
            case "8" -> 0x555555;
            case "9" -> 0x5555FF;
            case "a" -> 0x55FF55;
            case "b" -> 0x55FFFF;
            case "c" -> 0xFF5555;
            case "d" -> 0xFF55FF;
            case "e" -> 0xFFFF55;
            default -> 0xFFFFFF;
        };
    }
    public static Vector3f formatCodeVecColor(String code) {
        return switch (code) {
            case "0" -> new Vector3f(0, 0, 0);
            case "1" -> new Vector3f(0, 0, 170/255f);
            case "2" -> new Vector3f(0, 170/255f, 0);
            case "3" -> new Vector3f(0, 170/255f, 170/255f);
            case "4" -> new Vector3f(170/255f, 0, 0);
            case "5" -> new Vector3f(170/255f, 0, 170);
            case "6" -> new Vector3f(1, 170/255f, 0);
            case "7" -> new Vector3f(170/255f, 170/255f, 170/255f);
            case "8" -> new Vector3f(85/255f, 85/255f, 85/255f);
            case "9" -> new Vector3f(85/255f, 85/255f, 1);
            case "a" -> new Vector3f(85/255f, 1, 85/255f);
            case "b" -> new Vector3f(85/255f, 1, 1);
            case "c" -> new Vector3f(1, 85/255f, 85/255f);
            case "d" -> new Vector3f(1, 85/255f, 1);
            case "e" -> new Vector3f(1, 1, 85/255f);
            default -> new Vector3f(1, 1, 1);
        };
    }

    public static void spawnServerParticlesOnBlockFaces(ServerLevel pLevel, BlockPos pPos, ParticleOptions pParticle, IntProvider pCount) {
        for(Direction direction : Direction.values()) {
            spawnServerParticlesOnBlockFace(pLevel, pPos, pParticle, pCount, direction, 0.55D);
        }

    }

    public static void spawnServerParticlesOnBlockFace(ServerLevel pLevel, BlockPos pPos, ParticleOptions pParticle, IntProvider pCount, Direction pDirection, double p_216325_) {
        int i = pCount.sample(pLevel.random);

        for(int j = 0; j < i; ++j) {
            spawnServerParticleOnFace(pLevel, pPos, pDirection, pParticle, p_216325_);
        }

    }

    public static void spawnServerParticleOnFace(ServerLevel pLevel, BlockPos pPos, Direction pDirection, ParticleOptions pParticle, double p_216312_) {
        Vec3 vec3 = Vec3.atCenterOf(pPos);
        int i = pDirection.getStepX();
        int j = pDirection.getStepY();
        int k = pDirection.getStepZ();
        double d0 = vec3.x + (i == 0 ? Mth.nextDouble(pLevel.random, -0.5D, 0.5D) : (double)i * p_216312_);
        double d1 = vec3.y + (j == 0 ? Mth.nextDouble(pLevel.random, -0.5D, 0.5D) : (double)j * p_216312_);
        double d2 = vec3.z + (k == 0 ? Mth.nextDouble(pLevel.random, -0.5D, 0.5D) : (double)k * p_216312_);
        pLevel.sendParticles(pParticle, d0, d1, d2, 1, 0, 0, 0, 0);
    }
}
