package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.registry.ModEntities;
import com.exdrill.cave_enhancements.registry.ModItems;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class HarmonicArrowEntity extends PersistentProjectileEntity {



    public HarmonicArrowEntity(EntityType<? extends HarmonicArrowEntity> entityType, World world) {
        super(entityType, world);

    }

    LivingEntity owner = (LivingEntity) this.getOwner();

    public HarmonicArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.HARMONIC_ARROW, owner, world);
        this.owner = owner;
    }

    public HarmonicArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.HARMONIC_ARROW, x, y, z, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient && !this.inGround) {
            this.world.addParticle(ModParticles.HOVERING_NOTE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.5D, 0.0D);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        knockbackEntitiesAround(0.35F);
        if (this.world.isClient) {
            this.world.addParticle(ModParticles.AMETHYST_BLAST, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onHit(LivingEntity target) {
        knockbackEntitiesAround(0.2F);
    }

    public void knockbackEntitiesAround( float strength ) {

        Box box = new Box(this.getBlockPos());
        List<? extends LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box.expand(3), (entity) -> entity != this.getOwner());

        for (LivingEntity entity : list) {
            if (entity != null) {
                entity.takeKnockback(strength + getAdditionalPower(), this.getX() - entity.getX(), this.getZ() - entity.getZ());
                entity.velocityModified = true;
            }
        }

        if (!world.isClient) {
            this.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 1.0F, 1.0F);
        }
    }

    public float getAdditionalPower() {
        if (owner != null) {
            Hand hand = owner.getMainHandStack().getItem() == Items.BOW ? Hand.MAIN_HAND : Hand.OFF_HAND;
            ItemStack itemStack = owner.getStackInHand(hand);

            return EnchantmentHelper.getLevel(Enchantments.POWER, itemStack) / 30F;
        }
        return 0;
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.HARMONIC_ARROW);
    }

}
