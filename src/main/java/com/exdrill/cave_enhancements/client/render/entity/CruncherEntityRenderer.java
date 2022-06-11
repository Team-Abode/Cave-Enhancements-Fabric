package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.feature.CruncherEntityFeatureRenderer;
import com.exdrill.cave_enhancements.client.render.entity.model.CruncherEntityModel;
import com.exdrill.cave_enhancements.entity.CruncherEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CruncherEntityRenderer extends MobEntityRenderer<CruncherEntity, CruncherEntityModel<CruncherEntity>> {
    public CruncherEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CruncherEntityModel<>(context.getPart(CruncherEntityModel.ENTITY_MODEL_LAYER)), 0.5f);
        this.addFeature(new CruncherEntityFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(CruncherEntity entity) {
        return new Identifier(CaveEnhancements.MODID, "textures/entity/cruncher/cruncher.png");
    }
}