package net.turtlemaster42.pixelsofmc.gui.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class MachineProgressArea extends InfoArea {

    private final int progress;
    private final int maxProgress;

    public MachineProgressArea(int xMin, int yMin, int progress, int maxProgress)  {
        this(xMin,yMin,progress,maxProgress,8,64);
    }

    public MachineProgressArea(int xMin, int yMin, int progress, int maxProgress, int width, int height)  {
        super(new Rect2i(xMin, yMin, width, height));
        this.progress = progress;
        this.maxProgress = maxProgress;
    }

    public List<Component> getTooltips() {
            return List.of(new TextComponent((100/maxProgress * progress) +"%"));

    }

    @Override
    public void draw(PoseStack transform) {}
}
