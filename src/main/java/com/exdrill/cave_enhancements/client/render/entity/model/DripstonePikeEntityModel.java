package com.exdrill.cave_enhancements.client.render.entity.model;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.animation.DripstoneTortoiseAnimations;
import com.exdrill.cave_enhancements.entity.DripstonePikeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;

public class DripstonePikeEntityModel<T extends DripstonePikeEntity> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(CaveEnhancements.MODID, "dripstone_pike"), "main");
	private final ModelPart root;
	private final ModelPart pike;

	public DripstonePikeEntityModel(ModelPart root) {
		this.root = root.getChild("root");
		this.pike = this.root.getChild("pike");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();

		ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData pike = root.addChild("pike", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = pike.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, 0.0F, 16.0F, 48.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r2 = pike.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, 0.0F, 16.0F, 48.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

		return TexturedModelData.of(meshdefinition, 32, 48);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.risingAnimationState, DripstoneTortoiseAnimations.RISING, animationProgress);
	}
}