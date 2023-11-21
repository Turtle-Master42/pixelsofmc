package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.block.BallMillBlock;
import net.turtlemaster42.pixelsofmc.block.tile.BallMillTile;
import net.turtlemaster42.pixelsofmc.block.tile.HotIsostaticPressTile;

public class ModelBallMill extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox box;
    private final AdvancedModelBox ring;


    public ModelBallMill() {
        texWidth = 160;
        texHeight = 160;
        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        box = new AdvancedModelBox(this, "box");
        box.setTextureOffset(0, 0).addBox(-14.0F, -22F, -13.99F, 28f, 35f, 28f, 0.0f, false);
        box.setRotationPoint(8.0F, 0.0F, -16.0F);
        root.addChild(box);

        ring = new AdvancedModelBox(this, "ring");
        ring.setTextureOffset(0, 63).addBox(-16F, -26F, -15.99F, 32f, 4f, 32f, 0.0F, false);
        ring.setRotationPoint(8.0F, 0.0F, -16.0F);
        root.addChild(ring);

        this.updateDefaultPose();
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, box, ring);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {
    }

    public void renderMill(BallMillTile tile, float partialTick) {
        this.resetToDefaultPose();
        ring.rotateAngleY = (float) tile.rotation / 10 / Mth.TWO_PI;
        box.rotateAngleY = (float) tile.rotation / 10 / Mth.TWO_PI;
    }
}
