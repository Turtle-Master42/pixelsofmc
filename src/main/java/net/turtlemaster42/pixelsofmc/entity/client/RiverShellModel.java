package net.turtlemaster42.pixelsofmc.entity.client;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
        root.setPOMPos(0.0F, 0.0F, 0.0F);

        shell = new POMAdvancedModelBox(this, "shell");
        shell.setPOMPos(0f, 0f, 0f);
        root.addChild(shell);
        shell.setPOMTextureOffset(0, 0).addPOMBox(4F, 5F, 6F, 8F, 3F, 10F, 0.0F, false);
        shell.setPOMTextureOffset(36, 0).addPOMBox(3F, 2F, 6F, 6F, 1F, 10F, 0.0F, false);
        shell.setPOMTextureOffset(0, 13).addPOMBox(3F, 7F, 5F, 6F, 2F, 8F, 0.0F, false);

        shell_retractable = new POMAdvancedModelBox(this, "head_pivot");
        shell_retractable.setShouldScaleChildren(true);
        shell.addChild(shell_retractable);

        head_pivot = new POMAdvancedModelBox(this, "head_pivot");
        head_pivot.setRotationPoint(0.0F, -1F, -6.0F);
        shell_retractable.addChild(head_pivot);

        head = new POMAdvancedModelBox(this, "head");
        head_pivot.addChild(head);
        head.setPOMTextureOffset(28, 13).addPOMBox(1.5F, 3.5f, 4F, 3F, 3F, 4F, 0.0F, false);

        jaw = new POMAdvancedModelBox(this, "jaw");
        head.addChild(jaw);
        jaw.setPOMTextureOffset(32, 26).addPOMBox(1.5F, 1.5f, 4F, 3F, 1F, 4F, 0.0F, false);
        jaw.setScale(0.98f, 1f, 0.98f);

        front_leg_left = new POMAdvancedModelBox(this, "front_leg_left");
        shell_retractable.addChild(front_leg_left);
        front_leg_left.setPOMTextureOffset(0, 26).addPOMBox(-3F, 3.05f, 5.5F, 2F, 3F, 2F, 0.0F, false);

        front_leg_right = new POMAdvancedModelBox(this, "front_leg_right");
        shell_retractable.addChild(front_leg_right);
        front_leg_right.setPOMTextureOffset(8, 26).addPOMBox(5F, 3.05f, 5.5F, 2F, 3F, 2F, 0.0F, false);

        back_leg_left = new POMAdvancedModelBox(this, "back_leg_left");
        shell_retractable.addChild(back_leg_left);
        back_leg_left.setPOMTextureOffset(16, 26).addPOMBox(-3F, 3.05f, -1.5F, 2F, 3F, 2F, 0.0F, false);

        back_leg_right = new POMAdvancedModelBox(this, "back_leg_right");
        shell_retractable.addChild(back_leg_right);
        back_leg_right.setPOMTextureOffset(24, 26).addPOMBox(5F, 3.05f, -1.5F, 2F, 3F, 2F, 0.0F, false);

        front_foot_left = new POMAdvancedModelBox(this, "front_foot_left");
        front_leg_left.addChild(front_foot_left);
        front_foot_left.setPOMTextureOffset(0, 23).addPOMBox(-3F, 0.05f, 6.5F, 3F, 0F, 3F, 0.0F, false);

        front_foot_right = new POMAdvancedModelBox(this, "front_foot_right");
        front_leg_right.addChild(front_foot_right);
        front_foot_right.setPOMTextureOffset(12, 23).addPOMBox(6F, 0.05f, 6.5F, 3F, 0F, 3F, 0.0F, false);

        back_foot_left = new POMAdvancedModelBox(this, "back_foot_left");
        back_leg_left.addChild(back_foot_left);
        back_foot_left.setPOMTextureOffset(24, 23).addPOMBox(-3F, 0.05f, -0.5F, 3F, 0F, 3F, 0.0F, false);

        back_foot_right = new POMAdvancedModelBox(this, "back_foot_right");
        back_leg_right.addChild(back_foot_right);
        back_foot_right.setPOMTextureOffset(36, 23).addPOMBox(6F, 0.05f, -0.5F, 3F, 0F, 3F, 0.0F, false);

        tail_pivot = new POMAdvancedModelBox(this, "tail_pivot");
        tail_pivot.setRotationPoint(-0.5F, -3F, 4.0F);
        tail_pivot.rotateAngleX = -20;
        shell_retractable.addChild(tail_pivot);

        tail = new POMAdvancedModelBox(this, "tail");
        tail_pivot.addChild(tail);
        tail.setPOMTextureOffset(0, 31).addPOMBox(0F, 0f, 0F, 1F, 1F, 3F, 0.0F, false);


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

        progressRotationPrev(jaw, riverShellEntity.attackAnim, 0f, (float)Math.toRadians(45), 0f, 1);

        int shellScale = 1 - riverShellEntity.getInShell();
        this.shell_retractable.setScale(shellScale, shellScale, shellScale);

        this.faceTarget(netHeadYaw, headPitch, 0.8f, head_pivot);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

        matrixStackIn.translate(0f, 1.5f ,0f);

        if (this.young) {
            float f = 1.35F;
            head.setScale(f, f, f);
            head.setShouldScaleChildren(true);
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            parts().forEach((part) -> {
                part.render(matrixStackIn, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            });
            matrixStackIn.popPose();
            this.head.setScale(1F, 1F, 1F);
        } else {
            this.head.setScale(1F, 1F, 1F);
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
}
