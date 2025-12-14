package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.tile.StarTile;

public class BlackHoleModel extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox star_1;
    private final AdvancedModelBox star_2;
    private final AdvancedModelBox star_3;

    public BlackHoleModel() {
        texWidth = 192;
        texHeight = 208;

        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        star_1 = new AdvancedModelBox(this, "star_1");
        star_1.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(star_1);
        star_1.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, true);
        star_1.setScale(0, 0, 0);

        star_2 = new AdvancedModelBox(this, "star_2");
        star_2.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(star_2);
        star_2.setTextureOffset(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        star_2.setScale(0, 0, 0);

        star_3 = new AdvancedModelBox(this, "star_3");
        star_3.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(star_3);
        star_3.setTextureOffset(0, 64).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        star_3.setScale(0, 0, 0);


        //box(locX, locY, locZ, sizeX, sizeY, sizeZ, ?, flip?)
        this.updateDefaultPose();
    }


    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, star_1, star_2, star_3);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {
    }


    public void renderBlackHole(StarTile tile, float partialTick, Vec3 vec3) {
        this.resetToDefaultPose();
        float rotation = (tile.ticksExisted + partialTick + (tile.getBlockPos().getX()+tile.getBlockPos().getY()+tile.getBlockPos().getZ())) / 2;

        Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        Vec3 tilePos = tile.getBlockPos().getCenter();
        double dx = camPos.x - tilePos.x;
        double dy = camPos.y - tilePos.y;
        double dz = camPos.z - tilePos.z;
        Vec3 camDirection = Vec3.ZERO.add(dx, dy, dz);
        camDirection = camDirection.scale(7 / camDirection.length());

        star_1.rotateAngleX = rotation;
        star_1.rotateAngleY = rotation * 0.1f;
        star_1.rotateAngleZ = rotation * 0.11f;
        star_1.setScale(1, 1, 1);

        star_2.rotateAngleX = rotation;
        star_2.rotateAngleY = rotation * 0.1f;
        star_2.rotateAngleZ = rotation * 0.11f;
        star_2.setPos((float) (star_2.defaultPositionX - camDirection.x), (float) (star_2.defaultPositionY - camDirection.y), (float) (star_2.defaultPositionZ - camDirection.z));
        star_2.setScale(1.12f, 1.12f, 1.12f);

        star_3.rotateAngleX = rotation;
        star_3.rotateAngleY = rotation * 0.1f;
        star_3.rotateAngleZ = rotation * 0.11f;
        star_3.setPos((float) (star_3.defaultPositionX - camDirection.x * 2), (float) (star_3.defaultPositionY - camDirection.y * 2), (float) (star_3.defaultPositionZ - camDirection.z * 2));
        star_3.setScale(1.33f, 1.33f, 1.33f);


//
//        Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
//        Vec3 tilePos = tile.getBlockPos().getCenter();
//        double dx = camPos.x - tilePos.x;
//        double dy = camPos.y - tilePos.y;
//        double dz = camPos.z - tilePos.z;
//        double dl = Math.sqrt(dx * dx + dz * dz);
//        if (dy < 0) {
//            light_bend_below.setScale(1, 1,1);
//            light_bend_above.setScale(0, 0,0);
//            light_bend_below.rotateAngleX = (float) (Mth.atan2(dy, dl) + Math.toRadians(90));
//            light_bend_below.rotateAngleY = (float) (-Mth.atan2(-dz, -dx) + Math.toRadians(90));
//        } else {
//            light_bend_above.setScale(1, 1,1);
//            light_bend_below.setScale(0, 0,0);
//            light_bend_above.rotateAngleX = (float) (-Mth.atan2(dy, dl) + Math.toRadians(90));
//            light_bend_above.rotateAngleY = (float) (-Mth.atan2(dz, dx) + Math.toRadians(90));
//        }
//
//        disc.setScale(1, 1,1);
    }

    protected static double getXRotD(Vec3 toPos, Vec3 fromPos) {
        double dx = toPos.x - fromPos.x;
        double dy = toPos.y - fromPos.y;
        double dz = toPos.z - fromPos.z;
        double sqrt = Math.sqrt(dx * dx + dz * dz);
        if (dy < 0) {
            return Mth.atan2(dy, sqrt);
        }
        return -Mth.atan2(dy, sqrt);
    }

    protected static double getYRotD(Vec3 toPos, Vec3 fromPos) {
        double dx = toPos.x - fromPos.x;
        double dy = toPos.y - fromPos.y;
        double dz = toPos.z - fromPos.z;
        if (dy < 0) {
            return -Mth.atan2(-dz, -dx);
        }
        return -Mth.atan2(dz, dx);
    }
}
