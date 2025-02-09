package net.turtlemaster42.pixelsofmc.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

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
}
