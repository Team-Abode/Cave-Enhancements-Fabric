package com.teamabode.cave_enhancements.world.biome;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.registry.ModBiomes;
import com.teamabode.cave_enhancements.registry.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class CaveBiomes {

    public static void addBasicFeatures(net.minecraft.world.level.biome.BiomeGenerationSettings.Builder generationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }

    // Goop Caves
    public static Biome createGoopCaves() {
        // Spawn Settings
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettings);
        BiomeDefaultFeatures.commonSpawns(spawnSettings);
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.GOOP, 10, 1, 1));

        // Generation Settings
        net.minecraft.world.level.biome.BiomeGenerationSettings.Builder generationBuilder = new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder();
        addBasicFeatures(generationBuilder);

        // Feature Settings
        BiomeGenerationSettings.Builder featureSettings = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addPlainGrass(featureSettings);
        BiomeDefaultFeatures.addDefaultOres(featureSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(featureSettings);

        // Goop Caves
        BiomeModifications.create(new ResourceLocation(CaveEnhancements.MODID + "goop_caves"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(ModBiomes.GOOP_CAVES), ctx -> {
                });

        // Biome Builder
        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x50533)
                        .fogColor(0x878787)
                        .skyColor(0xC0D8FF)
                        .backgroundMusic(Musics.GAME)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(featureSettings.build())
                .build();

    }

    // Rose Quartz Caves
    public static Biome createRoseQuartzCaves() {
        // Spawn Settings
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettings);
        BiomeDefaultFeatures.commonSpawns(spawnSettings);

        // Generation Settings
        net.minecraft.world.level.biome.BiomeGenerationSettings.Builder builder2 = new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder();
        addBasicFeatures(builder2);

        // Feature Settings
        BiomeGenerationSettings.Builder featureSettings = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addPlainGrass(featureSettings);
        BiomeDefaultFeatures.addDefaultOres(featureSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(featureSettings);

        BiomeModifications.create(new ResourceLocation(CaveEnhancements.MODID + "rose_quartz_caves"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(ModBiomes.ROSE_QUARTZ_CAVES), ctx -> {
                });

        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.9F)
                .downfall(0.6F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x6BC0FF)
                        .waterFogColor(0x6BC0FF)
                        .fogColor(0xC0D8FF)
                        .skyColor(0x9084415)
                        .backgroundMusic(Musics.GAME)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(featureSettings.build())
                .build();
    }

    /*
    public static void addGoopCavesFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.GOOP_SPLAT);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ORE_GOOP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.GOOP_PATCH);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DRIPPING_GOOP);
    }

     */
}
