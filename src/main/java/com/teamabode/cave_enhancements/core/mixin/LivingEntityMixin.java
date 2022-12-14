package com.teamabode.cave_enhancements.core.mixin;

import com.teamabode.cave_enhancements.common.accessor.LivingEntityAccess;
import com.teamabode.cave_enhancements.core.registry.ModEffects;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {

    private static final EntityDataAccessor<Integer> REVERSAL_DAMAGE = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract boolean hasEffect(MobEffect effect);

    @Shadow public abstract boolean hurt(DamageSource source, float amount);

    @Inject(method = "getJumpPower", at = @At("HEAD"), cancellable = true)
    private void getJumpPower(CallbackInfoReturnable<Float> cir) {
        boolean hasEffect = this.hasEffect(ModEffects.STICKY);
        float jumpPower = 0.42F * this.getBlockJumpFactor();
        if (hasEffect) {
            cir.setReturnValue(jumpPower / 2);
        }
    }

    @Inject(method = "defineSynchedData", at = @At("HEAD"))
    protected void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(REVERSAL_DAMAGE, 0);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("ReversalDamage", getReversalDamage());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.setReversalDamage(compound.getInt("ReversalDamage"));
    }

    public void setReversalDamage(int damage) {
        this.entityData.set(REVERSAL_DAMAGE, damage);
    }

    public int getReversalDamage() {
        return this.entityData.get(REVERSAL_DAMAGE);
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getDirectEntity();

        if (attacker instanceof LivingEntity livingAttacker &&
        livingAttacker.hasEffect(ModEffects.REVERSAL) &&
        ((LivingEntityAccess) livingAttacker).getReversalDamage() > 0)
        {
            livingAttacker.level.playSound(null, livingAttacker.blockPosition(), ModSounds.EFFECT_REVERSAL_REVERSE, SoundSource.PLAYERS, 1.0F, 1.0F);
            ((LivingEntityAccess) livingAttacker).setReversalDamage(0);
        }
        if (amount > 0 && this.hasEffect(ModEffects.REVERSAL)) {
            if (this.random.nextFloat() < (amount / 5) ) {
                ((LivingEntityAccess) this).setReversalDamage(Math.min(((LivingEntityAccess) this).getReversalDamage() + 1, 5));
            }
        }
    }
}
