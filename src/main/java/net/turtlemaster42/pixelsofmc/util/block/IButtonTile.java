package net.turtlemaster42.pixelsofmc.util.block;

public interface IButtonTile {
    void setSwitch(int currentSwitch, boolean on);
    boolean getSwitch(int currentSwitch);
}
