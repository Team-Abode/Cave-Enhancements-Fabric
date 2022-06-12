package com.exdrill.cave_enhancements.entity.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EasingStatusEffect extends MobEffect {
    public EasingStatusEffect(MobEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if ((entity.getHealth() < (entity.getMaxHealth() / 2))) {
            entity.heal(1.0F);
        }
    }
}
