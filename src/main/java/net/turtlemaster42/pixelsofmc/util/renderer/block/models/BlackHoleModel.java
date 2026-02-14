package net.turtlemaster42.pixelsofmc.util.renderer.block.models;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;
import net.turtlemaster42.pixelsofmc.tile.StarTile;

public class BlackHoleModel extends AdvancedEntityModel<Entity> {
    private final AdvancedModelBox root;
    private final AdvancedModelBox horizon;
    private final AdvancedModelBox plasma_1;
    private final AdvancedModelBox plasma_2;
    private final AdvancedModelBox plasma_3;
    private final AdvancedModelBox plasma_4;

    public BlackHoleModel() {
        texWidth = 192;
        texHeight = 208;

        root = new AdvancedModelBox(this, "root");
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        horizon = new AdvancedModelBox(this, "horizon");
        horizon.setRotationPoint(0F, 0F, 0F);
        root.addChild(horizon);
        horizon.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        horizon.setScale(0, 0, 0);

        plasma_1 = new AdvancedModelBox(this, "plasma_1");
        plasma_1.setRotationPoint(0F, 0F, 0F);
        root.addChild(plasma_1);
        plasma_1.setTextureOffset(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        plasma_1.setScale(0, 0, 0);

        plasma_2 = new AdvancedModelBox(this, "plasma_2");
        plasma_2.setRotationPoint(0F, 0F, 0F);
        root.addChild(plasma_2);
        plasma_2.setTextureOffset(0, 64).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        plasma_2.setScale(0, 0, 0);

        plasma_3 = new AdvancedModelBox(this, "plasma_3");
        plasma_3.setRotationPoint(0F, 0F, 0F);
        root.addChild(plasma_3);
        plasma_3.setTextureOffset(0, 96).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        plasma_3.setScale(0, 0, 0);

        plasma_4 = new AdvancedModelBox(this, "plasma_4");
        plasma_4.setRotationPoint(0F, 0F, 0F);
        root.addChild(plasma_4);
        plasma_4.setTextureOffset(0, 128).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        plasma_4.setScale(0, 0, 0);


        //box(locX, locY, locZ, sizeX, sizeY, sizeZ, ?, flip?)
        this.updateDefaultPose();
    }


    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, horizon, plasma_1, plasma_2, plasma_3, plasma_4);
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(root);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {
    }

    public void renderBlackHole(StarTile tile, float partialTick) {
        this.resetToDefaultPose();

        float rotation = (tile.ticksExisted + partialTick + (tile.getBlockPos().getX()+tile.getBlockPos().getY()+tile.getBlockPos().getZ())) / 3;
        horizon.rotateAngleY = rotation * -0.91f;
        horizon.rotateAngleZ = rotation * -1.13f;
        horizon.setScale(0.6f, 0.6f, 0.6f);

        plasma_1.rotateAngleY = rotation * -0.91f;
        plasma_1.rotateAngleZ = rotation * -1.13f;
        plasma_1.setScale(-0.62f, -0.62f, -0.62f);

        plasma_2.rotateAngleY = rotation * -0.91f;
        plasma_2.rotateAngleZ = rotation * -1.13f;
        plasma_2.setScale(-0.65f, -0.65f, -0.65f);

        plasma_3.rotateAngleY = rotation * -0.91f;
        plasma_3.rotateAngleZ = rotation * -1.13f;
        plasma_3.setScale(-0.7f, -0.7f, -0.7f);

        plasma_4.rotateAngleY = rotation * -0.91f;
        plasma_4.rotateAngleZ = rotation * -1.13f;
        plasma_4.setScale(-0.8f, -0.8f, -0.8f);

    }
}
