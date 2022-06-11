package com.exdrill.cave_enhancements.mixin;


import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private ClientWorld world;

    @Inject(method = "processWorldEvent", at = @At("TAIL"))
    private void processWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        switch (eventId) {
            case 5190: {
                this.world.addParticle(ModParticles.SHOCKWAVE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
                break;
            }
        }
    }
}
