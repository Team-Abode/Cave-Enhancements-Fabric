package com.exdrill.cave_enhancements.client.render.entity.model;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.animation.DripstoneTortoiseAnimations;
import com.exdrill.cave_enhancements.entity.DripstoneTortoiseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DripstoneTortoiseEntityModel<T extends DripstoneTortoiseEntity> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(CaveEnhancements.MODID, "dripstone_tortoise"), "main");
	private final ModelPart root;
	private final ModelPart shell;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leg0;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;

	public DripstoneTortoiseEntityModel(ModelPart root) {
		this.root = root.getChild("root");
		this.shell = this.root.getChild("shell");
		this.body = this.shell.getChild("body");
		this.head = this.body.getChild("head");
		this.leg0 = this.body.getChild("leg0");
		this.leg1 = this.shell.getChild("leg1");
		this.leg2 = this.body.getChild("leg2");
		this.leg3 = this.shell.getChild("leg3");
	}

	public static TexturedModelData texturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();

		ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		ModelPartData shell = root.addChild("shell", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -6.0F, 0.0F));

		ModelPartData body = shell.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-17.0F, -6.0F, -7.0F, 18.0F, 6.0F, 20.0F, new Dilation(0.0F))
		.uv(0, 26).cuboid(-13.5F, 0.0F, -7.0F, 11.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(40, 26).cuboid(-3.0F, -2.0F, -8.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, -2.0F, -7.0F));

		ModelPartData leg0 = body.addChild("leg0", ModelPartBuilder.create().uv(0, 10).cuboid(-3.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-14.0F, 0.0F, 7.0F));

		ModelPartData leg2 = body.addChild("leg2", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-14.0F, 0.0F, -4.0F));

		ModelPartData point0 = body.addChild("point0", ModelPartBuilder.create(), ModelTransform.pivot(-12.0F, -6.0F, 8.0F));

		ModelPartData point0_r1 = point0.addChild("point0_r1", ModelPartBuilder.create().uv(0, 58).cuboid(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData point0_r2 = point0.addChild("point0_r2", ModelPartBuilder.create().uv(0, 58).cuboid(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		ModelPartData point1 = body.addChild("point1", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, -6.0F, 3.0F));

		ModelPartData point1_r1 = point1.addChild("point1_r1", ModelPartBuilder.create().uv(0, 58).cuboid(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData point1_r2 = point1.addChild("point1_r2", ModelPartBuilder.create().uv(0, 58).cuboid(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		ModelPartData point2 = body.addChild("point2", ModelPartBuilder.create(), ModelTransform.pivot(-12.0F, -6.0F, -2.0F));

		ModelPartData point2_r1 = point2.addChild("point2_r1", ModelPartBuilder.create().uv(0, 58).cuboid(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData point2_r2 = point2.addChild("point2_r2", ModelPartBuilder.create().uv(0, 58).cuboid(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		ModelPartData leg3 = shell.addChild("leg3", ModelPartBuilder.create().uv(0, 47).cuboid(-0.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, 0.0F, -4.0F));

		ModelPartData leg1 = shell.addChild("leg1", ModelPartBuilder.create().uv(0, 26).cuboid(-0.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, 0.0F, 7.0F));

		return TexturedModelData.of(meshdefinition, 128, 128);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.head.yaw = headYaw * 0.017453292F;
		this.leg0.pitch = MathHelper.cos(limbAngle * 1.1F) * 1.4F * limbDistance;
		this.leg1.pitch = MathHelper.cos(limbAngle * 1.1F + 3.1415927F) * 3F * limbDistance;
		this.leg2.pitch = MathHelper.cos(limbAngle * 1.1F + 3.1415927F) * 3F * limbDistance;
		this.leg3.pitch = MathHelper.cos(limbAngle * 1.1F) * 3F * limbDistance;
		this.updateAnimation(entity.stompingAnimationState, DripstoneTortoiseAnimations.STOMPING, animationProgress);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}