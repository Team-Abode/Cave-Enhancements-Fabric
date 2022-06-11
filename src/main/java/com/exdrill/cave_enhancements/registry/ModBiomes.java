package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.world.biome.CaveBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class ModBiomes {
    public static final RegistryKey<Biome> GOOP_CAVES = registerBiomeKeys("goop_caves");
    public static final RegistryKey<Biome> ROSE_QUARTZ_CAVES = registerBiomeKeys("rose_quartz_caves");

    private static RegistryKey<Biome> registerBiomeKeys(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(CaveEnhancements.MODID, name));
    }

    public static void register() {
        register(GOOP_CAVES, CaveBiomes.createGoopCaves());
        register(ROSE_QUARTZ_CAVES, CaveBiomes.createRoseQuartzCaves());
    }

    public static void registerModifications() {
          BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DRIPSTONE_CAVES), SpawnGroup.MONSTER, ModEntities.DRIPSTONE_TORTOISE, 100, 2, 3);
          BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES), SpawnGroup.MONSTER, ModEntities.CRUNCHER, 75, 1, 1);
    }


    private static RegistryEntry<Biome> register(RegistryKey<Biome> key, Biome biome) {
        return BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome);
    }
}
