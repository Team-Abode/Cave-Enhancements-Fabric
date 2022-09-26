package com.teamabode.cave_enhancements.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.world.biome.CaveBiomes;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> GOOP_CAVES = registerBiomeKeys("goop_caves");
    public static final ResourceKey<Biome> ROSE_QUARTZ_CAVES = registerBiomeKeys("rose_quartz_caves");

    private static ResourceKey<Biome> registerBiomeKeys(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, name));
    }

    public static void init() {
        create(GOOP_CAVES, CaveBiomes.createGoopCaves());
        create(ROSE_QUARTZ_CAVES, CaveBiomes.createRoseQuartzCaves());
    }

    private static void create(ResourceKey<Biome> key, Biome biome) {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, key, biome);
    }
}
