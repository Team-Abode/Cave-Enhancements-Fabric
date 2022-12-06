package com.teamabode.cave_enhancements.core.mixin;

import com.teamabode.cave_enhancements.common.item.AmethystFluteItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Phantom;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomSweepAttackGoal")
public abstract class PhantomMixin extends Goal {
    @Final
    Phantom field_7333;

    @ParametersAreNonnullByDefault
    @Inject(method = "canContinueToUse", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"), cancellable = true)
    private void shouldContinue(CallbackInfoReturnable<Boolean> cir) {

        Phantom phantom = this.field_7333;

        phantom.level.getEntities(phantom, phantom.getBoundingBox().inflate(16.0D), phantom::hasLineOfSight).forEach(entity -> {
            if (entity instanceof LivingEntity livingEntity && AmethystFluteItem.isScary(livingEntity)) {
                cir.setReturnValue(false);
            }
        });
    }
}
