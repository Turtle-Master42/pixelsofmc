package net.turtlemaster42.pixelsofmc.util.recipe;

import com.google.common.collect.Lists;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class FluidContainer implements Container {
    private final int size;
    private final NonNullList<FluidStack> fluids;
    private List<ContainerListener> listeners;

    public FluidContainer(int size) {
        this.size = size;
        this.fluids = NonNullList.withSize(size, FluidStack.EMPTY);
    }

    public FluidContainer(FluidStack... fluids) {
        this.size = fluids.length;
        this.fluids = NonNullList.of(FluidStack.EMPTY, fluids);
    }

    public void addListener(ContainerListener pListener) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }
        this.listeners.add(pListener);
    }

    public void removeListener(ContainerListener pListener) {
        if (this.listeners != null) {
            this.listeners.remove(pListener);
        }
    }


    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    public NonNullList<FluidStack> getFluids() {
        return fluids;
    }

    public FluidStack getFluid(int pIndex) {
        return pIndex >= 0 && pIndex < this.fluids.size() ? this.fluids.get(pIndex) : FluidStack.EMPTY;
    }

    public void setFluid(int pIndex, FluidStack pStack) {
        this.fluids.set(pIndex, pStack);
        this.setChanged();
    }

    public void clearContent() {
        this.fluids.clear();
        this.setChanged();
    }

    //USELESS
    @Override
    public @NotNull ItemStack getItem(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int i, int i1) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int i, @NotNull ItemStack itemStack) {}

    @Override
    public void setChanged() {}

    @Override
    public boolean stillValid(@NotNull Player player) {return true;}
}
