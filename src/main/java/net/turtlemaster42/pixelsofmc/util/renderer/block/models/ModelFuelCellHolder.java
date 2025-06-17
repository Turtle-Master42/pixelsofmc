package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.block.tile.FuelCellHolderTile;

public class ModelFuelCellHolder extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox cap_up;
    private final AdvancedModelBox notch_up_1;
    private final AdvancedModelBox notch_up_2;
    private final AdvancedModelBox cap_down;
    private final AdvancedModelBox cap_other;

    public ModelFuelCellHolder() {
        texWidth = 32;
        texHeight = 32;
        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        cap_up = new AdvancedModelBox(this, "cap_up");
        cap_up.setTextureOffset(0, 0).addBox(4f, -29f, -12f, 8f, 2f, 8f, 0.0f, false);
        root.addChild(cap_up);

        notch_up_1 = new AdvancedModelBox(this, "notch_up_1");
        notch_up_1.setTextureOffset(0, 10).addBox(5f, -30f, -11f, 2f, 1f, 6f, 0.0f, false);
        root.addChild(notch_up_1);

        notch_up_2 = new AdvancedModelBox(this, "notch_up_2");
        notch_up_2.setTextureOffset(0, 10).addBox(9f, -30f, -11f, 2f, 1f, 6f, 0.0f, false);
        root.addChild(notch_up_2);

        cap_down = new AdvancedModelBox(this, "cap_down");
        cap_down.setTextureOffset(0, 0).addBox(4f, -18f, -12f, 8f, 2f, 8f, 0.0f, false);
        root.addChild(cap_down);

        cap_other = new AdvancedModelBox(this, "cap_other");
        cap_other.setTextureOffset(0, 17).addBox(3.8f, 0f, -11.8f, 8f, 1f, 8f, 0.0f, false);
        cap_other.setScale(1.025f, 1f, 1.025f);
        root.addChild(cap_other);
        this.updateDefaultPose();
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, cap_up, cap_down, cap_other, notch_up_1, notch_up_2);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {}

    public void renderFuelCell(boolean active) {
        this.resetToDefaultPose();
        if (active) {
            root.rotateAngleX += (float) Math.PI;
            root.setPos(0f, -16f, -16f);
        }
    }
}
