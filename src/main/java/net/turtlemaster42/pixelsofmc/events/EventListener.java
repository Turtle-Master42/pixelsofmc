package net.turtlemaster42.pixelsofmc.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;
import net.turtlemaster42.pixelsofmc.init.POMentities;
import net.turtlemaster42.pixelsofmc.init.POMitems;

public class EventListener {

    BlockPos attackedBlockPos = null;
    BlockPos attackedMainPos = null;
    int attackedTicks = 0;

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


    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;

        if (mc.options.keyAttack.isDown() && !player.getAbilities().instabuild) {
            HitResult hitResult = mc.hitResult;
            if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {

                BlockPos hitPos = new BlockPos(hitResult.getLocation());
                BlockState hitState = level.getBlockState(hitPos);

                if (this.attackedBlockPos == null) {
                    if (level.getBlockEntity(hitPos) instanceof AbstractDummyMachineBlockTile hitTile) {
                        this.attackedBlockPos = hitPos;
                        this.attackedMainPos = hitTile.getMainPos();
                    }
                }

                if (!hitPos.equals(this.attackedBlockPos)) {
                    this.attackedTicks = 0;
                    this.attackedBlockPos = null;
                    this.attackedMainPos = null;
                }

                if (this.attackedBlockPos != null && this.attackedMainPos != null) {
                    this.attackedTicks++;
                    float progress = Math.min(hitState.getDestroyProgress(player, level, hitPos) * attackedTicks * 5f, 10f);
                    level.destroyBlockProgress(player.getId(), this.attackedMainPos, (int) progress);
                }

            }

        } else {
            this.attackedTicks = 0;
        }
    }


}
