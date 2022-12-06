package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.world.biome.CaveBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {

    public static final ResourceKey<Biome> GOOP_CAVES = createKey("goop_caves");
    public static final ResourceKey<Biome> ROSE_QUARTZ_CAVES = createKey("rose_quartz_caves");

    public static void init() {
        register(GOOP_CAVES, CaveBiomes.goopCaves());
        register(ROSE_QUARTZ_CAVES, CaveBiomes.roseQuartzCaves());
    }

    private static Holder<Biome> register(ResourceKey<Biome> biomeKey, Biome biome) {
        return BuiltinRegistries.register(BuiltinRegistries.BIOME, biomeKey, biome);
    }

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, name));
    }
}
