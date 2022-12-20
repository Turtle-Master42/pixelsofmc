package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

public class GuiTooltips {
    public GuiTooltips() {}

    public List<Component> getProgressArea(int progress, int maxProgress) {
        if (Screen.hasShiftDown())
            return List.of(
                    new TextComponent("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"),
                    new TextComponent("§9"+((maxProgress/20)-(progress/20))+" s")
            );
        else return List.of(new TextComponent("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"));
    }
    public List<Component> getHeatArea(int heat) {
        if (Screen.hasShiftDown())
            return List.of(new TextComponent("§c"+(heat+273)+" K"));
        else return List.of(new TextComponent("§c"+(heat)+" °C"));
    }

    public List<Component> getTimeArea(int time) {
        return List.of(new TextComponent("§9"+(time/20)+" s"));
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
                    new TextComponent("§6"+energy+"§r§7 FE"),
                    new TextComponent("§e"+maxEnergy+"§r§7 FE")
            );
        } else return List.of(
                new TextComponent(("§6"+energy/1000*multi) + "." +
                        ((energy/10*multi) - ((energy/1000*multi)*100)+"§r§7 "+letter1+"FE")),
                new TextComponent(("§e"+ (float) (maxEnergy / 10000*multi) /100) + "§r§7 "+letter2+"FE"));
        }
    }
