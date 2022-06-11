package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.model.DripstoneTortoiseEntityModel;
import com.exdrill.cave_enhancements.entity.DripstoneTortoiseEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DripstoneTortoiseEntityRenderer extends MobEntityRenderer<DripstoneTortoiseEntity, DripstoneTortoiseEntityModel<DripstoneTortoiseEntity>> {
    public DripstoneTortoiseEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DripstoneTortoiseEntityModel<>(context.getPart(DripstoneTortoiseEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(DripstoneTortoiseEntity entity) {
        return new Identifier(CaveEnhancements.MODID, "textures/entity/dripstone_tortoise/dripstone_tortoise.png");
    }
}
