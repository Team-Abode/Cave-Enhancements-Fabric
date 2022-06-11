package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.effect.EasingStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStatusEffects {

    public static final StatusEffect EASING = new EasingStatusEffect(StatusEffectCategory.BENEFICIAL, 0xf7addc);

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(CaveEnhancements.MODID, "easing"), EASING);
    }
}
