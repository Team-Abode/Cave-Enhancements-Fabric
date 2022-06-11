package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.HarmonicArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class JingleArrowEntityRenderer extends ProjectileEntityRenderer<HarmonicArrowEntity> {
    public static final Identifier TEXTURE = new Identifier(CaveEnhancements.MODID, "textures/entity/harmonic_arrow.png");

    public JingleArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(HarmonicArrowEntity entity) {
        return TEXTURE;
    }
}
