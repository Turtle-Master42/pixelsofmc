package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;
import net.turtlemaster42.pixelsofmc.init.POMentities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.AttributeSet;
import java.util.List;
import java.util.Objects;

public class RiverShellEntityItem extends Item {
    public RiverShellEntityItem(Item.Properties pProperties) {
        super(pProperties);
    }
    public RiverShellEntityItem() {
        this(new Properties());
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pContext.getItemInHand();
            BlockPos blockpos = pContext.getClickedPos();
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            RiverShellEntity riverShell = POMentities.RIVER_SHELL.get().spawn((ServerLevel)level, itemstack, pContext.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
            if (riverShell != null) {
                RiverShellEntity.loadEntityDataFromStack(itemstack, riverShell);
                Objects.requireNonNull(pContext.getPlayer()).getInventory().removeItem(itemstack);
                level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!(pLevel instanceof ServerLevel)) {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        } else {
            ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

            RiverShellEntity riverShell = POMentities.RIVER_SHELL.get().spawn((ServerLevel)pLevel, itemstack, pPlayer, new BlockPos(pPlayer.position()).above(2), MobSpawnType.SPAWN_EGG, false, false);
            if (riverShell != null) {
                RiverShellEntity.loadEntityDataFromStack(itemstack, riverShell);
                riverShell.setDeltaMovement(pPlayer.getDeltaMovement());
                riverShell.addDeltaMovement(pPlayer.getLookAngle().scale(1.5d));
                Objects.requireNonNull(pPlayer).getInventory().removeItem(itemstack);
                pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, pPlayer.blockPosition().above(2));
            }
            return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (compoundtag.contains("Health", 99))
            pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.entity.health", compoundtag.getFloat("Health")).withStyle(ChatFormatting.RED));
        if (compoundtag.contains("Age", 99))
            if (compoundtag.getInt("Age") < 0) {
                int age = Math.round(compoundtag.getFloat("Age") / -1200);
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.entity.age", age).withStyle(ChatFormatting.AQUA));
            }

        if (compoundtag.contains("ActiveEffects")) {
            pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.entity.effects").withStyle(ChatFormatting.LIGHT_PURPLE));
            ListTag listtag = compoundtag.getList("ActiveEffects", 10);
            for(int i = 0; i < listtag.size(); ++i) {
                MobEffectInstance effect = MobEffectInstance.load(listtag.getCompound(i));
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.entity.effect", Component.translatable(effect.getDescriptionId()), Component.translatable("potion.potency."+effect.getAmplifier()), MobEffectUtil.formatDuration(effect, 1f)).withStyle(ChatFormatting.GOLD));
            }
        }

        if (compoundtag.contains("Glowing"))
            pTooltipComponents.add(Component.literal("Glowing"));

        if (compoundtag.contains("NoAI"))
            pTooltipComponents.add(Component.literal("NoAI").withStyle(ChatFormatting.GRAY));
        if (compoundtag.contains("Silent"))
            pTooltipComponents.add(Component.literal("Silenced").withStyle(ChatFormatting.GRAY));
        if (compoundtag.contains("NoGravity"))
            pTooltipComponents.add(Component.literal("NoGravity").withStyle(ChatFormatting.GRAY));
        if (compoundtag.contains("Invulnerable"))
            pTooltipComponents.add(Component.literal("Invulnerable").withStyle(ChatFormatting.GRAY));
    }
}
