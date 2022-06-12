package com.exdrill.cave_enhancements.mixin;


import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    private ClientLevel world;

    public WorldRendererMixin(ClientLevel world) {
        this.world = world;
    }

    @Inject(method = "levelEvent", at = @At("TAIL"))
    private void processWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        switch (eventId) {
            case 5190: {
                this.world.addParticle(ModParticles.SHOCKWAVE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
                break;
            }
        }
    }
}
