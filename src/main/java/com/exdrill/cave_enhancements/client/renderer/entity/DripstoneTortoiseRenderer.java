package com.exdrill.cave_enhancements.client.renderer.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.model.DripstoneTortoiseModel;
import com.exdrill.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DripstoneTortoiseRenderer extends MobRenderer<DripstoneTortoise, DripstoneTortoiseModel<DripstoneTortoise>> {
    public DripstoneTortoiseRenderer(EntityRendererProvider.Context context) {
        super(context, new DripstoneTortoiseModel<>(context.bakeLayer(DripstoneTortoiseModel.LAYER_LOCATION)), 0.5f);
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/dripstone_tortoise/dripstone_tortoise.png");

    @Override
    public ResourceLocation getTextureLocation(DripstoneTortoise entity) {
        return TEXTURE;
    }
}
