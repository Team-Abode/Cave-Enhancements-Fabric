package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.world.biome.CaveBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class ModBiomes {
    public static final ResourceKey<Biome> GOOP_CAVES = registerBiomeKeys("goop_caves");
    public static final ResourceKey<Biome> ROSE_QUARTZ_CAVES = registerBiomeKeys("rose_quartz_caves");

    private static ResourceKey<Biome> registerBiomeKeys(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, name));
    }

    public static void register() {
        register(GOOP_CAVES, CaveBiomes.createGoopCaves());
        register(ROSE_QUARTZ_CAVES, CaveBiomes.createRoseQuartzCaves());
    }

    private static Holder<Biome> register(ResourceKey<Biome> key, Biome biome) {
        return BuiltinRegistries.register(BuiltinRegistries.BIOME, key, biome);
    }
}
