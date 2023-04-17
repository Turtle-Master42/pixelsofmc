package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class GuiTooltips {
    public GuiTooltips() {}

    public List<Component> getProgressArea(int progress, int maxProgress) {
        if (Screen.hasShiftDown())
            return List.of(
                    Component.literal("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"),
                    Component.literal("§9"+((maxProgress/20)-(progress/20))+" s")
            );
        else return List.of(Component.literal("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"));
    }
    public List<Component> getHeatArea(int heat, int requiredHeat, int requiredMaxHeat) {
        List<Component> text = new java.util.ArrayList<>(List.of());
        if (Screen.hasShiftDown())
            text.add(Component.literal("§c"+(heat+273)+" K"));
        else text.add(Component.literal("§c"+(heat)+" °C"));
        if (requiredHeat != -1) {
            text.add(Component.translatable("tooltip.pixelsofmc.heat.required"));
            if (heat > requiredHeat)
                if (heat < requiredMaxHeat)
                    if (Screen.hasShiftDown())
                        text.add(Component.literal("§a > " + (requiredHeat + 273) + " K"));
                    else text.add(Component.literal("§a > " + (requiredHeat) + " °C"));
                else
                    if (Screen.hasShiftDown())
                        text.add(Component.literal("§6 < " + (requiredMaxHeat + 273) + " K"));
                    else text.add(Component.literal("§6 < " + (requiredMaxHeat) + " °C"));

            else if (Screen.hasShiftDown())
                text.add(Component.literal("§4 > " + (requiredHeat + 273) + " K"));
            else text.add(Component.literal("§4 > " + (requiredHeat) + " °C"));
        }

        return text;
    }

    public List<Component> getTimeArea(int time) {
        return List.of(Component.literal("§9"+(time/20)+" s"));
    }

    public List<Component> getEnergyArea(int energy, int maxEnergy) {
        String letter1 = "";
        String letter2 = "";
        int multi = 1;

        if (maxEnergy >= 1000000) {
            letter1="K";
            letter2="M";
        } else if (maxEnergy >= 1000000000) {
            letter1="M";
            letter2="G";
            multi=1000;
        }
        if (Screen.hasShiftDown()) {
            return List.of(
                    Component.literal("§6"+energy+"§r§7 FE"),
                    Component.literal("§e"+maxEnergy+"§r§7 FE")
            );
        } else return List.of(
                Component.literal(("§6"+energy/1000*multi) + "." +
                        ((energy/10*multi) - ((energy/1000*multi)*100)+"§r§7 "+letter1+"FE")),
                Component.literal(("§e"+ (float) (maxEnergy / 10000*multi) /100) + "§r§7 "+letter2+"FE"));
        }
    }
