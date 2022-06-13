package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.item.AmethystFluteItem;
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
        this.field_7333.level.getEntities(this.field_7333, this.field_7333.getBoundingBox().inflate(16.0D), this.field_7333::hasLineOfSight).forEach(entity -> {
            if (entity instanceof LivingEntity livingEntity && AmethystFluteItem.isScary(livingEntity)) {
                cir.setReturnValue(false);
            }
        });
    }
}
