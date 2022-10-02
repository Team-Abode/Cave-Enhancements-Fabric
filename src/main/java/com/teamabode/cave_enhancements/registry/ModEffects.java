package com.teamabode.cave_enhancements.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.effect.ReversalMobEffect;
import com.teamabode.cave_enhancements.effect.ViscousMobEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModEffects {

    public static MobEffect register(String id, MobEffect effect) {
        return Registry.register(Registry.MOB_EFFECT, new ResourceLocation(CaveEnhancements.MODID, id), effect);
    }

    public static final MobEffect REVERSAL = register("reversal", new ReversalMobEffect(MobEffectCategory.BENEFICIAL, 0xf7addc));
    public static final MobEffect STICKY = register("sticky", new ViscousMobEffect(MobEffectCategory.HARMFUL, 0xf0dead).addAttributeModifier(Attributes.MOVEMENT_SPEED, "89266f72-4f61-4151-ac06-104ea9a17f22", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED, "202186ab-317b-4064-a731-135065f562c8", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));


    public static void init() {

    }
}
