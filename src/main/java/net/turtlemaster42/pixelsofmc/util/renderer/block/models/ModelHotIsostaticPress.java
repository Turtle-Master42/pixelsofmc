package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.block.tile.HotIsostaticPressTile;

public class ModelHotIsostaticPress extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox plate;
    private final AdvancedModelBox pillar;
    private final AdvancedModelBox cylinder;
    private final AdvancedModelBox ring_1;
    private final AdvancedModelBox ring_2;
    private final AdvancedModelBox ring_3;
    private final AdvancedModelBox indicator;
    private final AdvancedModelBox needle;

    public ModelHotIsostaticPress() {
        texWidth = 256;
        texHeight = 256;
        root = new AdvancedModelBox(this, "root");

        plate = new AdvancedModelBox(this, "plate");
        plate.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -4.0F, 32f, 32f, 4f, 0.0f, false);
        root.addChild(plate);

        pillar = new AdvancedModelBox(this, "pillar");
        pillar.setTextureOffset(0, 36).addBox(0.0F, 10.0F, -48.0F, 32f, 12f, 44f, 0.0F, false);
        root.addChild(pillar);

        cylinder = new AdvancedModelBox(this, "cylinder");
        cylinder.setTextureOffset(0, 122).addBox(4.0F, 4.0F, -42.0F, 24f, 24f, 32f, 0.0F, false);
        root.addChild(cylinder);

        ring_1 = new AdvancedModelBox(this, "ring_1");
        ring_1.setTextureOffset(0, 92).addBox(3.0F, 3.0F, -19.0F, 26f, 26f, 4f, 0.0F, false);
        root.addChild(ring_1);

        ring_2 = new AdvancedModelBox(this, "ring_2");
        ring_2.setTextureOffset(0, 92).addBox(3.0F, 3.0F, -28.0F, 26f, 26f, 4f, 0.0F, false);
        root.addChild(ring_2);

        ring_3 = new AdvancedModelBox(this, "ring_3");
        ring_3.setTextureOffset(0, 92).addBox(3.0F, 3.0F, -37.0F, 26f, 26f, 4f, 0.0F, false);
        root.addChild(ring_3);

        indicator = new AdvancedModelBox(this, "indicator");
        indicator.setPos(32.0F, 13.0F, -20.0F);
        indicator.setTextureOffset(0, 4).addBox(0f, 0f, 0f, 1f, 6f, 6f, 0.0F, false);
        root.addChild(indicator);

        needle = new AdvancedModelBox(this, "needle");
        needle.setPos(1.2f, 2f, 5f);
        needle.setTextureOffset(0, 48).addBox(0f, 0f, 0f, 0.5f, 4f, 1f, 0.0F, false);
        indicator.addChild(needle);

        this.updateDefaultPose();
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, plate, pillar, cylinder, ring_1, ring_2, ring_3, indicator, needle);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

//    @Override
//    public void m_6973_(Entity entity, float v, float v1, float v2, float v3, float v4) {
//    }

    public void renderIndecator(HotIsostaticPressTile tile, float partialTick) {
        float rotation = (float)(0.0017*tile.getHeat())/Mth.TWO_PI;
        this.setRotateAngle(needle, -rotation , 0 ,0);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {

    }
}
