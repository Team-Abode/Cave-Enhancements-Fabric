package com.exdrill.cave_enhancements.world.biome;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.registry.ModBiomes;
import com.exdrill.cave_enhancements.registry.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class CaveBiomes {

    public static void addBasicFeatures(net.minecraft.world.biome.GenerationSettings.Builder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }

    // Goop Caves
    public static Biome createGoopCaves() {
        // Spawn Settings
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.GOOP, 10, 1, 1));

        // Generation Settings
        net.minecraft.world.biome.GenerationSettings.Builder builder2 = new net.minecraft.world.biome.GenerationSettings.Builder();
        addBasicFeatures(builder2);

        // Feature Settings
        GenerationSettings.Builder featureSettings = new GenerationSettings.Builder();
        DefaultBiomeFeatures.addPlainsTallGrass(featureSettings);
        DefaultBiomeFeatures.addDefaultOres(featureSettings);
        DefaultBiomeFeatures.addDefaultDisks(featureSettings);

        // Goop Caves
        BiomeModifications.create(new Identifier(CaveEnhancements.MODID + "goop_caves"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(ModBiomes.GOOP_CAVES), ctx -> {
                });

        // Biome Builder
        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.5F)
                .downfall(0.5F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x50533)
                        .fogColor(0x878787)
                        .skyColor(0xC0D8FF)
                        .music(MusicType.GAME)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(featureSettings.build())
                .build();

    }

    // Rose Quartz Caves
    public static Biome createRoseQuartzCaves() {
        // Spawn Settings
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

        // Generation Settings
        net.minecraft.world.biome.GenerationSettings.Builder builder2 = new net.minecraft.world.biome.GenerationSettings.Builder();
        addBasicFeatures(builder2);

        // Feature Settings
        GenerationSettings.Builder featureSettings = new GenerationSettings.Builder();
        DefaultBiomeFeatures.addPlainsTallGrass(featureSettings);
        DefaultBiomeFeatures.addDefaultOres(featureSettings);
        DefaultBiomeFeatures.addDefaultDisks(featureSettings);

        BiomeModifications.create(new Identifier(CaveEnhancements.MODID + "rose_quartz_caves"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(ModBiomes.ROSE_QUARTZ_CAVES), ctx -> {
                });

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.9F)
                .downfall(0.6F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x6BC0FF)
                        .waterFogColor(0x6BC0FF)
                        .fogColor(0xC0D8FF)
                        .skyColor(0x9084415)
                        .music(MusicType.GAME)
                        .build())
                .spawnSettings(spawnSettings.build())
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
