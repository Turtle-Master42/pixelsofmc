package net.turtlemaster42.pixelsofmc.util.renderer;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;

public class POMAdvancedModelBox extends AdvancedModelBox {
    public POMAdvancedModelBox(AdvancedEntityModel model, String name) {
        super(model, name);
    }

    public BasicModelPart addCitadelBox(String Name, float posX, float posY, float posZ, int width, int height, int depth, float scale, int textureOffsetX, int textureOffsetY) {
        return super.addBox(Name, posX, posY, posZ, width, height, depth, scale, textureOffsetX, textureOffsetY);
    }

    public BasicModelPart addCitadelBox(float posX, float posY, float posZ, float width, float height, float depth) {
        return super.addBox(posX, posY, posZ, width, height, depth);
    }

    public BasicModelPart addCitadelBox(float posX, float posY, float posZ, float width, float height, float depth, boolean mirror) {
        return super.addBox(posX, posY, posZ, width, height, depth, mirror);
    }

    public void addCitadelBox(float posX, float posY, float posZ, float width, float height, float depth, float scale) {
        super.addBox(posX, posY, posZ, width, height, depth, scale);
    }

    public void addCitadelBox(float posX, float posY, float posZ, float width, float height, float depth, float scaleX, float scaleY, float scaleZ) {
        super.addBox(posX, posY, posZ, width, height, depth, scaleX, scaleY, scaleZ);
    }

    public void addCitadelBox(float posX, float posY, float posZ, float width, float height, float depth, float scale, boolean mirror) {
        super.addBox(posX, posY, posZ, width, height, depth, scale, mirror);
    }

    public BasicModelPart addPOMBox(String Name, float posX, float posY, float posZ, int width, int height, int depth, float scale, int textureOffsetX, int textureOffsetY) {
        return super.addBox(Name, -posX, -posY, -posZ, width, height, depth, scale, textureOffsetX, textureOffsetY);
    }

    public BasicModelPart addPOMBox(float posX, float posY, float posZ, float width, float height, float depth) {
        return super.addBox(-posX, -posY, -posZ, width, height, depth);
    }

    public BasicModelPart addPOMBox(float posX, float posY, float posZ, float width, float height, float depth, boolean mirror) {
        return super.addBox(-posX, -posY, -posZ, width, height, depth, mirror);
    }

    public void addPOMBox(float posX, float posY, float posZ, float width, float height, float depth, float scale) {
        super.addBox(-posX, -posY, -posZ, width, height, depth, scale);
    }

    public void addPOMBox(float posX, float posY, float posZ, float width, float height, float depth, float scaleX, float scaleY, float scaleZ) {
        super.addBox(-posX, -posY, -posZ, width, height, depth, scaleX, scaleY, scaleZ);
    }

    public void addPOMBox(float posX, float posY, float posZ, float width, float height, float depth, float scale, boolean mirror) {
        super.addBox(-posX, -posY, -posZ, width, height, depth, scale, mirror);
    }

    public POMAdvancedModelBox setPOMTextureOffset(int textureOffsetX, int textureOffsetY) {
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        return this;
    }

    public void setPOMPos(float xIn, float yIn, float zIn) {
        super.setPos(-xIn, -yIn, -zIn);
    }
}
