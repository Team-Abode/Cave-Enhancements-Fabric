package com.exdrill.cave_enhancements.client.render.entity.model;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.GoopEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class GoopEntityModel<E extends MobEntity> extends EntityModel<GoopEntity> {
	public static final EntityModelLayer ENTITY_MODEL_LAYER = new EntityModelLayer(new Identifier(CaveEnhancements.MODID, "goop"), "main");
	private final ModelPart goop;

	public GoopEntityModel(ModelPart root) {
		this.goop = root.getChild("goop");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();

		ModelPartData goop = partdefinition.addChild("goop", ModelPartBuilder.create().uv(0, 20).cuboid(-6.5F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(26, 30).cuboid(1.5F, 3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-10.5F, 8.0F, -10.0F, 20.0F, 0.0F, 20.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		return TexturedModelData.of(meshdefinition, 128, 128);
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.goop.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(GoopEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if (entity.isStickingUp()) {
			this.goop.pitch = 3.1415927F;
			this.goop.pivotY = 17.0F;
		} else if (!entity.isStickingUp()) {
			this.goop.pitch = 0.0F;
			this.goop.pivotY = 16.0F;
		}
	}
}