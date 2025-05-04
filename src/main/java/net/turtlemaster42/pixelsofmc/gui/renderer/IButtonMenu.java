package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IButtonMenu {
    void setSwitch(int currentSwitch, boolean on);
    boolean getSwitch(int currentSwitch);
    BlockEntity getBlockEntity();
}
