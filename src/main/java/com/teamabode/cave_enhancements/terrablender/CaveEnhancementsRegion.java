package com.teamabode.cave_enhancements.terrablender;

import com.teamabode.cave_enhancements.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class CaveEnhancementsRegion extends Region {

    public CaveEnhancementsRegion(ResourceLocation name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(ModBiomes.GOOP_CAVES, ModBiomes.GOOP_CAVES);
            builder.replaceBiome(ModBiomes.ROSE_QUARTZ_CAVES, ModBiomes.ROSE_QUARTZ_CAVES);
        });
    }
}
