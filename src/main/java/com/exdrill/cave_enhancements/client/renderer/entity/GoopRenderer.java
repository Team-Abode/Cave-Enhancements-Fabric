package com.exdrill.cave_enhancements.client.renderer.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.model.GoopModel;
import com.exdrill.cave_enhancements.entity.goop.Goop;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoopRenderer extends MobRenderer<Goop, GoopModel<Goop>> {
    public GoopRenderer(EntityRendererProvider.Context context) {
        super(context, new GoopModel<>(context.bakeLayer(GoopModel.ENTITY_MODEL_LAYER)), 0.5f);
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/goop.png");

    @Override
    public ResourceLocation getTextureLocation(Goop entity) {
        return TEXTURE;
    }
}