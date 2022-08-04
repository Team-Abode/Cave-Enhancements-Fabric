package com.exdrill.cave_enhancements.client.renderer.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.renderer.entity.layers.CruncherHeldItemLayer;
import com.exdrill.cave_enhancements.client.model.CruncherModel;
import com.exdrill.cave_enhancements.entity.cruncher.Cruncher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CruncherRenderer extends MobRenderer<Cruncher, CruncherModel<Cruncher>> {
    public CruncherRenderer(EntityRendererProvider.Context context) {
        super(context, new CruncherModel<>(context.bakeLayer(CruncherModel.ENTITY_MODEL_LAYER)), 0.5f);
        this.addLayer(new CruncherHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Cruncher entity) {
        return new ResourceLocation(CaveEnhancements.MODID, "textures/entity/cruncher/cruncher.png");
    }
}