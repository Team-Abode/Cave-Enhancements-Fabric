package com.exdrill.cave_enhancements.block.entity;

import com.exdrill.cave_enhancements.registry.ModBlockEntities;
import com.exdrill.cave_enhancements.registry.ModParticles;
import com.exdrill.cave_enhancements.registry.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RoseQuartzChimesBlockEntity extends BlockEntity {
    public int ticksTillActivateClear = 1200;
    public int ticking = 0;


    public RoseQuartzChimesBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROSE_QUARTZ_CHIMES, pos, state);
    }

    public static void tick(Level world, BlockPos pos, RoseQuartzChimesBlockEntity entity) {
        ++entity.ticking;

        if(entity.ticksTillActivateClear > 0) {
            entity.ticksTillActivateClear--;
        }

        AABB box = new AABB(pos).inflate(8);

        List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, box, (e) -> true);

        LivingEntity otherEntity;
        for (LivingEntity livingEntity : list) {
            otherEntity = livingEntity;

            if (otherEntity instanceof Monster) {
                if (world.isRaining() && entity.ticksTillActivateClear <= 600) {
                    otherEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 1, false, true));
                } else if (!world.isRaining() && entity.ticksTillActivateClear <= 0) {
                    otherEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 0, false, true));
                }
            }
            if ((otherEntity instanceof AgeableMob || otherEntity instanceof Player)) {
                if (world.isRaining() && entity.ticksTillActivateClear <= 600 && !otherEntity.hasEffect(ModEffects.EASING) ) {
                    otherEntity.addEffect(new MobEffectInstance(ModEffects.EASING, 300, 1, false, true));
                } else if (!world.isRaining() && entity.ticksTillActivateClear <= 0 && !otherEntity.hasEffect(ModEffects.EASING)) {
                    otherEntity.addEffect(new MobEffectInstance(ModEffects.EASING, 300, 0, false, true));
                }
            }
        }

        if ((entity.ticksTillActivateClear <= 600 && world.isRaining()) || (entity.ticksTillActivateClear <= 0 && !world.isRaining())) {
            entity.ticksTillActivateClear = 1200;
            world.addParticle(ModParticles.ROSE_CHIMES, entity.getBlockPos().getX() + 0.5D, entity.getBlockPos().getY() + 0.3D, entity.getBlockPos().getZ() + 0.5D, 0D, 0D, 0D);
            world.playSound(null, pos, SoundEvents.NOTE_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
