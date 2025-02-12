package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.block.tile.StarTile;

public class StarModel extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox star_1;
    private final AdvancedModelBox star_2;
    private final AdvancedModelBox star_3;
    private final AdvancedModelBox big_star;
    private final AdvancedModelBox disc;
    private final AdvancedModelBox light_bend_above;
    private final AdvancedModelBox light_bend_below;

    public StarModel() {
        texWidth = 192;
        texHeight = 208;

        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        star_1 = new AdvancedModelBox(this, "star_1");
        star_1.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(star_1);
        star_1.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        star_1.setScale(0, 0, 0);

        star_2 = new AdvancedModelBox(this, "star_2");
        star_2.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(star_2);
        star_2.setTextureOffset(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        star_2.setScale(0, 0, 0);

        star_3 = new AdvancedModelBox(this, "star_3");
        star_3.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(star_3);
        star_3.setTextureOffset(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        star_3.setScale(0, 0, 0);


        big_star = new AdvancedModelBox(this, "big_star");
        big_star.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(big_star);
        big_star.setTextureOffset(64, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, false);
        big_star.setScale(0, 0, 0);

        disc = new AdvancedModelBox(this, "disc");
        disc.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(disc);
        disc.setTextureOffset(0, 64).addBox(-24.0F, 0.0F, -24.0F, 48.0F, 0.0F, 48.0F, 0.0F, false);
        disc.setScale(0, 0, 0);

        light_bend_above = new AdvancedModelBox(this, "light_bend_above");
        light_bend_above.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(light_bend_above);
        light_bend_above.setTextureOffset(0, 112).addBox(-24.0F, 0.0F, -24.0F, 48.0F, 0.0F, 48.0F, 0.0F, false);
        light_bend_above.setScale(0, 0, 0);

        light_bend_below = new AdvancedModelBox(this, "light_bend_below");
        light_bend_below.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(light_bend_below);
        light_bend_below.setTextureOffset(0, 160).addBox(-24.0F, 0.0F, -24.0F, 48.0F, 0.0F, 48.0F, 0.0F, false);
        light_bend_below.setScale(0, 0, 0);


        //box(locX, locY, locZ, sizeX, sizeY, sizeZ, ?, flip?)
        this.updateDefaultPose();
    }


    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, star_1, star_2, star_3, big_star, disc, light_bend_above, light_bend_below);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {
    }

    public void renderStar(StarTile tile, float partialTick) {
        this.resetToDefaultPose();
        star_2.setScale(0,0,0);
        star_3.setScale(0,0,0);
        big_star.setScale(0,0,0);
        disc.setScale(0,0,0);
        light_bend_above.setScale(0,0,0);
        light_bend_below.setScale(0,0,0);

        float rotation = (tile.ticksExisted + partialTick + (tile.getBlockPos().getX()+tile.getBlockPos().getY()+tile.getBlockPos().getZ())) / 32;
        float scale = 1 + (Mth.sin(rotation) / 8);
        star_1.rotateAngleX = rotation;
        star_1.rotateAngleY = rotation * 0.8f;
        star_1.rotateAngleZ = rotation * 1.2f;
        star_1.setScale(scale, scale, scale);
    }

    public void renderBigStar(StarTile tile, float partialTick) {
        this.resetToDefaultPose();
        star_1.setScale(0,0,0);
        star_2.setScale(0,0,0);
        star_3.setScale(0,0,0);
        disc.setScale(0,0,0);
        light_bend_above.setScale(0,0,0);
        light_bend_below.setScale(0,0,0);

        float rotation = (tile.ticksExisted + partialTick + (tile.getBlockPos().getX()+tile.getBlockPos().getY()+tile.getBlockPos().getZ())) / 48;
        float scale = 1 + (Mth.sin(rotation) / 6);
        big_star.rotateAngleX = rotation * 1.2f;
        big_star.rotateAngleY = rotation;
        big_star.rotateAngleZ = rotation * 0.8f;
        big_star.setScale(scale, scale, scale);
    }

    public void renderNeutronStar(StarTile tile, float partialTick) {
        this.resetToDefaultPose();
        star_3.setScale(0,0,0);
        big_star.setScale(0,0,0);
        disc.setScale(0,0,0);
        light_bend_above.setScale(0,0,0);
        light_bend_below.setScale(0, 0,0);

        float rotation = (tile.ticksExisted + partialTick + (tile.getBlockPos().getX()+tile.getBlockPos().getY()+tile.getBlockPos().getZ())) / 8;
        float scale = 1 + (Mth.sin(rotation) / 6);
        float orbit_x = (Mth.cos(5*rotation)) * 5;
        float orbit_z = (Mth.sin(5*rotation)) * 5;

        star_1.setPos(star_1.defaultPositionX + orbit_x, star_1.defaultPositionY, star_1.defaultPositionZ + orbit_z);
        star_2.setPos(star_2.defaultPositionX - orbit_x, star_2.defaultPositionY, star_2.defaultPositionZ - orbit_z);

        star_1.rotateAngleX = rotation;
        star_1.rotateAngleY = rotation * 0.8f;
        star_1.rotateAngleZ = rotation * 1.2f;
        star_1.setScale(scale, scale, scale);
        star_2.rotateAngleX = rotation * -0.9f;
        star_2.rotateAngleY = rotation;
        star_2.rotateAngleZ = rotation * -1.1f;
        star_2.setScale(scale, scale, scale);
    }

    public void renderBlackHole(StarTile tile, float partialTick, Vec3 vec3) {
        this.resetToDefaultPose();
        big_star.setScale(0,0,0);

        float rotation = (tile.ticksExisted + partialTick + (tile.getBlockPos().getX()+tile.getBlockPos().getY()+tile.getBlockPos().getZ())) / 2;
        float scale = 0.9f;
        star_1.rotateAngleX = rotation * 1;
        star_1.rotateAngleY = rotation * 0.1f;
        star_1.rotateAngleZ = rotation * 0.11f;
        star_1.setScale(scale, scale, scale);
        star_2.rotateAngleX = rotation * 0.1f;
        star_2.rotateAngleY = rotation * 1.1f;
        star_2.rotateAngleZ = rotation * 0.12f;
        star_2.setScale(scale, scale, scale);
        star_3.rotateAngleX = rotation * 0.12f;
        star_3.rotateAngleY = rotation * 0.11f;
        star_3.rotateAngleZ = rotation * 1.25f;
        star_3.setScale(scale, scale, scale);


        Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        Vec3 tilePos = tile.getBlockPos().getCenter();
        double dx = camPos.x - tilePos.x;
        double dy = camPos.y - tilePos.y;
        double dz = camPos.z - tilePos.z;
        double dl = Math.sqrt(dx * dx + dz * dz);
        if (dy < 0) {
            light_bend_below.setScale(1, 1,1);
            light_bend_above.setScale(0, 0,0);
            light_bend_below.rotateAngleX = (float) (Mth.atan2(dy, dl) + Math.toRadians(90));
            light_bend_below.rotateAngleY = (float) (-Mth.atan2(-dz, -dx) + Math.toRadians(90));
        } else {
            light_bend_above.setScale(1, 1,1);
            light_bend_below.setScale(0, 0,0);
            light_bend_above.rotateAngleX = (float) (-Mth.atan2(dy, dl) + Math.toRadians(90));
            light_bend_above.rotateAngleY = (float) (-Mth.atan2(dz, dx) + Math.toRadians(90));
        }

        disc.setScale(1, 1,1);
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
