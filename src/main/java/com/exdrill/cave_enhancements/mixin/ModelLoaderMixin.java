package com.exdrill.cave_enhancements.mixin;


import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ModelBakery.class)
public abstract class ModelLoaderMixin {

    /*
    @Shadow
    protected abstract void addModel(ModelResourceLocation modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.BEFORE))
    private void inject(ResourceManager resourceManager, BlockColors blockColors, ProfilerFiller profiler, int i, CallbackInfo ci) {
        this.addModel(new ModelResourceLocation("cave_enhancements:amethyst_flute_in_hand#inventory"));
    }

     */
}
