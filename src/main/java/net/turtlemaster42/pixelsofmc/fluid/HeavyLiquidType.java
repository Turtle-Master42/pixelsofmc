package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.joml.Vector3f;

public class HeavyLiquidType extends BaseFluidType {
    public HeavyLiquidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor, Properties properties) {
        super(stillTexture, flowingTexture, overlayTexture, tintColor, fogColor, 0f, 1f, properties);
    }

    public HeavyLiquidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor, float fogMinDistance, float fogMaxDistance, Properties properties) {
        super(stillTexture, flowingTexture, overlayTexture, tintColor, fogColor, fogMinDistance, fogMaxDistance, properties);
    }

    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        boolean isGoingDown = entity.getDeltaMovement().y <= 0.0D; //is going down
        float movement = 0.02F * (float)entity.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue();

        entity.moveRelative(movement, movementVector); //move entity
        entity.move(MoverType.SELF, entity.getDeltaMovement()); //???
        Vec3 deltaMovement = entity.getDeltaMovement();

        // make you go up while on a ladder and in the fluid
        if (entity.horizontalCollision && entity.onClimbable()) {
            deltaMovement = new Vec3(deltaMovement.x, 0.2D, deltaMovement.z);
        }

        entity.setDeltaMovement(deltaMovement.multiply(0.75f, 0.5F, 0.75f)); //apply movement + slowdown
        entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y + 0.07f, entity.getDeltaMovement().z); //apply movement + slowdown


        //falling in fluid logic, because that should push you down
        Vec3 fallingAdjustedMovement = entity.getFluidFallingAdjustedMovement(gravity, isGoingDown, entity.getDeltaMovement());
        entity.setDeltaMovement(fallingAdjustedMovement);
        if (entity.horizontalCollision && entity.isFree(fallingAdjustedMovement.x, fallingAdjustedMovement.y + 0.6d, fallingAdjustedMovement.z)) {
            entity.setDeltaMovement(fallingAdjustedMovement.x, 0.3f, fallingAdjustedMovement.z);
        }
        return true;
    }
}
