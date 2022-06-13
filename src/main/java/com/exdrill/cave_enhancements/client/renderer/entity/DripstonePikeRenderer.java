package com.exdrill.cave_enhancements.client.renderer.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.model.DripstonePikeEntityModel;
import com.exdrill.cave_enhancements.entity.DripstonePike;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DripstonePikeRenderer extends MobRenderer<DripstonePike, DripstonePikeEntityModel<DripstonePike>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/dripstone_tortoise/dripstone_pike.png");

    public DripstonePikeRenderer(EntityRendererProvider.Context context) {
        super(context, new DripstonePikeEntityModel<>(context.bakeLayer(DripstonePikeEntityModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(DripstonePike entity) {
        return TEXTURE;
    }
}
