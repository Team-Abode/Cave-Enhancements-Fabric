package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.accessor.LivingEntityAccess;
import com.exdrill.cave_enhancements.effect.ReversalMobEffect;
import com.exdrill.cave_enhancements.registry.ModEffects;
import com.exdrill.cave_enhancements.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
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
        boolean hasEffect = this.hasEffect(ModEffects.VISCOUS);

        if (hasEffect) {
            cir.setReturnValue(0.21F * this.getBlockJumpFactor());
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
        if (attacker instanceof LivingEntity livingEntity) {
            ReversalMobEffect.onReversal(livingEntity);
        }
        if (amount > 0 && this.hasEffect(ModEffects.REVERSAL)) {
            this.setReversalDamage(Mth.log2(8 + Mth.ceil(amount)));
        }
    }
}
