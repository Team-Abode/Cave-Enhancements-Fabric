package com.exdrill.cave_enhancements.client.render.entity;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.client.render.entity.model.DripstonePikeEntityModel;
import com.exdrill.cave_enhancements.entity.DripstonePikeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DripstonePikeEntityRenderer extends MobRenderer<DripstonePikeEntity, DripstonePikeEntityModel<DripstonePikeEntity>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/dripstone_tortoise/dripstone_pike.png");

    public DripstonePikeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DripstonePikeEntityModel<>(context.bakeLayer(DripstonePikeEntityModel.LAYER_LOCATION)), 0.0f);
    }



    @Override
    public ResourceLocation getTexture(DripstonePikeEntity entity) {
        return TEXTURE;
    }
}
