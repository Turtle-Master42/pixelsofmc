package net.turtlemaster42.pixelsofmc.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;
import net.turtlemaster42.pixelsofmc.init.POMentities;
import net.turtlemaster42.pixelsofmc.init.POMitems;

public class EventListener {

    @SubscribeEvent
    public void itemTossEvent(ItemTossEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getEntity().getItem().is(POMitems.RIVER_SHELL.get())) {
                RiverShellEntity riverShell = POMentities.RIVER_SHELL.get().spawn((ServerLevel) event.getEntity().level, event.getEntity().getItem(), event.getPlayer(), event.getPlayer().blockPosition().above(), MobSpawnType.SPAWN_EGG, false, false);
                if (riverShell != null) {
                    RiverShellEntity.loadEntityDataFromStack(event.getEntity().getItem(), riverShell);
                    event.getEntity().level.gameEvent(event.getPlayer(), GameEvent.ENTITY_PLACE, event.getPlayer().blockPosition().above());
                    riverShell.addDeltaMovement(event.getEntity().getDeltaMovement());
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void itemJoinLevelEvent(EntityJoinLevelEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getEntity() instanceof ItemEntity item) {
                if (item.getItem().is(POMitems.RIVER_SHELL.get())) {
                    RiverShellEntity riverShell = POMentities.RIVER_SHELL.get().spawn((ServerLevel) event.getEntity().level, item.getItem(), null, item.blockPosition(), MobSpawnType.SPAWN_EGG, false, false);
                    if (riverShell != null) {
                        RiverShellEntity.loadEntityDataFromStack(item.getItem(), riverShell);
                        riverShell.addDeltaMovement(item.getDeltaMovement());
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
