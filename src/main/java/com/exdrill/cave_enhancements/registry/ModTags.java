package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {

    public static final TagKey<BannerPattern> GOOP_PATTERN_ITEM = TagKey.of(Registry.BANNER_PATTERN_KEY, new Identifier(CaveEnhancements.MODID, "goop_pattern_item"));
    public static final TagKey<Block> RECEIVERS = TagKey.of(Registry.BLOCK_KEY, new Identifier(CaveEnhancements.MODID, "receivers"));
    public static final TagKey<Block> PIKE_DESTROYABLES = TagKey.of(Registry.BLOCK_KEY, new Identifier(CaveEnhancements.MODID, "pike_destroyables"));
}
