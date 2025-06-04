package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.util.Util;

import javax.annotation.Nullable;

public class POMdamage {
    private final Registry<DamageType> damageTypes;
    private final DamageSource acid;
    private final DamageSource super_cooled;
    private final DamageSource sun;
    private final DamageSource black_hole;
    private final DamageSource plasma;


    public POMdamage(RegistryAccess pRegistry) {
        this.damageTypes = pRegistry.registryOrThrow(Registries.DAMAGE_TYPE);
        this.acid = this.source("acid");
        this.super_cooled = this.source("super_cooled");
        this.sun = this.source("sun");
        this.black_hole = this.source("black_hole");
        this.plasma = this.source("plasma");
    }

    public static DamageSource acid(Level level) {
        return new POMdamage(level.registryAccess()).acid;
    }

    public static DamageSource super_cooled(Level level) {
        return new POMdamage(level.registryAccess()).super_cooled;
    }

    public static DamageSource sun(Level level) {
        return new POMdamage(level.registryAccess()).sun;
    }

    public static DamageSource black_hole(Level level) {
        return new POMdamage(level.registryAccess()).black_hole;
    }

    public static DamageSource plasma(Level level) {
        return new POMdamage(level.registryAccess()).plasma;
    }


    private DamageSource source(String path) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Util.resourceLocation(path))));
    }

    private DamageSource source(String path, @Nullable Entity pEntity) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Util.resourceLocation(path))), pEntity);
    }

    private DamageSource source(String path, @Nullable Entity pCausingEntity, @Nullable Entity pDirectEntity) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Util.resourceLocation(path))), pCausingEntity, pDirectEntity);
    }
}
