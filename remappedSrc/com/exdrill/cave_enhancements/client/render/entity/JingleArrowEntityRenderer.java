package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.HarmonicArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class JingleArrowEntityRenderer extends ArrowRenderer<HarmonicArrowEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/harmonic_arrow.png");

    public JingleArrowEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTexture(HarmonicArrowEntity entity) {
        return TEXTURE;
    }
}
