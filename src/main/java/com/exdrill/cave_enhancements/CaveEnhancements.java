package com.exdrill.cave_enhancements;

import com.exdrill.cave_enhancements.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.function.Consumer;

public class CaveEnhancements implements ModInitializer {
    public static final String MODID = "cave_enhancements";

    public static final BannerPattern GOOP = new BannerPattern("goop");




    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModItems.register();
        ModBlocks.registerBlockEntities();
        ModSounds.register();
        ModEntities.register();
        ModBiomes.register();
        ModParticles.register();
        ModBiomes.registerModifications();
        ModStatusEffects.register();
        ModBlocks.registerOxidizablePairs();
        Registry.register(Registry.BANNER_PATTERN, new ResourceLocation(MODID, "goop"), GOOP);
    }
}

