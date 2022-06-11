package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModEntities;
import com.exdrill.cave_enhancements.registry.ModItems;
import com.exdrill.cave_enhancements.registry.ModSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BigGoopDripProjectile extends ThrownItemEntity {
    public BigGoopDripProjectile(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BigGoopDripProjectile(World world, LivingEntity owner) {
        super(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, owner, world); // null will be changed later
    }

    public BigGoopDripProjectile(World world, double x, double y, double z) {
        super(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, x, y, z, world); // null will be changed later
    }

    //Item projectile is rendered as
    @Override
    protected Item getDefaultItem() {
        return ModItems.BIG_GOOP_DRIP;
    }

    //On hit particles
    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(ModItems.BIG_GOOP_DRIP, 1));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public boolean hitEntity = false;

    //When Hit Entity
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity();

        entity.damage(DamageSource.GENERIC, 3F);

        hitEntity = true;
    }


    //Generic Hit (Has block hit inside)
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            if (!hitEntity) {

                BlockPos pos = new BlockPos(hitResult.getPos());

                if (world.getBlockState(pos).isOf(Blocks.AIR)) {
                    world.setBlockState(pos, ModBlocks.GOOP_TRAP.getDefaultState());
                    world.playSound(null, pos, ModSounds.BLOCK_GOOP_BLOCK_PLACE, SoundCategory.BLOCKS, 1F, 1F);
                }
            }

            this.world.sendEntityStatus(this, (byte)3);

            this.discard();
        }
    }


}
