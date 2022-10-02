package com.teamabode.cave_enhancements.entity;

import com.teamabode.cave_enhancements.registry.ModEntities;
import com.teamabode.cave_enhancements.registry.ModItems;
import com.teamabode.cave_enhancements.registry.ModParticles;
import java.util.List;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class HarmonicArrow extends AbstractArrow {



    public HarmonicArrow(EntityType<? extends HarmonicArrow> entityType, Level world) {
        super(entityType, world);
    }

    LivingEntity owner = (LivingEntity) this.getOwner();

    public HarmonicArrow(Level world, LivingEntity owner) {
        super(ModEntities.HARMONIC_ARROW, owner, world);
        this.owner = owner;
    }

    public HarmonicArrow(Level world, double x, double y, double z) {
        super(ModEntities.HARMONIC_ARROW, x, y, z, world);
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide && !this.inGround) {
            this.level.addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, -0.5D, 0.0D);
        }
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        harmonicArrowEffects(0.7F, this);
        super.onHitBlock(blockHitResult);
    }

    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        harmonicArrowEffects(0.7F, entity);
        super.onHitEntity(result);
    }

    public void harmonicArrowEffects(float strength, Entity target) {
        AABB box = new AABB(target.blockPosition());
        List<? extends LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box.inflate(3), (entity) -> entity != this.getOwner() && entity != target);

        for (LivingEntity entity : list) {
            if (entity != null) {
                entity.knockback(strength, this.getX() - entity.getX(), this.getZ() - entity.getZ());
                entity.hurtMarked = true;
            }
        }

        if (level instanceof ServerLevel server) {
            server.playSound(null, target.blockPosition(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            server.sendParticles(ModParticles.HARMONIC_WAVE, target.getX(), (target instanceof HarmonicArrow) ? target.getY() : target.getEyeY(), target.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.HARMONIC_ARROW);
    }

}
