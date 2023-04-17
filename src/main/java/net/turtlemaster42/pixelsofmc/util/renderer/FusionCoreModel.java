package net.turtlemaster42.pixelsofmc.util.renderer;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;

public class FusionCoreModel extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox star_1;
    private final AdvancedModelBox star_2;
    private final AdvancedModelBox big_star;
    private final AdvancedModelBox disc;

    public FusionCoreModel() {
        texWidth = 192;
        texHeight = 112;


        root = new AdvancedModelBox(this, "root");
        root.setPos(0.5f, 0.5f, 0.5f);
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        star_1 = new AdvancedModelBox(this, "star_1");
        star_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(star_1);
        star_1.setTextureOffset(0 ,0).addBox(0f, 0f, 0f, 16.1F,16.1F, 16.1F, 0.0F, false);

        star_2 = new AdvancedModelBox(this, "star_2");
        star_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(star_2);
        star_2.setTextureOffset(0 ,32).addBox(0f, 0f, 0f, 16.1F,16.1F, 16.1F, 0.0F, false);

        big_star = new AdvancedModelBox(this, "big_star");
        big_star.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(big_star);
        big_star.setTextureOffset(64 ,0).addBox(0f, 0f, 0f, 32.0F,32.0F, 32.0F, 0.0F, false);

        disc = new AdvancedModelBox(this, "disc");
        disc.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(disc);
        disc.setTextureOffset(0 ,64).addBox(0f, 0f, 0f, 48.0F,48.0F, 0.0F, 0.0F, false);

        this.updateDefaultPose();
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

//    @Override
//    public void m_6973_(Entity entity, float v, float v1, float v2, float v3, float v4) {
//    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, star_1, star_2, big_star, disc);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void renderStar(SDSFusionControllerTile tile, float partialTick) {
        this.resetToDefaultPose();
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
    }
}
