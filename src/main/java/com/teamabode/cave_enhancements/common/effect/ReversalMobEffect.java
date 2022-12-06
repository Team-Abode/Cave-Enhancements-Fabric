package com.teamabode.cave_enhancements.common.effect;

import com.teamabode.cave_enhancements.accessor.LivingEntityAccess;
import com.teamabode.cave_enhancements.core.registry.ModEffects;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.Map;

public class ReversalMobEffect extends MobEffect {
    public ReversalMobEffect(MobEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "b0675f38-b536-4dda-b068-dfc145c2016d", 0.0F, AttributeModifier.Operation.ADDITION);
    }
    
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMapIn, int amplifier) {
        if (!entity.level.isClientSide) {

            for (Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
                AttributeInstance attributeInstance = attributeMapIn.getInstance(entry.getKey());
                int amount = ((LivingEntityAccess)entity).getReversalDamage();

                if (attributeInstance != null) {
                    AttributeModifier attributemodifier = entry.getValue();
                    attributeInstance.removeModifier(attributemodifier);
                    attributeInstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), "Reversal Boost", amount, attributemodifier.getOperation()));
                }
            }
        }
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        this.addAttributeModifiers(entity, entity.getAttributes(), amplifier);
    }

    public static void onReversal(LivingEntity attacker) {
        if (attacker.hasEffect(ModEffects.REVERSAL) && attacker instanceof LivingEntityAccess trueAttacker) {
            if (trueAttacker.getReversalDamage() > 0) {
                attacker.level.playSound(null, attacker.blockPosition(), ModSounds.EFFECT_REVERSAL_REVERSE, SoundSource.PLAYERS, 0.75F, 1.0F);
            }
            trueAttacker.setReversalDamage(0);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
