package net.turtlemaster42.pixelsofmc.util;

import net.minecraftforge.fml.ModList;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

public enum Mods {
    //build-in mod compat
    MEKANISM,
    JEI;

    //create
    public boolean isLoaded() {
        return ModList.get().isLoaded(asId());
    }

    public String asId() {
        return name().toLowerCase(Locale.ROOT);
    }

    public <T> Optional<T> runIfInstalled(Supplier<Supplier<T>> toRun) {
        if (isLoaded())
            return Optional.of(toRun.get().get());
        return Optional.empty();
    }

    public void executeIfInstalled(Supplier<Runnable> toExecute) {
        if (isLoaded()) {
            toExecute.get().run();
        }
    }
}
