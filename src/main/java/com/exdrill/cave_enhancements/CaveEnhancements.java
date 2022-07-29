package com.exdrill.cave_enhancements;

import com.exdrill.cave_enhancements.entity.Cruncher;
import com.exdrill.cave_enhancements.entity.DripstoneTortoise;
import com.exdrill.cave_enhancements.registry.*;
import com.exdrill.cave_enhancements.terrablender.TerrablenderIntegration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.launch.FabricLauncher;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class CaveEnhancements implements ModInitializer {
    public static final String MODID = "cave_enhancements";
    public static final Logger LOGGER = LoggerFactory.getLogger("Cave Enhancements");

    public static final BannerPattern GOOP = new BannerPattern("goop");


    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        ModSounds.register();
        ModEntities.register();
        ModBiomes.register();
        ModParticles.register();
        ModEffects.register();
        Registry.register(Registry.BANNER_PATTERN, new ResourceLocation(MODID, "goop"), GOOP);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES), MobCategory.MONSTER, ModEntities.DRIPSTONE_TORTOISE, 100, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES), MobCategory.MONSTER, ModEntities.CRUNCHER, 5, 1, 1);

        SpawnRestrictionAccessor.callRegister(ModEntities.DRIPSTONE_TORTOISE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, DripstoneTortoise::checkDripstoneTortoiseSpawnRules);
        SpawnRestrictionAccessor.callRegister(ModEntities.CRUNCHER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Cruncher::checkCruncherSpawnRules);


        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.REDSTONE_RECEIVER, ModBlocks.EXPOSED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.EXPOSED_REDSTONE_RECEIVER, ModBlocks.WEATHERED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.WEATHERED_REDSTONE_RECEIVER, ModBlocks.OXIDIZED_REDSTONE_RECEIVER);

        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.REDSTONE_RECEIVER, ModBlocks.WAXED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.EXPOSED_REDSTONE_RECEIVER, ModBlocks.WAXED_EXPOSED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.WEATHERED_REDSTONE_RECEIVER, ModBlocks.WAXED_WEATHERED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.OXIDIZED_REDSTONE_RECEIVER, ModBlocks.WAXED_OXIDIZED_REDSTONE_RECEIVER);


        if (FabricLoaderImpl.INSTANCE.isModLoaded("terrablender")) {
            LOGGER.info("Terrablender detected, integrating...");

        } else {
            LOGGER.info("Terrablender not found, skipping Terrablender integration");
        }

    }
}

