package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.IFluidTank;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluidArea extends InfoArea {
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();

    private final IFluidTank fluidTank;
    private final FluidTankRenderer fluidRenderer;
    private final Component tankDescription;

    public FluidArea(IFluidTank fluidTank, Component tankDescription, Rect2i area) {
        super(area);
        this.fluidTank = fluidTank;
        this.tankDescription = tankDescription;
        this.fluidRenderer = new FluidTankRenderer(fluidTank.getCapacity(), true, area.getWidth(), area.getHeight());
    }

    public List<Component> getTooltips() {
        List<Component> tooltip = new ArrayList<>();

        FluidStack fluidStack = fluidTank.getFluid();
        try {
            if (!tankDescription.equals(Component.empty()))
                tooltip.add(tankDescription);

            if (!fluidStack.getFluid().isSame(Fluids.EMPTY)) {
                tooltip.add(fluidStack.getDisplayName());
            }

            long amount = fluidStack.getAmount();
            long milliBuckets = (amount * 1000) / FluidType.BUCKET_VOLUME;

            MutableComponent amountString = Component.translatable("tooltip.pixelsofmc.fluid.amount.with.capacity", nf.format(milliBuckets), nf.format(fluidTank.getCapacity()));
            tooltip.add(amountString.withStyle(ChatFormatting.GRAY));

        } catch (RuntimeException e) {
            PixelsOfMc.LOGGER.error("Failed to get tooltip for fluid: {}", String.valueOf(e));
        }

        return tooltip;
    }

    @Override
    protected void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, getTooltips(), Optional.empty(), mouseX, mouseY);
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        fluidRenderer.render(guiGraphics, area.getX(), area.getY(), fluidTank.getFluid());
    }
}
