package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Test extends Item {

    public Test(Properties properties) {
        super(properties);
    }

    @NotNull
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.EAT;
    }

    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int Int) {
        if (livingEntity instanceof Player) {
            livingEntity.startSleeping(livingEntity.getOnPos());
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        player.startAutoSpinAttack(20);
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, itemstack);
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("tooltip.pixelsofmc.test", this.category));
        pTooltipComponents.add(new TranslatableComponent("tooltip.pixelsofmc." + pStack.getItem()).withStyle(pStack.getRarity().color));
    }


}
