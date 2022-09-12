package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.item.AmethystFluteItem;
import com.exdrill.cave_enhancements.item.GlowPasteItem;
import com.exdrill.cave_enhancements.item.GoopBucketItem;
import com.exdrill.cave_enhancements.item.HarmonicArrowItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.material.Fluids;

public class ModItems {

    public static final Item GLOW_PASTE = register("glow_paste", new GlowPasteItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).durability(32)));
    public static final Item ROSE_QUARTZ = register("rose_quartz", new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final Item AMETHYST_FLUTE = register("amethyst_flute", new AmethystFluteItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).durability(64)));
    public static final Item HARMONIC_ARROW = register("harmonic_arrow", new HarmonicArrowItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final Item GOOP_BUCKET = register("goop_bucket", new GoopBucketItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));
    public static final Item GOOP_BANNER_PATTERN = register("goop_banner_pattern", new BannerPatternItem(ModTags.GOOP_PATTERN_ITEM,new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final Item GOOP_SPAWN_EGG = register("goop_spawn_egg", new SpawnEggItem(ModEntities.GOOP, 13946012, 11637089, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Item CRUNCHER_SPAWN_EGG = register("cruncher_spawn_egg", new SpawnEggItem(ModEntities.CRUNCHER, 11127234, 5757312, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Item DRIPSTONE_TORTOISE_SPAWN_EGG = register("dripstone_tortoise_spawn_egg", new SpawnEggItem(ModEntities.DRIPSTONE_TORTOISE, 8156236, 6967114, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final Item BIG_GOOP_DRIP = register("big_goop_drip",new Item(new Item.Properties()));

    public static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, id), item);
    }

    public static void register() {}
}
