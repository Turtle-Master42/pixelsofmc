package net.turtlemaster42.pixelsofmc.util;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private static final List<String> HEX = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f");
    private static final List<Integer> DEC = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    public static int[] hexToRGB(String hex) {
        List<String> aHex = List.of(String.valueOf(hex.toLowerCase().charAt(0)),
                String.valueOf(hex.toLowerCase().charAt(1)),
                String.valueOf(hex.toLowerCase().charAt(2)),
                String.valueOf(hex.toLowerCase().charAt(3)),
                String.valueOf(hex.toLowerCase().charAt(4)),
                String.valueOf(hex.toLowerCase().charAt(5)));

        //PixelsOfMc.LOGGER.info("Hex: {}", aHex);

        int R = DEC.get(HEX.indexOf(aHex.get(0))) * 16 + HEX.indexOf(aHex.get(1));
        int G = DEC.get(HEX.indexOf(aHex.get(2))) * 16 + HEX.indexOf(aHex.get(3));
        int B = DEC.get(HEX.indexOf(aHex.get(4))) * 16 + HEX.indexOf(aHex.get(5));
        return new int[]{R, G, B};
    }
}
