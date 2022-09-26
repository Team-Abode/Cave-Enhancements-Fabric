package com.teamabode.cave_enhancements.client.renderer.entity;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.model.DripstonePikeModel;
import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstonePike;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DripstonePikeRenderer extends MobRenderer<DripstonePike, DripstonePikeModel<DripstonePike>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/dripstone_tortoise/dripstone_pike.png");

    public DripstonePikeRenderer(EntityRendererProvider.Context context) {
        super(context, new DripstonePikeModel<>(context.bakeLayer(DripstonePikeModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(DripstonePike entity) {
        return TEXTURE;
    }
}
