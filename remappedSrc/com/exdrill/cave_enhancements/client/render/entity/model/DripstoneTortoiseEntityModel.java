package com.exdrill.cave_enhancements.client.render.entity.model;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.animation.DripstoneTortoiseAnimations;
import com.exdrill.cave_enhancements.entity.DripstoneTortoiseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DripstoneTortoiseEntityModel<T extends DripstoneTortoiseEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "dripstone_tortoise"), "main");
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

	public static LayerDefinition texturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition shell = root.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition body = shell.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0F, -6.0F, -7.0F, 18.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(-13.5F, 0.0F, -7.0F, 11.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(40, 26).addBox(-3.0F, -2.0F, -8.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -2.0F, -7.0F));

		PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 10).addBox(-3.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, 0.0F, 7.0F));

		PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, 0.0F, -4.0F));

		PartDefinition point0 = body.addOrReplaceChild("point0", CubeListBuilder.create(), PartPose.offset(-12.0F, -6.0F, 8.0F));

		PartDefinition point0_r1 = point0.addOrReplaceChild("point0_r1", CubeListBuilder.create().texOffs(0, 58).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition point0_r2 = point0.addOrReplaceChild("point0_r2", CubeListBuilder.create().texOffs(0, 58).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		PartDefinition point1 = body.addOrReplaceChild("point1", CubeListBuilder.create(), PartPose.offset(-3.0F, -6.0F, 3.0F));

		PartDefinition point1_r1 = point1.addOrReplaceChild("point1_r1", CubeListBuilder.create().texOffs(0, 58).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition point1_r2 = point1.addOrReplaceChild("point1_r2", CubeListBuilder.create().texOffs(0, 58).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		PartDefinition point2 = body.addOrReplaceChild("point2", CubeListBuilder.create(), PartPose.offset(-12.0F, -6.0F, -2.0F));

		PartDefinition point2_r1 = point2.addOrReplaceChild("point2_r1", CubeListBuilder.create().texOffs(0, 58).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition point2_r2 = point2.addOrReplaceChild("point2_r2", CubeListBuilder.create().texOffs(0, 58).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		PartDefinition leg3 = shell.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 47).addBox(-0.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, -4.0F));

		PartDefinition leg1 = shell.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 26).addBox(-0.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, 7.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = headYaw * 0.017453292F;
		this.leg0.xRot = Mth.cos(limbAngle * 1.1F) * 1.4F * limbDistance;
		this.leg1.xRot = Mth.cos(limbAngle * 1.1F + 3.1415927F) * 3F * limbDistance;
		this.leg2.xRot = Mth.cos(limbAngle * 1.1F + 3.1415927F) * 3F * limbDistance;
		this.leg3.xRot = Mth.cos(limbAngle * 1.1F) * 3F * limbDistance;
		this.animate(entity.stompingAnimationState, DripstoneTortoiseAnimations.STOMPING, animationProgress);
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}