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
    private final AdvancedModelBox cap_up_plate;
    private final AdvancedModelBox notch_up_1;
    private final AdvancedModelBox notch_up_2;
    private final AdvancedModelBox cap_down;
    private final AdvancedModelBox cap_other;

    public ModelFuelCellHolder() {
        texWidth = 64;
        texHeight = 64;
        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        cap_up = new AdvancedModelBox(this, "cap_up");
        cap_up.setTextureOffset(0, 0).addBox(3f, -30f, -13f, 10f, 2f, 10f, 0.0f, false);
        root.addChild(cap_up);

        cap_up_plate = new AdvancedModelBox(this, "cap_up_plate");
        cap_up_plate.setTextureOffset(0, 29).addBox(4f, -31f, -12f, 8f, 1f, 8f, 0.0f, false);
        root.addChild(cap_up_plate);

        notch_up_1 = new AdvancedModelBox(this, "notch_up_1");
        notch_up_1.setTextureOffset(0, 13).addBox(5f, -32f, -11f, 2f, 1f, 6f, 0.0f, false);
        root.addChild(notch_up_1);

        notch_up_2 = new AdvancedModelBox(this, "notch_up_2");
        notch_up_2.setTextureOffset(0, 21).addBox(9f, -32f, -11f, 2f, 1f, 6f, 0.0f, false);
        root.addChild(notch_up_2);

        cap_down = new AdvancedModelBox(this, "cap_down");
        cap_down.setTextureOffset(0, 0).addBox(3f, -18f, -13f, 10f, 2f, 10f, 0.0f, false);
        root.addChild(cap_down);

        cap_other = new AdvancedModelBox(this, "cap_other");
        cap_other.setTextureOffset(0, 0).addBox(3f, 0f, -13f, 10f, 2f, 10f, 0.0f, false);
        root.addChild(cap_other);

        this.updateDefaultPose();
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, cap_up, cap_up_plate, cap_down, cap_other, notch_up_1, notch_up_2);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {
    }

    public void renderFuelCell(FuelCellHolderTile tile, float partialTick, boolean active) {
        this.resetToDefaultPose();
        if (active) {
            root.rotateAngleX += (float) Math.PI;
            root.setPos(0f, -16f, -16f);
        }
    }
}
