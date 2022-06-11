package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.model.GoopEntityModel;
import com.exdrill.cave_enhancements.entity.GoopEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoopEntityRenderer extends MobRenderer<GoopEntity, GoopEntityModel<GoopEntity>> {
    public GoopEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new GoopEntityModel<>(context.bakeLayer(GoopEntityModel.ENTITY_MODEL_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTexture(GoopEntity entity) {
        return new ResourceLocation(CaveEnhancements.MODID, "textures/entity/goop.png");
    }
}