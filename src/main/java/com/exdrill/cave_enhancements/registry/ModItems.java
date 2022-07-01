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

    //Items
    public static final BlockItem GOOP = new BlockItem(ModBlocks.GOOP_SPLAT, new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final BlockItem GOOP_BLOCK = new BlockItem(ModBlocks.GOOP_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem GOOP_TRAP = new BlockItem(ModBlocks.GOOP_TRAP, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem DRIPPING_GOOP = new BlockItem(ModBlocks.DRIPPING_GOOP, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final GlowPasteItem GLOW_PASTE = new GlowPasteItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).durability(32));
    public static final BlockItem SPECTACLE_CANDLE = new BlockItem(ModBlocks.SPECTACLE_CANDLE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final BlockItem LIGHTNING_ANCHOR = new BlockItem(ModBlocks.LIGHTNING_ANCHOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final BlockItem CHARGED_LIGHTNING_ANCHOR = new BlockItem(ModBlocks.CHARGED_LIGHTNING_ANCHOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final AmethystFluteItem AMETHYST_FLUTE = new AmethystFluteItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).durability(64));
    public static final SpawnEggItem GOOP_SPAWN_EGG = new SpawnEggItem(ModEntities.GOOP, 13946012, 11637089, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    public static final GoopBucketItem GOOP_BUCKET = new GoopBucketItem(ModEntities.GOOP, Fluids.EMPTY, ModSounds.ITEM_BUCKET_EMPTY_GOOP, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
    public static final BannerPatternItem GOOP_BANNER_PATTERN = new BannerPatternItem(ModTags.GOOP_PATTERN_ITEM,new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    // Redstone Receivers
    public static final BlockItem REDSTONE_RECEIVER = new BlockItem(ModBlocks.REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final BlockItem EXPOSED_REDSTONE_RECEIVER = new BlockItem(ModBlocks.EXPOSED_REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final BlockItem WEATHERED_REDSTONE_RECEIVER = new BlockItem(ModBlocks.WEATHERED_REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final BlockItem OXIDIZED_REDSTONE_RECEIVER = new BlockItem(ModBlocks.OXIDIZED_REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));

    public static final BlockItem WAXED_REDSTONE_RECEIVER = new BlockItem(ModBlocks.WAXED_REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final BlockItem WAXED_EXPOSED_REDSTONE_RECEIVER = new BlockItem(ModBlocks.WAXED_EXPOSED_REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final BlockItem WAXED_WEATHERED_REDSTONE_RECEIVER = new BlockItem(ModBlocks.WAXED_WEATHERED_REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final BlockItem WAXED_OXIDIZED_REDSTONE_RECEIVER = new BlockItem(ModBlocks.WAXED_OXIDIZED_REDSTONE_RECEIVER, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));


    public static final Item BIG_GOOP_DRIP = new Item(new Item.Properties());
    
    public static final SpawnEggItem CRUNCHER_SPAWN_EGG = new SpawnEggItem(ModEntities.CRUNCHER, 11127234, 5757312, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    public static final SpawnEggItem DRIPSTONE_TORTOISE_SPAWN_EGG = new SpawnEggItem(ModEntities.DRIPSTONE_TORTOISE, 8156236, 6967114, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    public static final BlockItem ROSE_QUARTZ_BLOCK = new BlockItem(ModBlocks.ROSE_QUARTZ_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem JAGGED_ROSE_QUARTZ = new BlockItem(ModBlocks.JAGGED_ROSE_QUARTZ, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final Item ROSE_QUARTZ = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final BlockItem POLISHED_ROSE_QUARTZ = new BlockItem(ModBlocks.POLISHED_ROSE_QUARTZ, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem POLISHED_ROSE_QUARTZ_SLAB = new BlockItem(ModBlocks.POLISHED_ROSE_QUARTZ_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem POLISHED_ROSE_QUARTZ_STAIRS = new BlockItem(ModBlocks.POLISHED_ROSE_QUARTZ_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem POLISHED_ROSE_QUARTZ_WALL = new BlockItem(ModBlocks.POLISHED_ROSE_QUARTZ_WALL, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final BlockItem ROSE_QUARTZ_TILES = new BlockItem(ModBlocks.ROSE_QUARTZ_TILES, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem ROSE_QUARTZ_TILE_SLAB = new BlockItem(ModBlocks.ROSE_QUARTZ_TILE_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem ROSE_QUARTZ_TILE_STAIRS = new BlockItem(ModBlocks.ROSE_QUARTZ_TILE_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem ROSE_QUARTZ_TILE_WALL = new BlockItem(ModBlocks.ROSE_QUARTZ_TILE_WALL, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final BlockItem ROSE_QUARTZ_CHIMES = new BlockItem(ModBlocks.ROSE_QUARTZ_CHIMES, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final BlockItem ROSE_QUARTZ_LAMP = new BlockItem(ModBlocks.ROSE_QUARTZ_LAMP, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final BlockItem SOUL_ROSE_QUARTZ_LAMP  = new BlockItem(ModBlocks.SOUL_ROSE_QUARTZ_LAMP, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final HarmonicArrowItem HARMONIC_ARROW = new HarmonicArrowItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));

    public static final BlockItem ENCHANTED_TETHER = new BlockItem(ModBlocks.TEST, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    //Registry
    public static void register() {
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "goop"), GOOP);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "goop_block"), GOOP_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "goop_trap"), GOOP_TRAP);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "dripping_goop"), DRIPPING_GOOP);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "glow_paste"), GLOW_PASTE);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "spectacle_candle"), SPECTACLE_CANDLE);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "lightning_anchor"), LIGHTNING_ANCHOR);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "charged_lightning_anchor"), CHARGED_LIGHTNING_ANCHOR);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "amethyst_flute"), AMETHYST_FLUTE);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "goop_bucket"), GOOP_BUCKET);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "big_goop_drip"), BIG_GOOP_DRIP);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz"), ROSE_QUARTZ);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_block"), ROSE_QUARTZ_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "jagged_rose_quartz"), JAGGED_ROSE_QUARTZ);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz"), POLISHED_ROSE_QUARTZ);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz_slab"), POLISHED_ROSE_QUARTZ_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz_stairs"), POLISHED_ROSE_QUARTZ_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz_wall"), POLISHED_ROSE_QUARTZ_WALL);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tiles"), ROSE_QUARTZ_TILES);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tile_slab"), ROSE_QUARTZ_TILE_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tile_stairs"), ROSE_QUARTZ_TILE_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tile_wall"), ROSE_QUARTZ_TILE_WALL);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_chimes"), ROSE_QUARTZ_CHIMES);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_lamp"), ROSE_QUARTZ_LAMP);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "soul_rose_quartz_lamp"), SOUL_ROSE_QUARTZ_LAMP);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "goop_banner_pattern"), GOOP_BANNER_PATTERN);

        // Redstone Receivers
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "redstone_receiver"), REDSTONE_RECEIVER);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "exposed_redstone_receiver"), EXPOSED_REDSTONE_RECEIVER);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "weathered_redstone_receiver"), WEATHERED_REDSTONE_RECEIVER);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "oxidized_redstone_receiver"), OXIDIZED_REDSTONE_RECEIVER);

        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "waxed_redstone_receiver"), WAXED_REDSTONE_RECEIVER);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "waxed_exposed_redstone_receiver"), WAXED_EXPOSED_REDSTONE_RECEIVER);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "waxed_weathered_redstone_receiver"), WAXED_WEATHERED_REDSTONE_RECEIVER);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "waxed_oxidized_redstone_receiver"), WAXED_OXIDIZED_REDSTONE_RECEIVER);

        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "harmonic_arrow"), HARMONIC_ARROW);

        // Spawn Eggs
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "goop_spawn_egg"), GOOP_SPAWN_EGG);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "cruncher_spawn_egg"), CRUNCHER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "dripstone_tortoise_spawn_egg"), DRIPSTONE_TORTOISE_SPAWN_EGG);

        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "enchanted_tether"), ENCHANTED_TETHER);
    }
}
