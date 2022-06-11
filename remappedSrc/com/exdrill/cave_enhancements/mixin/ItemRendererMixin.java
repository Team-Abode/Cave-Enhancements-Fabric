package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.registry.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModelShaper models;

    @ParametersAreNonnullByDefault
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "HEAD"), index = 8, argsOnly = true)
    private BakedModel renderItem(BakedModel model, ItemStack stack, ItemTransforms.TransformType renderMode) {
        if (stack.is(ModItems.AMETHYST_FLUTE)) {
            if (renderMode == ItemTransforms.TransformType.GUI || renderMode == ItemTransforms.TransformType.GROUND || renderMode == ItemTransforms.TransformType.FIXED) {
                model = this.models.getModelManager().getModel(new ModelResourceLocation("cave_enhancements:amethyst_flute#inventory"));
            } else {
                model = this.models.getModelManager().getModel(new ModelResourceLocation("cave_enhancements:amethyst_flute_in_hand#inventory"));
            }

        }
        return model;


    }
}
