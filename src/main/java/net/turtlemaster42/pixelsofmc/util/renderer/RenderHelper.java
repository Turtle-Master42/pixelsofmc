//credits Botania

package net.turtlemaster42.pixelsofmc.util.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.*;
import net.minecraft.util.Mth;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.mixin.client.AccessorRenderType;
import org.joml.Matrix4f;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class RenderHelper extends RenderType {

    private static final RenderType STAR;

    public RenderHelper(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    private static RenderType makeLayer(String name, VertexFormat format, VertexFormat.Mode mode,
                                        int bufSize, boolean hasCrumbling, boolean sortOnUpload, CompositeState glState) {
        return AccessorRenderType.create(name, format, mode, bufSize, hasCrumbling, sortOnUpload, glState);
    }


    static {
        RenderType.CompositeState glState = RenderType.CompositeState.builder()
                .setShaderState(POSITION_COLOR_SHADER)
                .setWriteMaskState(COLOR_WRITE)
                .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
                .createCompositeState(false);
        STAR = makeLayer(PixelsOfMc.MOD_ID + "star", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, 256, false, false, glState);

    }

    public static void renderStar(PoseStack ms, MultiBufferSource buffers, int color, float xScale, float yScale, float zScale, long seed) {
        VertexConsumer buffer = buffers.getBuffer(STAR);

        float ticks = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
        float semiPeriodTicks = 200;
        float f1 = Mth.abs(Mth.sin((float) Math.PI / semiPeriodTicks * ticks))
                * 0.9F + 0.1F; // shift to [0.1, 1.0]

        float f2 = f1 > 0.F ? (f1 - 0.7F) / 0.2F : 0;
        Random random = new Random(seed);

        ms.pushPose();
        ms.scale(xScale, yScale, zScale);

        for (int i = 0; i < (f1 + f1 * f1) / 2F * 90F + 30F; i++) {
            ms.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360F));
            ms.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360F));
            ms.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360F));
            ms.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360F));
            ms.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360F));
            ms.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360F + f1 * 90F));
            float f3 = random.nextFloat() * 20F + 5F + f2 * 10F;
            float f4 = random.nextFloat() * 2F + 1F + f2 * 2F;
            float r = ((color & 0xFF0000) >> 16) / 255F;
            float g = ((color & 0xFF00) >> 8) / 255F;
            float b = (color & 0xFF) / 255F;
            Matrix4f mat = ms.last().pose();
            Runnable center = () -> buffer.vertex(mat, 0, 0, 0).color(r, g, b, f1).endVertex();
            Runnable[] vertices = {
                    () -> buffer.vertex(mat, -0.866F * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex(),
                    () -> buffer.vertex(mat, 0.866F * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex(),
                    () -> buffer.vertex(mat, 0, f3, 1F * f4).color(0, 0, 0, 0).endVertex(),
                    () -> buffer.vertex(mat, -0.866F * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex()
            };
            triangleFan(center, vertices);
        }

        ms.popPose();
    }

    public static void triangleFan(Runnable center, Runnable... vertices) {
        triangleFan(center, Arrays.asList(vertices));
    }

    /**
     * With a buffer in GL_TRIANGLES mode, emulates GL_TRIANGLE_FAN on the CPU.
     * This is because batching of GL_TRIANGLE_FAN makes no sense (the vertices would bleed into one massive fan)
     */
    public static void triangleFan(Runnable center, List<Runnable> vertices) {
        for (int i = 0; i < vertices.size() - 1; i++) {
            center.run();
            vertices.get(i).run();
            vertices.get(i + 1).run();
        }
    }
}
