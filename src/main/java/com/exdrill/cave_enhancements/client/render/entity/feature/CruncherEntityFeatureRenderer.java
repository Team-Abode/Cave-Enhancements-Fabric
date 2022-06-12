package com.exdrill.cave_enhancements.client.render.entity.feature;

import com.exdrill.cave_enhancements.client.render.entity.model.CruncherEntityModel;
import com.exdrill.cave_enhancements.entity.CruncherEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class CruncherEntityFeatureRenderer extends RenderLayer<CruncherEntity, CruncherEntityModel<CruncherEntity>> {

    private final ItemInHandRenderer heldItemRenderer;

    public CruncherEntityFeatureRenderer(RenderLayerParent<CruncherEntity, CruncherEntityModel<CruncherEntity>> featureRendererContext, ItemInHandRenderer heldItemRenderer) {
        super(featureRendererContext);
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, CruncherEntity cruncherEntity, float f, float g, float h, float j, float k, float l) {
        matrixStack.pushPose();

        matrixStack.translate(0, 1.25, 0);

        matrixStack.mulPose(Vector3f.YP.rotation(getParentModel().head.yRot));
        matrixStack.mulPose(Vector3f.XP.rotation(getParentModel().head.xRot));

        matrixStack.translate(0, -.2, -.5);

        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        ItemStack itemStack = cruncherEntity.getItemBySlot(EquipmentSlot.MAINHAND);

        this.heldItemRenderer.renderItem(cruncherEntity, itemStack, TransformType.GROUND, false, matrixStack, vertexConsumerProvider, i);
        matrixStack.popPose();

    }
}