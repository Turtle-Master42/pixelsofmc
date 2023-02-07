//credits ImmersiveEngineering

package net.turtlemaster42.pixelsofmc.gui.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */
public class EnergyInfoArea extends InfoArea {
    private final IEnergyStorage energy;

    public EnergyInfoArea(int xMin, int yMin, IEnergyStorage energy)  {
        this(xMin, yMin, energy,8,64);
    }

    public EnergyInfoArea(int xMin, int yMin, IEnergyStorage energy, int width, int height)  {
        super(new Rect2i(xMin, yMin, width, height));
        this.energy = energy;
    }

    public List<Component> getTooltips() {
        if (Screen.hasShiftDown()) {
            return List.of(
                    new TextComponent("§6"+energy.getEnergyStored()+"§r§7 FE"),
                    new TextComponent("§e"+energy.getMaxEnergyStored()+"§r§7 FE")
            );
        }
        if (energy.getMaxEnergyStored() >= 1000000) {
            return List.of(
                    new TextComponent(
                            ("§6"+energy.getEnergyStored()/1000) + "." +
                                    ((energy.getEnergyStored()/10) - ((energy.getEnergyStored()/1000)*100)+"§r§7 KFE"
                    )),
                    new TextComponent(
                            ("§e"+ (float) (energy.getMaxEnergyStored() / 10000) /100) + "§r§7 MFE"));
        } else if (energy.getMaxEnergyStored() >= 1000000000) {
            return List.of(
                    new TextComponent(
                            ("§6"+energy.getEnergyStored()/1000000) + "." +
                                    ((energy.getEnergyStored()/10000) - ((energy.getEnergyStored()/1000000)*100)+"§r§7 MFE"
                                    )),
                    new TextComponent(
                            ("§e"+ (float) (energy.getMaxEnergyStored() / 10000000) /100) + "§r§7 GFE"));
        } else {
            return List.of(
                    new TextComponent("§6"+energy.getEnergyStored()+"§r§7 FE"),
                    new TextComponent("§e"+energy.getMaxEnergyStored()+"§r§7 FE"));
        }
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
