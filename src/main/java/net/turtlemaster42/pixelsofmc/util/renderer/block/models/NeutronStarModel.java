package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.tile.StarTile;

public class NeutronStarModel extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox star_1;
    private final AdvancedModelBox star_2;
    public NeutronStarModel() {
        texWidth = 64;
        texHeight = 32;

        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        star_1 = new AdvancedModelBox(this, "star_1");
        star_1.setRotationPoint(0F, 0F, 0F);
        root.addChild(star_1);
        star_1.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        star_1.setScale(0, 0, 0);

        star_2 = new AdvancedModelBox(this, "star_2");
        star_2.setRotationPoint(0F, 0F, 0F);
        root.addChild(star_2);
        star_2.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        star_2.setScale(0, 0, 0);
        this.updateDefaultPose();
    }


    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, star_1, star_2);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {}

    public void renderNeutronStar(StarTile tile, float partialTick) {
        this.resetToDefaultPose();

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
}
