package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.feature.CruncherEntityFeatureRenderer;
import com.exdrill.cave_enhancements.client.render.entity.model.CruncherEntityModel;
import com.exdrill.cave_enhancements.entity.CruncherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CruncherEntityRenderer extends MobRenderer<CruncherEntity, CruncherEntityModel<CruncherEntity>> {
    public CruncherEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new CruncherEntityModel<>(context.bakeLayer(CruncherEntityModel.ENTITY_MODEL_LAYER)), 0.5f);
        this.addLayer(new CruncherEntityFeatureRenderer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTexture(CruncherEntity entity) {
        return new ResourceLocation(CaveEnhancements.MODID, "textures/entity/cruncher/cruncher.png");
    }
}