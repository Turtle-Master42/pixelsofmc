package net.turtlemaster42.pixelsofmc.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMentities;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RiverShellEntity extends Animal {
    private int speedingTime = 0;
    private int inShell = 0;

    public RiverShellEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ARMOR, 8.0d)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1d)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1d)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.12f).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, ArmorStand.class, 5f, 2d, 2d));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.3f));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        //this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 2f, 0.3f));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pOtherParent) {
        return POMentities.RIVER_SHELL.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SEAGRASS);
    }

    @Override
    public boolean canBreed() {
        return true;
    }

    public boolean isInShell() {
        return inShell != 0;
    }

    public int getInShell() {
        return inShell;
    }

    @Override
    public @NotNull InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {

        if (pPlayer.isCrouching() && pPlayer.getMainHandItem().isEmpty()) {
            if (!this.dead) {
                ItemStack stack = new ItemStack(POMitems.RIVER_SHELL.get(), 1);
                storeEntityDataInStack(stack, this);
                this.remove(RemovalReason.UNLOADED_WITH_PLAYER);
                pPlayer.addItem(stack);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        if (inShell != 0)
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0 , 0, 0);

        if (speedingTime > 0) {
            level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0 , 0, 0);
            if (speedingTime >= 50) {
                inShell = 1;
            }
        }

        if (!this.isOnGround()) {
            Vec3 deltaMovement = this.getDeltaMovement();
            double combinedSpeed = Math.abs(deltaMovement.x) + Math.abs(deltaMovement.y) + Math.abs(deltaMovement.z);
            //PixelsOfMc.LOGGER.info("combinedSpeed: {}", combinedSpeed);
            if (combinedSpeed >= 0.75) {
                speedingTime = speedingTime + 10;
            }
        } else {
            if (speedingTime == 1) {
                inShell = 0;
                speedingTime = 0;
            }
            else speedingTime--;
        }

        super.tick();
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
        return !isInShell();
    }

    public static void storeEntityDataInStack(ItemStack stack, RiverShellEntity riverShell) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        if (riverShell.hasCustomName())
            stack.setHoverName(riverShell.getCustomName());
        if (riverShell.isNoAi())
            compoundtag.putBoolean("NoAI", riverShell.isNoAi());
        if (riverShell.isSilent())
            compoundtag.putBoolean("Silent", riverShell.isSilent());
        if (riverShell.isNoGravity())
            compoundtag.putBoolean("NoGravity", riverShell.isNoGravity());
        if (riverShell.hasGlowingTag())
            compoundtag.putBoolean("Glowing", true);
        if (riverShell.isInvulnerable())
            compoundtag.putBoolean("Invulnerable", riverShell.isInvulnerable());
        if (riverShell.isPersistenceRequired())
            compoundtag.putBoolean("PersistenceRequired", riverShell.isPersistenceRequired());

        if (!riverShell.getActiveEffects().isEmpty()) {
            ListTag activeEffects = new ListTag();
            for (MobEffectInstance effect : riverShell.getActiveEffects()) {
                activeEffects.add(effect.save(new CompoundTag()));
            }
            compoundtag.put("ActiveEffects", activeEffects);
        }

        compoundtag.putFloat("Health", riverShell.getHealth());
        compoundtag.putInt("ForcedAge", riverShell.forcedAge);
        compoundtag.putInt("Age", riverShell.getAge());
    }

    public static void loadEntityDataFromStack(ItemStack stack, RiverShellEntity riverShell) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        if (compoundtag.contains("NoAI"))
            riverShell.setNoAi(compoundtag.getBoolean("NoAI"));
        if (compoundtag.contains("Silent"))
            riverShell.setSilent(compoundtag.getBoolean("Silent"));
        if (compoundtag.contains("NoGravity"))
            riverShell.setNoGravity(compoundtag.getBoolean("NoGravity"));
        if (compoundtag.contains("Glowing"))
            riverShell.setGlowingTag(compoundtag.getBoolean("Glowing"));
        if (compoundtag.contains("Invulnerable"))
            riverShell.setInvulnerable(compoundtag.getBoolean("Invulnerable"));
        if (compoundtag.contains("PersistenceRequired", 99))
            if (compoundtag.getBoolean("PersistenceRequired"))
                riverShell.setPersistenceRequired();
        if (compoundtag.contains("ActiveEffects")) {
            ListTag listtag = compoundtag.getList("ActiveEffects", 10);
            for(int i = 0; i < listtag.size(); ++i) {
                riverShell.addEffect(Objects.requireNonNull(MobEffectInstance.load(listtag.getCompound(i))));
            }
        }

        if (compoundtag.contains("Health", 99))
            riverShell.setHealth(compoundtag.getFloat("Health"));
        if (compoundtag.contains("ForcedAge", 99))
            riverShell.forcedAge = compoundtag.getInt("ForcedAge");
        if (compoundtag.contains("Age", 99))
            riverShell.setAge(compoundtag.getInt("Age"));
    }
}
