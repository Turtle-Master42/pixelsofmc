package net.turtlemaster42.pixelsofmc.entity.client;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;
import net.turtlemaster42.pixelsofmc.util.renderer.POMAdvancedModelBox;

public class RiverShellModel extends AdvancedEntityModel<RiverShellEntity> {
    private final POMAdvancedModelBox root;
    private final POMAdvancedModelBox shell;
    private final POMAdvancedModelBox shell_retractable;
    private final POMAdvancedModelBox head;
    private final POMAdvancedModelBox head_pivot;
    private final POMAdvancedModelBox jaw;
    private final POMAdvancedModelBox front_leg_left;
    private final POMAdvancedModelBox front_leg_right;
    private final POMAdvancedModelBox back_leg_left;
    private final POMAdvancedModelBox back_leg_right;
    private final POMAdvancedModelBox front_foot_left;
    private final POMAdvancedModelBox front_foot_right;
    private final POMAdvancedModelBox back_foot_left;
    private final POMAdvancedModelBox back_foot_right;
    private final POMAdvancedModelBox tail;
    private final POMAdvancedModelBox tail_pivot;

    public RiverShellModel() {
        texWidth = 80;
        texHeight = 80;

        root = new POMAdvancedModelBox(this, "root");
        root.setPOMPos(0.0f, 0.0f, 0.0f);

        shell = new POMAdvancedModelBox(this, "shell");
        shell.setPOMPos(0f, 0f, 0f);
        root.addChild(shell);
        shell.setPOMTextureOffset(0, 0).addPOMBox(4f, 5f, 6f, 8f, 3f, 10f, 0.0f, false);
        shell.setPOMTextureOffset(36, 0).addPOMBox(3f, 2f, 6f, 6f, 1f, 10f, 0.0f, false);
        shell.setPOMTextureOffset(0, 13).addPOMBox(3f, 7f, 5f, 6f, 2f, 8f, 0.0f, false);

        shell_retractable = new POMAdvancedModelBox(this, "shell_retractable");
        shell_retractable.setShouldScaleChildren(true);
        shell.addChild(shell_retractable);

        head_pivot = new POMAdvancedModelBox(this, "head_pivot");
        head_pivot.setPOMRotationPoint(0.0f, 1f, 6.0f);
//        shell_retractable.addChild(head_pivot);
        shell.addChild(head_pivot);

        head = new POMAdvancedModelBox(this, "head");
        head_pivot.addChild(head);
        head.setPOMTextureOffset(28, 13).addPOMBox(1.5f, 3.5f, 4f, 3f, 3f, 4f, 0.0f, false);

        jaw = new POMAdvancedModelBox(this, "jaw");
        head.addChild(jaw);
        jaw.setPOMTextureOffset(32, 26).addPOMBox(1.5f, 1.5f, 4f, 3f, 1f, 4f, 0.0f, false);
        jaw.setScale(0.98f, 1f, 0.98f);

        front_leg_left = new POMAdvancedModelBox(this, "front_leg_left");
        shell_retractable.addChild(front_leg_left);
        front_leg_left.setPOMRotationPoint(-3f, 3.05f, 5.5f);
        front_leg_left.setPOMTextureOffset(0, 26).addPOMBox(0f, 0f, 0f, 2f, 3f, 2f, 0.0f, false);

        front_leg_right = new POMAdvancedModelBox(this, "front_leg_right");
        shell_retractable.addChild(front_leg_right);
        front_leg_right.setPOMRotationPoint(3f, 3.05f, 5.5f);
        front_leg_right.setPOMTextureOffset(8, 26).addPOMBox(2f, 0f, 0f, 2f, 3f, 2f, 0.0f, false);

        back_leg_left = new POMAdvancedModelBox(this, "back_leg_left");
        shell_retractable.addChild(back_leg_left);
        back_leg_left.setPOMRotationPoint(-3f, 3.05f, -1.5f);
        back_leg_left.setPOMTextureOffset(16, 26).addPOMBox(0f, 0f, 0f, 2f, 3f, 2f, 0.0f, false);

        back_leg_right = new POMAdvancedModelBox(this, "back_leg_right");
        shell_retractable.addChild(back_leg_right);
        back_leg_right.setPOMRotationPoint(3f, 3.05f, -1.5f);
        back_leg_right.setPOMTextureOffset(24, 26).addPOMBox(2f, 0f, 0f, 2f, 3f, 2f, 0.0f, false);

        front_foot_left = new POMAdvancedModelBox(this, "front_foot_left");
        front_leg_left.addChild(front_foot_left);
        front_foot_left.setPOMTextureOffset(0, 23).addPOMBox(0f, -3f, 1f, 3f, 0.01f, 3f, 0.0f, false);

        front_foot_right = new POMAdvancedModelBox(this, "front_foot_right");
        front_leg_right.addChild(front_foot_right);
        front_foot_right.setPOMTextureOffset(12, 23).addPOMBox(3f, -3f, 1f, 3f, 0.01f, 3f, 0.0f, false);

        back_foot_left = new POMAdvancedModelBox(this, "back_foot_left");
        back_leg_left.addChild(back_foot_left);
        back_foot_left.setPOMTextureOffset(24, 23).addPOMBox(0f, -3f, 1f, 3f, 0.01f, 3f, 0.0f, false);

        back_foot_right = new POMAdvancedModelBox(this, "back_foot_right");
        back_leg_right.addChild(back_foot_right);
        back_foot_right.setPOMTextureOffset(36, 23).addPOMBox(3f, -3f, 1f, 3f, 0.01f, 3f, 0.0f, false);

        tail_pivot = new POMAdvancedModelBox(this, "tail_pivot");
        tail_pivot.setRotationPoint(-0.5f, -3f, 4.0f);
        tail_pivot.rotateAngleX = -45;
        shell_retractable.addChild(tail_pivot);

        tail = new POMAdvancedModelBox(this, "tail");
        tail_pivot.addChild(tail);
        tail.setPOMTextureOffset(0, 31).addPOMBox(0f, 0f, 0f, 1f, 1f, 3f, 0.0f, false);


        this.updateDefaultPose();
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, shell, shell_retractable, head, head_pivot, front_leg_left, front_leg_right, back_leg_left, back_leg_right, front_foot_left, front_foot_right, back_foot_left, back_foot_right, tail_pivot, tail);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(RiverShellEntity riverShellEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.resetToDefaultPose();
        float partialTick = ageInTicks - riverShellEntity.tickCount;
        float retractAnimationTime = riverShellEntity.getRetractAnimationTime() + partialTick; //0 - 10, 10 - 50

        progressRotationPrev(jaw, riverShellEntity.attackAnim, 0f, (float)Math.toRadians(45), 0f, 1);

        progressPositionPrev(head, animationNodeProgress(retractAnimationTime, 0, 7), 0f, 0f, 6f, 1f);
        progressPositionPrev(shell, animationNodeProgress(retractAnimationTime, 0, 10), 0f, 1f, 0f, 1f);
        float legRetractProgress1 = animationNodeProgress(retractAnimationTime, 1, 6);
        progressRotationPrev(front_leg_left, legRetractProgress1, 0, 0, (float)Math.toRadians(-80), 1f);
        progressRotationPrev(front_leg_right, legRetractProgress1, 0, 0, (float)Math.toRadians(80),  1f);
        progressRotationPrev(back_leg_left, legRetractProgress1, 0, 0, (float)Math.toRadians(-80),  1f);
        progressRotationPrev(back_leg_right, legRetractProgress1, 0, 0, (float)Math.toRadians(80),  1f);
        progressPositionPrev(front_leg_left, legRetractProgress1, 0f, 2f, 0f, 1f);
        progressPositionPrev(front_leg_right, legRetractProgress1, 0f, 2f, 0f,  1f);
        progressPositionPrev(back_leg_left, legRetractProgress1, 0f, 2f, 0f,  1f);
        progressPositionPrev(back_leg_right, legRetractProgress1, 0f, 2f, 0f,  1f);
        float legRetractProgress2 = animationNodeProgress(retractAnimationTime, 6, 10);
        progressPositionPrev(front_leg_left, legRetractProgress2, -4f, -1f, 0.6f, 1f);
        progressPositionPrev(front_leg_right, legRetractProgress2, 4f, -1f, 0.6f,  1f);
        progressPositionPrev(back_leg_left, legRetractProgress2, -4f, -1f, 0f,  1f);
        progressPositionPrev(back_leg_right, legRetractProgress2, 4f, -1f, 0f,  1f);


        progressPositionPrev(head, animationNodeProgress(retractAnimationTime, 10, 15), 0f, 0f, -4f, 1f);
//        progressRotationPrev(head, animationNodeProgress(retractAnimationTime, 20, 25), (float)Math.toRadians(-45), 0, 0, 1f);
//        progressRotationPrev(head, animationNodeProgress(retractAnimationTime, 25, 35), (float)Math.toRadians(45), 0, 0, 1f);
//        progressRotationPrev(head, animationNodeProgress(retractAnimationTime, 35, 40), 0, 0, 0, 1f);
        progressPositionPrev(head, animationNodeProgress(retractAnimationTime, 40, 45), 0f, 0f, -2f, 1f);
        progressPositionPrev(shell, animationNodeProgress(retractAnimationTime, 45, 50), 0f, -1f, 0f, 1f);


        if (!riverShellEntity.isInShell()) {
            this.faceTarget(Math.max(Math.min(netHeadYaw, 50f), -50f), headPitch, 0.8f, head_pivot);
        }

    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

        matrixStackIn.translate(0f, 1.5f ,0f);

        if (this.young) {
            float f = 1.35f;
            head.setScale(f, f, f);
            head.setShouldScaleChildren(true);
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.5f, 0.5f, 0.5f);
            parts().forEach((part) -> {
                part.render(matrixStackIn, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            });
            matrixStackIn.popPose();
            this.head.setScale(1f, 1f, 1f);
        } else {
            this.head.setScale(1f, 1f, 1f);
            matrixStackIn.pushPose();
            parts().forEach((part) -> {
                part.render(matrixStackIn, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            });
            matrixStackIn.popPose();
        }
    }

    public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
        AdvancedModelBox.rotateAngleX = x;
        AdvancedModelBox.rotateAngleY = y;
        AdvancedModelBox.rotateAngleZ = z;
    }

    public float animationNodeProgress(float progress, float nodeStart, float nodeEnd) {
        return Math.min(Math.max(progress - nodeStart, 0) / (nodeEnd - nodeStart), 1f);
    }
}
