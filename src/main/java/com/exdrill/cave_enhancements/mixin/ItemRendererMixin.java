package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.registry.ModItems;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @ParametersAreNonnullByDefault
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "HEAD"), index = 8, argsOnly = true)
    private BakedModel renderItem(BakedModel model, ItemStack stack, ModelTransformation.Mode renderMode) {
        if (stack.isOf(ModItems.AMETHYST_FLUTE)) {
            if (renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED) {
                model = this.models.getModelManager().getModel(new ModelIdentifier("cave_enhancements:amethyst_flute#inventory"));
            } else {
                model = this.models.getModelManager().getModel(new ModelIdentifier("cave_enhancements:amethyst_flute_in_hand#inventory"));
            }

        }
        return model;


    }
}
