package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.block.*;
import com.exdrill.cave_enhancements.block.entity.LightningAnchorBlockEntity;
import com.exdrill.cave_enhancements.block.entity.ReceiverBlockEntity;
import com.exdrill.cave_enhancements.block.entity.RoseQuartzChimesBlockEntity;
import com.exdrill.cave_enhancements.block.entity.SpectacleCandleBlockEntity;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    
    // Block Components
    public static final Block GOOP_BLOCK = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).velocityMultiplier(0.3F).strength(0.5F, 1.0F).sounds(ModSounds.GOOP_BLOCK).jumpVelocityMultiplier(0.9F));
    public static final GoopTrapBlock GOOP_TRAP = new GoopTrapBlock(FabricBlockSettings.of(Material.GOURD).strength(2.0F, 5.0F).sounds(ModSounds.GOOP_BLOCK).velocityMultiplier( 0.01F).jumpVelocityMultiplier(0.3F).slipperiness(0.8F).mapColor(MapColor.PALE_YELLOW));
    public static final GoopSplatBlock GOOP_SPLAT = new GoopSplatBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).breakInstantly().sounds(ModSounds.GOOP_DECORATION).noCollision().nonOpaque().mapColor(MapColor.PALE_YELLOW));
    public static final DrippingGoopBlock DRIPPING_GOOP = new DrippingGoopBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).sounds(ModSounds.GOOP_DECORATION).nonOpaque().noCollision().luminance(2).mapColor(MapColor.PALE_YELLOW));

    public static final GlowSplotchBlock GLOW_SPLOTCH = new GlowSplotchBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).sounds(BlockSoundGroup.HONEY).nonOpaque().noCollision().luminance(8).mapColor(MapColor.PALE_YELLOW));
    public static final SpectacleCandleBlock SPECTACLE_CANDLE = new SpectacleCandleBlock(FabricBlockSettings.of(Material.DECORATION).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE).strength(0.1F, 0F));

    public static final Block LIGHTNING_ANCHOR = new LightningAnchorBlock(FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresTool().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER));
    public static final Block CHARGED_LIGHTNING_ANCHOR = new ChargedLightningAnchorBlock(FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresTool().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).luminance((state) -> 15));

    public static final Block ROSE_QUARTZ_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final JaggedRoseQuartzBlock JAGGED_ROSE_QUARTZ = new JaggedRoseQuartzBlock(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).noCollision().sounds(ModSounds.ROSE_QUARTZ));
    public static final Block POLISHED_ROSE_QUARTZ = new Block(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final SlabBlock POLISHED_ROSE_QUARTZ_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final StairsBlock POLISHED_ROSE_QUARTZ_STAIRS = new StairsBlock(POLISHED_ROSE_QUARTZ.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ).nonOpaque());
    public static final WallBlock POLISHED_ROSE_QUARTZ_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final Block ROSE_QUARTZ_TILES = new Block(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final SlabBlock ROSE_QUARTZ_TILE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final StairsBlock ROSE_QUARTZ_TILE_STAIRS = new StairsBlock(ROSE_QUARTZ_TILES.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final WallBlock ROSE_QUARTZ_TILE_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ));
    public static final Block ROSE_QUARTZ_CHIMES = new RoseQuartzChimesBlock(FabricBlockSettings.of(Material.STONE).strength(2F, 10).requiresTool().mapColor(MapColor.PINK).sounds(ModSounds.ROSE_QUARTZ).nonOpaque());
    public static final RoseQuartzLampBlock ROSE_QUARTZ_LAMP = new RoseQuartzLampBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.LANTERN).luminance(15));
    public static final RoseQuartzLampBlock SOUL_ROSE_QUARTZ_LAMP = new RoseQuartzLampBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.LANTERN).luminance(15));

    public static final OxidizableReceiverBlock REDSTONE_RECEIVER = new OxidizableReceiverBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).breakInstantly());
    public static final OxidizableReceiverBlock EXPOSED_REDSTONE_RECEIVER = new OxidizableReceiverBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).sounds(BlockSoundGroup.COPPER).breakInstantly());
    public static final OxidizableReceiverBlock WEATHERED_REDSTONE_RECEIVER = new OxidizableReceiverBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.DARK_AQUA).sounds(BlockSoundGroup.COPPER).breakInstantly());
    public static final OxidizableReceiverBlock OXIDIZED_REDSTONE_RECEIVER = new OxidizableReceiverBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.TEAL).sounds(BlockSoundGroup.COPPER).breakInstantly());

    public static final ReceiverBlock WAXED_REDSTONE_RECEIVER = new ReceiverBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).breakInstantly());
    public static final ReceiverBlock WAXED_EXPOSED_REDSTONE_RECEIVER = new ReceiverBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).sounds(BlockSoundGroup.COPPER).breakInstantly());
    public static final ReceiverBlock WAXED_WEATHERED_REDSTONE_RECEIVER = new ReceiverBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.DARK_AQUA).sounds(BlockSoundGroup.COPPER).breakInstantly());
    public static final ReceiverBlock WAXED_OXIDIZED_REDSTONE_RECEIVER = new ReceiverBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.of(Material.DECORATION).mapColor(MapColor.TEAL).sounds(BlockSoundGroup.COPPER).breakInstantly());

    // Block Registry
    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "goop_block"), GOOP_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "goop_splat"), GOOP_SPLAT);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "goop_trap"), GOOP_TRAP);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "dripping_goop"), DRIPPING_GOOP);

        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "glow_splotch"), GLOW_SPLOTCH);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "spectacle_candle"), SPECTACLE_CANDLE);

        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "lightning_anchor"), LIGHTNING_ANCHOR);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "charged_lightning_anchor"), CHARGED_LIGHTNING_ANCHOR);

        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "rose_quartz_block"), ROSE_QUARTZ_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "jagged_rose_quartz"), JAGGED_ROSE_QUARTZ);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "polished_rose_quartz"), POLISHED_ROSE_QUARTZ);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "polished_rose_quartz_slab"), POLISHED_ROSE_QUARTZ_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "polished_rose_quartz_stairs"), POLISHED_ROSE_QUARTZ_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "polished_rose_quartz_wall"), POLISHED_ROSE_QUARTZ_WALL);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "rose_quartz_tiles"), ROSE_QUARTZ_TILES);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "rose_quartz_tile_slab"), ROSE_QUARTZ_TILE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "rose_quartz_tile_stairs"), ROSE_QUARTZ_TILE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "rose_quartz_tile_wall"), ROSE_QUARTZ_TILE_WALL);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "rose_quartz_chimes"), ROSE_QUARTZ_CHIMES);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "rose_quartz_lamp"), ROSE_QUARTZ_LAMP);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "soul_rose_quartz_lamp"), SOUL_ROSE_QUARTZ_LAMP);

        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "redstone_receiver"), REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "exposed_redstone_receiver"), EXPOSED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "weathered_redstone_receiver"), WEATHERED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "oxidized_redstone_receiver"), OXIDIZED_REDSTONE_RECEIVER);

        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "waxed_redstone_receiver"), WAXED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "waxed_exposed_redstone_receiver"), WAXED_EXPOSED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "waxed_weathered_redstone_receiver"), WAXED_WEATHERED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.MODID, "waxed_oxidized_redstone_receiver"), WAXED_OXIDIZED_REDSTONE_RECEIVER);
    }

    // Block Render Type
    public static void registerClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(GOOP_SPLAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DRIPPING_GOOP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GLOW_SPLOTCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SPECTACLE_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JAGGED_ROSE_QUARTZ, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(REDSTONE_RECEIVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EXPOSED_REDSTONE_RECEIVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WEATHERED_REDSTONE_RECEIVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OXIDIZED_REDSTONE_RECEIVER, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_REDSTONE_RECEIVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_EXPOSED_REDSTONE_RECEIVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_WEATHERED_REDSTONE_RECEIVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_OXIDIZED_REDSTONE_RECEIVER, RenderLayer.getCutout());
    }

    // Block Entity
    public static BlockEntityType<SpectacleCandleBlockEntity> SPECTACLE_CANDLE_BLOCK_ENTITY;
    public static BlockEntityType<LightningAnchorBlockEntity> LIGHTNING_ANCHOR_BLOCK_ENTITY;
    public static BlockEntityType<RoseQuartzChimesBlockEntity> ROSE_QUARTZ_CHIMES_BLOCK_ENTITY;
    public static BlockEntityType<ReceiverBlockEntity> RECEIVER_BLOCK_ENTITY;


    // Block Entity Registry
    public static void registerBlockEntities() {
        SPECTACLE_CANDLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.MODID, "spectacle_candle"), FabricBlockEntityTypeBuilder.create(SpectacleCandleBlockEntity::new, SPECTACLE_CANDLE).build(null));
        LIGHTNING_ANCHOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.MODID, "lightning_anchor"), FabricBlockEntityTypeBuilder.create(LightningAnchorBlockEntity::new, LIGHTNING_ANCHOR).build(null));
        ROSE_QUARTZ_CHIMES_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.MODID, "rose_quartz_chimes"), FabricBlockEntityTypeBuilder.create(RoseQuartzChimesBlockEntity::new, ROSE_QUARTZ_CHIMES).build(null));
        RECEIVER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.MODID, "redstone_receiver"),FabricBlockEntityTypeBuilder.create(ReceiverBlockEntity::new, REDSTONE_RECEIVER, EXPOSED_REDSTONE_RECEIVER, WEATHERED_REDSTONE_RECEIVER, OXIDIZED_REDSTONE_RECEIVER, WAXED_REDSTONE_RECEIVER, WAXED_EXPOSED_REDSTONE_RECEIVER, WAXED_WEATHERED_REDSTONE_RECEIVER, WAXED_OXIDIZED_REDSTONE_RECEIVER).build(null));
    }

    // Pairs
    public static void registerOxidizablePairs() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(REDSTONE_RECEIVER, EXPOSED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_REDSTONE_RECEIVER, WEATHERED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_REDSTONE_RECEIVER, OXIDIZED_REDSTONE_RECEIVER);

        OxidizableBlocksRegistry.registerWaxableBlockPair(REDSTONE_RECEIVER, WAXED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_REDSTONE_RECEIVER, WAXED_EXPOSED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_REDSTONE_RECEIVER, WAXED_WEATHERED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_REDSTONE_RECEIVER, WAXED_OXIDIZED_REDSTONE_RECEIVER);
    }
}
