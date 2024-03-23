//credits ImmersiveEngineering

package net.turtlemaster42.pixelsofmc.gui.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.InfinitePixelEnergyStorage;

import java.util.List;

public class InfiniteEnergyInfoArea extends InfoArea {
    private final InfinitePixelEnergyStorage energy;

    public InfiniteEnergyInfoArea(int xMin, int yMin, InfinitePixelEnergyStorage energy)  {
        this(xMin, yMin, energy,8,64);
    }

    public InfiniteEnergyInfoArea(int xMin, int yMin, InfinitePixelEnergyStorage energy, int width, int height)  {
        super(new Rect2i(xMin, yMin, width, height));
        this.energy = energy;
    }

    public List<Component> getTooltips() {
        String energyString = energy.getInfiniteEnergy().toString();
        String capacityString = energy.getInfiniteCapacity().toString();


        if (Screen.hasShiftDown()) {
            return List.of(
                    Component.literal("§6"+energyString+"§r§7 FE"),
                    Component.literal("§e"+capacityString+"§r§7 FE")
            );
        }

        String compactEnergyName = "FE";
        String compactCapacityName = "FE";

        String newEnergyString = energyString;
        String newCapacityString = capacityString;

        if (energyString.length() <= 3) {
            compactEnergyName = "FE";
        } else if (energyString.length() <= 6) {
            compactEnergyName = "KFE";
            int split = energyString.length() - 3;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 9) {
            compactEnergyName = "MFE";
            int split = energyString.length() - 6;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 12) {
            compactEnergyName = "GFE";
            int split = energyString.length() - 9;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 15) {
            compactEnergyName = "TFE";
            int split = energyString.length() - 12;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 18) {
            compactEnergyName = "PFE";
            int split = energyString.length() - 15;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 21) {
            compactEnergyName = "EFE";
            int split = energyString.length() - 18;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 24) {
            compactEnergyName = "ZFE";
            int split = energyString.length() - 21;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 27) {
            compactEnergyName = "YFE";
            int split = energyString.length() - 24;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 30) {
            compactEnergyName = "RFE";
            int split = energyString.length() - 27;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        } else if (energyString.length() <= 33) {
            compactEnergyName = "QFE";
            int split = energyString.length() - 30;
            newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
        }

        if (capacityString.length() <= 3) {
            compactCapacityName = "FE";
        } else if (capacityString.length() <= 6) {
            compactCapacityName = "KFE";
            int split = capacityString.length() - 3;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 9) {
            compactCapacityName = "MFE";
            int split = capacityString.length() - 6;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 12) {
            compactCapacityName = "GFE";
            int split = capacityString.length() - 9;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 15) {
            compactCapacityName = "TFE";
            int split = capacityString.length() - 12;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 18) {
            compactCapacityName = "PFE";
            int split = capacityString.length() - 15;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 21) {
            compactCapacityName = "EFE";
            int split = capacityString.length() - 18;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 24) {
            compactCapacityName = "ZFE";
            int split = capacityString.length() - 21;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 27) {
            compactCapacityName = "YFE";
            int split = capacityString.length() - 24;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 30) {
            compactCapacityName = "RFE";
            int split = capacityString.length() - 27;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        } else if (capacityString.length() <= 33) {
            compactCapacityName = "QFE";
            int split = capacityString.length() - 30;
            newCapacityString = capacityString.substring(0, split) + "." + capacityString.substring(split, split + 2);
        }



        return List.of(
                Component.literal("§6"+newEnergyString+"§r§7 "+compactEnergyName),
                Component.literal("§e"+newCapacityString+"§r§7 "+compactCapacityName)
        );
    }

    @Override
    public void draw(PoseStack transform) {
        final int height = area.getHeight();
        int stored = (int)(height*(energy.getEnergyStored()/(float)energy.getMaxEnergyStored()));
        fillGradient(
                transform,
                area.getX(), area.getY()+(height - stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffb51500, 0xff600b00
        );
    }
}
