package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModTags {

    public static final TagKey<BannerPattern> GOOP_PATTERN_ITEM = TagKey.create(Registry.BANNER_PATTERN_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "goop_pattern_item"));
    public static final TagKey<Block> RECEIVERS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "receivers"));
    public static final TagKey<Block> PIKE_DESTROYABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "pike_destroyables"));
}
