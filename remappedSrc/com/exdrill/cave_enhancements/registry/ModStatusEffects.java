package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.effect.EasingStatusEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModStatusEffects {

    public static final MobEffect EASING = new EasingStatusEffect(MobEffectCategory.BENEFICIAL, 0xf7addc);

    public static void register() {
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(CaveEnhancements.MODID, "easing"), EASING);
    }
}
