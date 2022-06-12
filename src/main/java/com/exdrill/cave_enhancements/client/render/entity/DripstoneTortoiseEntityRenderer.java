package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.model.DripstoneTortoiseEntityModel;
import com.exdrill.cave_enhancements.entity.DripstoneTortoiseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DripstoneTortoiseEntityRenderer extends MobRenderer<DripstoneTortoiseEntity, DripstoneTortoiseEntityModel<DripstoneTortoiseEntity>> {
    public DripstoneTortoiseEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DripstoneTortoiseEntityModel<>(context.bakeLayer(DripstoneTortoiseEntityModel.LAYER_LOCATION)), 0.5f);
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/dripstone_tortoise/dripstone_tortoise.png");

    @Override
    public ResourceLocation getTextureLocation(DripstoneTortoiseEntity entity) {
        return TEXTURE;
    }
}
