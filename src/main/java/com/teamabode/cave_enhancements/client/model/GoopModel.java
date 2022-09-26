package com.teamabode.cave_enhancements.client.model;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.goop.Goop;
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
import net.minecraft.world.entity.Mob;

public class GoopModel<E extends Mob> extends EntityModel<Goop> {
	public static final ModelLayerLocation ENTITY_MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "goop"), "main");
	private final ModelPart goop;

	public GoopModel(ModelPart root) {
		this.goop = root.getChild("goop");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition goop = partdefinition.addOrReplaceChild("goop", CubeListBuilder.create().texOffs(0, 20).addBox(-6.5F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(26, 30).addBox(1.5F, 3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-10.5F, 8.0F, -10.0F, 20.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.goop.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(Goop entity, float f, float g, float h, float i, float j) {
		if (entity.isStickingUp()) {
			this.goop.xRot = 3.1415927F;
			this.goop.y = 17.0F;
		} else if (!entity.isStickingUp()) {
			this.goop.xRot = 0.0F;
			this.goop.y = 16.0F;
		}
	}
}