package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.tile.StarTile;

public class BigStarModel extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox big_star;

    public BigStarModel() {
        texWidth = 128;
        texHeight = 64;

        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        big_star = new AdvancedModelBox(this, "big_star");
        big_star.setRotationPoint(0.0F, 0F, 0F);
        root.addChild(big_star);
        big_star.setTextureOffset(0, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, false);
        big_star.setScale(0, 0, 0);

        this.updateDefaultPose();
    }


    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, big_star);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {}

    public void renderBigStar(StarTile tile, float partialTick) {
        this.resetToDefaultPose();
        float rotation = (tile.ticksExisted + partialTick + (tile.getBlockPos().getX()+tile.getBlockPos().getY()+tile.getBlockPos().getZ())) / 48;
        float scale = 1 + (Mth.sin(rotation) / 6);
        big_star.rotateAngleX = rotation * 1.2f;
        big_star.rotateAngleY = rotation;
        big_star.rotateAngleZ = rotation * 0.8f;
        big_star.setScale(scale, scale, scale);
    }
}
