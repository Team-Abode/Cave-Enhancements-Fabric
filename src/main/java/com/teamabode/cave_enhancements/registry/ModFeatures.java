package com.teamabode.cave_enhancements.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.world.feature.RoseQuartzCrystalFeature;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModFeatures {

    public static final Feature<NoneFeatureConfiguration> ROSE_QUARTZ_CRYSTAL = new RoseQuartzCrystalFeature(NoneFeatureConfiguration.CODEC);

    public static void init() {
        Registry.register(Registry.FEATURE, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_crystal"), ROSE_QUARTZ_CRYSTAL);
    }
}
