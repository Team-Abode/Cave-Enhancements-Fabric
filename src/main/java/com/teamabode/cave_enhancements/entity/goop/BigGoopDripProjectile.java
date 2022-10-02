package com.teamabode.cave_enhancements.entity.goop;

import com.teamabode.cave_enhancements.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BigGoopDripProjectile extends ThrowableItemProjectile {
    public BigGoopDripProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public BigGoopDripProjectile(Level world, LivingEntity owner) {
        super(ModEntities.BIG_GOOP_DRIP, owner, world); // null will be changed later
    }

    public BigGoopDripProjectile(Level world, double x, double y, double z) {
        super(ModEntities.BIG_GOOP_DRIP, x, y, z, world); // null will be changed later
    }

    //Item projectile is rendered as
    @Override
    protected Item getDefaultItem() {
        return ModItems.BIG_GOOP_DRIP;
    }

    //On hit particles
    @Environment(EnvType.CLIENT)
    private ParticleOptions getParticleParameters() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ModItems.BIG_GOOP_DRIP, 1));
    }

    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            ParticleOptions particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public boolean hitEntity = false;

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(ModDamageSource.GOOP_DRIP, 5.0F);
            livingEntity.addEffect(new MobEffectInstance(ModEffects.STICKY, 100, 1, true, false));
        }
        hitEntity = true;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            if (!hitEntity) {

                BlockPos pos = new BlockPos(hitResult.getLocation());

                if (level.getBlockState(pos).is(Blocks.AIR)) {
                    level.setBlockAndUpdate(pos, ModBlocks.GOOP_TRAP.defaultBlockState());
                    level.playSound(null, pos, ModSounds.BLOCK_GOOP_BLOCK_PLACE, SoundSource.BLOCKS, 1F, 1F);
                }
            }

            this.level.broadcastEntityEvent(this, (byte)3);

            this.discard();
        }
    }
}
