package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.item.AmethystFluteItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$SwoopMovementGoal")
public abstract class PhantomEntityMixin extends Goal {
    @Final
    PhantomEntity field_7333;

    @ParametersAreNonnullByDefault
    @Inject(method = "shouldContinue", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"), cancellable = true)
    private void shouldContinue(CallbackInfoReturnable<Boolean> cir) {
        this.field_7333.world.getOtherEntities(this.field_7333, this.field_7333.getBoundingBox().expand(16.0D), this.field_7333::canSee).forEach(entity -> {
            if (entity instanceof LivingEntity livingEntity && AmethystFluteItem.isScary(livingEntity)) {
                cir.setReturnValue(false);
            }
        });
    }
}
