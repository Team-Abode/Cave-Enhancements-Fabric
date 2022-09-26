package com.teamabode.cave_enhancements.entity;

import com.teamabode.cave_enhancements.registry.ModEntities;
import com.teamabode.cave_enhancements.registry.ModItems;
import com.teamabode.cave_enhancements.registry.ModParticles;
import java.util.List;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
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

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide && !this.inGround) {
            this.level.addParticle(ModParticles.HARMONIC_NOTE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.5D, 0.0D);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        knockbackEntitiesAround(0.45F);
        if (this.level.isClientSide) {
            this.level.addParticle(ModParticles.HARMONIC_WAVE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
        super.onHitBlock(blockHitResult);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        knockbackEntitiesAround(0.25F);
    }

    public void knockbackEntitiesAround( float strength ) {

        AABB box = new AABB(this.blockPosition());
        List<? extends LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box.inflate(3), (entity) -> entity != this.getOwner());

        for (LivingEntity entity : list) {
            if (entity != null) {
                entity.knockback(strength + getAdditionalPower(), this.getX() - entity.getX(), this.getZ() - entity.getZ());
                entity.hurtMarked = true;
            }
        }

        if (!level.isClientSide) {
            this.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1.0F, 1.0F);
        }
    }

    public float getAdditionalPower() {
        if (owner != null) {
            InteractionHand hand = owner.getMainHandItem().getItem() == Items.BOW ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack itemStack = owner.getItemInHand(hand);



            return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack) / 30F;
        }
        return 0;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.HARMONIC_ARROW);
    }

}
