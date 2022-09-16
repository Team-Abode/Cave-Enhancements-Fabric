package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

public class ModPotions {

    public static void init() {
        PotionBrewing.addMix(Potions.AWKWARD, ModItems.ROSE_QUARTZ, REVERSAL);
        PotionBrewing.addMix(REVERSAL, Items.REDSTONE, LONG_REVERSAL);
    }

    public static Potion register(String id, Potion potion) {
        return Registry.register(Registry.POTION, new ResourceLocation(CaveEnhancements.MODID, id), potion);
    }

    public static final Potion REVERSAL = register("reversal", new Potion(new MobEffectInstance(ModEffects.REVERSAL, 1800)));
    public static final Potion LONG_REVERSAL = register("long_reversal", new Potion(new MobEffectInstance(ModEffects.REVERSAL, 3600)));
}
