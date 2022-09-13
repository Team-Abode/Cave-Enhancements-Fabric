package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.effect.ReversalMobEffect;
import com.exdrill.cave_enhancements.effect.StickingMobEffect;
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
    public static final MobEffect STICKING = register("sticking", new StickingMobEffect(MobEffectCategory.HARMFUL, 0xf0dead).addAttributeModifier(Attributes.MOVEMENT_SPEED, "89266f72-4f61-4151-ac06-104ea9a17f22", -0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL));


    public static void init() {

    }
}
