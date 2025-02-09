package net.turtlemaster42.pixelsofmc.util.block;

public interface IButtonTile {
    void setSwitch(boolean on, int currentSwitch);
    boolean getSwitch(int currentSwitch);
}
