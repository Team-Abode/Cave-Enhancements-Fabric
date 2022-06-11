package com.exdrill.cave_enhancements.client.render.entity.feature;

import com.exdrill.cave_enhancements.client.render.entity.model.CruncherEntityModel;
import com.exdrill.cave_enhancements.entity.CruncherEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CruncherEntityFeatureRenderer extends FeatureRenderer<CruncherEntity, CruncherEntityModel<CruncherEntity>> {

    private final HeldItemRenderer heldItemRenderer;

    public CruncherEntityFeatureRenderer(FeatureRendererContext<CruncherEntity, CruncherEntityModel<CruncherEntity>> featureRendererContext, HeldItemRenderer heldItemRenderer) {
        super(featureRendererContext);
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CruncherEntity cruncherEntity, float f, float g, float h, float j, float k, float l) {
        matrixStack.push();

        matrixStack.translate(0, 1.25, 0);

        matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(getContextModel().head.yaw));
        matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(getContextModel().head.pitch));

        matrixStack.translate(0, -.2, -.5);

        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));

        ItemStack itemStack = cruncherEntity.getEquippedStack(EquipmentSlot.MAINHAND);

        this.heldItemRenderer.renderItem(cruncherEntity, itemStack, Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();

    }
}