package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.network.PixelFluidItemHandlerSimple;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.world.level.block.LiquidBlock.LEVEL;

public class BigBucket extends Item {
    public final int capacity;
    public BigBucket(Item.Properties properties, int bucketMax) {
        super(properties.stacksTo(1));
        this.capacity = bucketMax;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new PixelFluidItemHandlerSimple(stack, capacity);
    }

    @Nonnull
    public FluidStack getFluid(ItemStack stack) {
        CompoundTag tagCompound = stack.getTag();
        if (tagCompound == null || !tagCompound.contains("Fluid"))
            return FluidStack.EMPTY;

        return FluidStack.loadFluidStackFromNBT(tagCompound.getCompound("Fluid"));
    }

    protected void setFluid(ItemStack stack, FluidStack fluid) {
        if (!stack.hasTag())
            stack.setTag(new CompoundTag());

        CompoundTag fluidTag = new CompoundTag();
        fluid.writeToNBT(fluidTag);
        stack.getTag().put("Fluid", fluidTag);
    }

    public int getColor(ItemStack stack) {
        return getBarColor(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null);
        if (fluidHandlerItem.getFluidInTank(0).isEmpty()) {return 16;}
        return 1;
    }

    //TODO: Try to fix stacked buckets moving up and down even though no valid interaction took place
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack baseStack = pPlayer.getItemInHand(pHand);
        ItemStack itemStack = baseStack.copyWithCount(1);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(pLevel, pPlayer, getFluid(itemStack).getAmount() >= capacity ? ClipContext.Fluid.NONE : ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        }

        BlockPos blockpos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getDirection();
        BlockPos relativePos = blockpos.relative(direction);

        if (!pLevel.mayInteract(pPlayer, blockpos) || !pPlayer.mayUseItemAt(relativePos, direction, itemStack)) {
            return InteractionResultHolder.pass(itemStack);
        }

        IFluidHandlerItem fluidItem = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null);
        FluidStack fluid = fluidItem.getFluidInTank(0);
        BlockState state = pLevel.getBlockState(blockpos);
        boolean inCreative = pPlayer.getAbilities().instabuild;

        //pickup
        if (fluid.isEmpty() || (fluid.getAmount() <= capacity - 1000  && !pPlayer.isCrouching())) {
            if (state.getBlock() instanceof LiquidBlock liquidBlock && (liquidBlock.getFluid().getSource().equals(fluid.getRawFluid()) || fluid.isEmpty()) && state.getValue(LEVEL) == 0) {
                pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);

                fluidItem.fill(new FluidStack(liquidBlock.getFluid().getSource(), 1000), IFluidHandler.FluidAction.EXECUTE);
                //stats, sounds and events
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                liquidBlock.getPickupSound(state).ifPresent((p_150709_) -> pPlayer.playSound(p_150709_, 1.0F, 1.0F));
                pLevel.gameEvent(pPlayer, GameEvent.FLUID_PICKUP, blockpos);
                if (!pLevel.isClientSide) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) pPlayer, itemStack);
                }

                //stacked bucket logic
                if (fluid.isEmpty()) {
                    baseStack.shrink(1);
                    if (baseStack.isEmpty()) {
                        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
                    } else {
                        if (!pPlayer.getInventory().add(itemStack)) {
                            pPlayer.drop(itemStack, false);
                        }
                        return InteractionResultHolder.sidedSuccess(baseStack, pLevel.isClientSide());
                    }
                }
                return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
            }
        }
        //place
        if (!fluid.isEmpty()) {
            BlockPos placePos = canBlockContainFluid(pLevel, blockpos, state, fluid.getFluid()) ? blockpos : relativePos;
            if (this.emptyContents(pPlayer, pLevel, placePos, blockHitResult, itemStack)) {
                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, placePos, itemStack);
                }
                if (!inCreative) {
                    fluidItem.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                }
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
            }
        }
        return InteractionResultHolder.fail(itemStack);
    }

    public boolean emptyContents(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, @Nullable BlockHitResult pResult, @NotNull ItemStack pItemStack) {
        Fluid fluid = getFluid(pItemStack).getFluid();
        if (!(fluid instanceof FlowingFluid)) {return false;}
        BlockState blockstate = pLevel.getBlockState(pPos);
        Block block = blockstate.getBlock();
        boolean canBeReplaced = blockstate.canBeReplaced(fluid);
        boolean conditionsMet = blockstate.isAir() || canBeReplaced || block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(pLevel, pPos, blockstate, fluid);
        java.util.Optional<net.minecraftforge.fluids.FluidStack> containedFluidStack = java.util.Optional.of(pItemStack).flatMap(net.minecraftforge.fluids.FluidUtil::getFluidContained);
        if (!conditionsMet) {
            return pResult != null && this.emptyContents(pPlayer, pLevel, pResult.getBlockPos().relative(pResult.getDirection()), null, pItemStack);

        //vaporize
        } else if (containedFluidStack.isPresent() && fluid.getFluidType().isVaporizedOnPlacement(pLevel, pPos, containedFluidStack.get())) {
            fluid.getFluidType().onVaporize(pPlayer, pLevel, pPos, containedFluidStack.get());
            return true;

        //vaporize water
        } else if (pLevel.dimensionType().ultraWarm() && fluid.is(FluidTags.WATER)) {
            int x = pPos.getX();
            int y = pPos.getY();
            int z = pPos.getZ();
            pLevel.playSound(pPlayer, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);
            for(int l = 0; l < 8; ++l) {
                pLevel.addParticle(ParticleTypes.LARGE_SMOKE, (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
            }
            return true;

        //gas
        } else if (fluid.getFluidType().isLighterThanAir()) {
            int x = pPos.getX();
            int y = pPos.getY();
            int z = pPos.getZ();
            pLevel.playSound(pPlayer, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);
            for(int l = 0; l < 8; ++l) {
                pLevel.addParticle(ParticleTypes.CLOUD, (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
                if (fluid.getFluidType().getTemperature() > 1000) {
                    pLevel.addParticle(ParticleTypes.FLAME, (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            return true;

        //place lava
        } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(pLevel, pPos, blockstate, fluid)) {
            ((LiquidBlockContainer)block).placeLiquid(pLevel, pPos, blockstate, ((FlowingFluid)fluid).getSource(false));
            SoundEvent soundevent = fluid.getFluidType().getSound(pPlayer, pLevel, pPos, net.minecraftforge.common.SoundActions.BUCKET_EMPTY);
            if(soundevent == null) soundevent = fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
            pLevel.playSound(pPlayer, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
            pLevel.gameEvent(pPlayer, GameEvent.FLUID_PLACE, pPos);
            return true;

        //place fluid
        } else {
            if (!pLevel.isClientSide && canBeReplaced && !blockstate.liquid()) {
                pLevel.destroyBlock(pPos, true);
            }

            if (!pLevel.setBlock(pPos, fluid.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                return false;

            } else {
                SoundEvent soundevent = fluid.getFluidType().getSound(pPlayer, pLevel, pPos, net.minecraftforge.common.SoundActions.BUCKET_EMPTY);
                if(soundevent == null) soundevent = fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
                pLevel.playSound(pPlayer, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
                pLevel.gameEvent(pPlayer, GameEvent.FLUID_PLACE, pPos);
                return true;
            }
        }
    }

    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate, Fluid fluid) {
        return (blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, fluid)) || blockstate.getFluidState().is(fluid);
    }

    public int getBarWidth(ItemStack pStack) {
        IFluidHandlerItem fluid = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null);
        return Math.round((float)fluid.getFluidInTank(0).getAmount() * 13.0F / capacity);
    }

    public int getBarColor(ItemStack pStack) {
        Fluid fluid = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null).getFluidInTank(0).getFluid();
        if (fluid == Fluids.LAVA) return -44273;    //(new Color(255, 83, 15)).getRGB()
        return IClientFluidTypeExtensions.of(fluid).getTintColor(new FluidStack(fluid, 1));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        FluidStack fluid = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null).getFluidInTank(0);
        return fluid.getAmount() < capacity && fluid.getAmount() > 0 && !fluid.isEmpty();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        FluidStack fluid = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null).getFluidInTank(0);
        if (!fluid.isEmpty()) {
            pTooltipComponents.add(Component.translatable((fluid.getTranslationKey())).withStyle(ChatFormatting.AQUA));
            if (Screen.hasShiftDown()) {
                pTooltipComponents.add(Component.literal(fluid.getAmount() + " mb").withStyle(ChatFormatting.GRAY));
            } else {
                pTooltipComponents.add(Component.literal((fluid.getAmount() / 1000) + " b").withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
