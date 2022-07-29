package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import org.jetbrains.annotations.NotNull;

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
}
