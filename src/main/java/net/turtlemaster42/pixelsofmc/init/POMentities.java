package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;

public class POMentities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PixelsOfMc.MOD_ID);

    public static final RegistryObject<EntityType<RiverShellEntity>> RIVER_SHELL =
            ENTITY_TYPES.register("river_shell",
                    () -> EntityType.Builder.of(RiverShellEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 0.5f)
                            .build(new ResourceLocation(PixelsOfMc.MOD_ID, "test").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
