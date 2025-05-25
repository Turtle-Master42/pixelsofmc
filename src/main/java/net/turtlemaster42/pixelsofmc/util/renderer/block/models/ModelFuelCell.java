package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.block.tile.FuelCellHolderTile;

public class ModelFuelCell extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox fuel;


    public ModelFuelCell() {
        texWidth = 32;
        texHeight = 18;
        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        fuel = new AdvancedModelBox(this, "fuel");
        fuel.setTextureOffset(0, 0).addBox(4f, -28F, -12F, 8f, 10f, 8f, 0.0f, false);
        root.addChild(fuel);

        this.updateDefaultPose();
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, fuel);
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
