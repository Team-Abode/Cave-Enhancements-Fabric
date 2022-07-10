package com.exdrill.cave_enhancements;

import com.exdrill.cave_enhancements.entity.Cruncher;
import com.exdrill.cave_enhancements.entity.DripstoneTortoise;
import com.exdrill.cave_enhancements.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.Heightmap;

public class CaveEnhancements implements ModInitializer {
    public static final String MODID = "cave_enhancements";

    public static final BannerPattern GOOP = new BannerPattern("goop");

    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModBlockEntities.register();

        ModEntities.register();
        ModItems.register();

        ModSounds.register();
        ModBiomes.register();
        ModParticles.register();
        ModEffects.register();
        Registry.register(Registry.BANNER_PATTERN, new ResourceLocation(MODID, "goop"), GOOP);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES), MobCategory.MONSTER, ModEntities.DRIPSTONE_TORTOISE, 100, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES), MobCategory.MONSTER, ModEntities.CRUNCHER, 75, 1, 1);

        SpawnRestrictionAccessor.callRegister(ModEntities.DRIPSTONE_TORTOISE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, DripstoneTortoise::checkDripstoneTortoiseSpawnRules);
        SpawnRestrictionAccessor.callRegister(ModEntities.CRUNCHER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Cruncher::checkCruncherSpawnRules);
    }
}

