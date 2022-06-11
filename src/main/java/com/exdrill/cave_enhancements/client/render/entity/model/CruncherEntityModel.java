package com.exdrill.cave_enhancements.client.render.entity.model;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.CruncherEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CruncherEntityModel<E extends MobEntity> extends EntityModel<CruncherEntity> {
    public static final EntityModelLayer ENTITY_MODEL_LAYER = new EntityModelLayer(new Identifier(CaveEnhancements.MODID, "cruncher"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    public final ModelPart head;
    public final ModelPart upperJaw;
    private final ModelPart lowerJaw;
    private final ModelPart mossCap;


    public CruncherEntityModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.body = root.getChild("body");
        this.leg0 = root.getChild("leg0");
        this.leg1 = root.getChild("leg1");
        this.leg2 = body.getChild("leg2");
        this.leg3 = body.getChild("leg3");
        this.head = body.getChild("head");
        this.upperJaw = head.getChild("upper_jaw");
        this.lowerJaw = head.getChild("lower_jaw");
        this.mossCap = upperJaw.getChild("moss_cap");

    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData ModelPartData = meshdefinition.getRoot();
        ModelPartData root = ModelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 26).cuboid(-3.0F, -2.5F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
        ModelPartData leg3 = body.addChild("leg3", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.25F, -0.5F, -1.25F));
        ModelPartData leg2 = body.addChild("leg2", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.25F, -0.5F, -1.25F));
        ModelPartData head = body.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 0.0F));
        ModelPartData upperJaw = head.addChild("upper_jaw", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -10.0F, 8.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 4.0F));
        ModelPartData mossCap = upperJaw.addChild("moss_cap", ModelPartBuilder.create().uv(0, 51).cuboid(-4.0F, -4.0F, -10.0F, 8.0F, 3.0F, 10.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lowerJaw = head.addChild("lower_jaw", ModelPartBuilder.create().uv(0, 13).cuboid(-4.0F, -1.0F, -10.0F, 8.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 4.0F));
        ModelPartData leg0 = root.addChild("leg0", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.25F, -2.5F, 3.25F));
        ModelPartData leg1 = root.addChild("leg1", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.25F, -2.5F, 3.25F));

        return TexturedModelData.of(meshdefinition, 64, 64);
    }

    @Override
    public void animateModel(CruncherEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);

    }

    @Override
    public void setAngles(CruncherEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        this.head.yaw = headYaw * 0.017453292F;
        this.leg0.pitch = MathHelper.cos(limbAngle * 1.1F) * 1.4F * limbDistance;
        this.leg1.pitch = MathHelper.cos(limbAngle * 1.1F + 3.1415927F) * 3F * limbDistance;
        this.leg2.pitch = MathHelper.cos(limbAngle * 1.1F + 3.1415927F) * 3F * limbDistance;
        this.leg3.pitch = MathHelper.cos(limbAngle * 1.1F) * 3F * limbDistance;

        if (entity.isEating()) {
            this.head.pitch = -5F;
            this.upperJaw.pitch = -MathHelper.cos(animationProgress * 0.8F) * 0.1F;
            this.lowerJaw.pitch = MathHelper.cos(animationProgress * 0.8F) * 0.1F;
        } else if (!entity.isEating()) {
            this.head.pitch = headPitch * 0.017453292F;
            this.upperJaw.pitch = 0.0F;
            this.lowerJaw.pitch = -0.0F;
        }
        this.mossCap.visible = !entity.hasBeenSheared();

    }
}