package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.block.*;
import com.exdrill.cave_enhancements.block.entity.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.ToIntFunction;

public class ModBlocks {
    
    // Block Components
    public static final Block GOOP_BLOCK = new Block(FabricBlockSettings.of(Material.CLAY).speedFactor(0.3F).strength(0.5F, 1.0F).sound(ModSounds.GOOP_BLOCK).jumpFactor(0.9F));
    public static final GoopTrapBlock GOOP_TRAP = new GoopTrapBlock(FabricBlockSettings.of(Material.VEGETABLE).strength(2.0F, 5.0F).sound(ModSounds.GOOP_BLOCK).speedFactor( 0.01F).jumpFactor(0.3F).friction(0.8F).color(MaterialColor.SAND));
    public static final SplatBlock GOOP_SPLAT = new SplatBlock(FabricBlockSettings.of(Material.CLAY).instabreak().sound(ModSounds.GOOP_DECORATION).noCollission().noOcclusion().color(MaterialColor.SAND));
    public static final DrippingGoopBlock DRIPPING_GOOP = new DrippingGoopBlock(FabricBlockSettings.of(Material.CLAY).sound(ModSounds.GOOP_DECORATION).noOcclusion().noCollission().lightLevel((state) -> 2).color(MaterialColor.SAND));

    public static final SplatBlock GLOW_SPLOTCH = new SplatBlock(FabricBlockSettings.of(Material.CLAY).sound(SoundType.HONEY_BLOCK).noOcclusion().noCollission().lightLevel((state) -> 8).color(MaterialColor.SAND));
    public static final SpectacleCandleBlock SPECTACLE_CANDLE = new SpectacleCandleBlock(FabricBlockSettings.of(Material.DECORATION).sound(SoundType.CANDLE).lightLevel(CandleBlock.LIGHT_EMISSION).strength(0.1F, 0F));
    public static final SpectacleCandleCakeBlock SPECTACLE_CANDLE_CAKE = new SpectacleCandleCakeBlock(ModBlocks.SPECTACLE_CANDLE, FabricBlockSettings.of(Material.CAKE).sound(SoundType.CANDLE).lightLevel(litBlockEmission(3)).strength(0.5F, 0F));

    public static final Block LIGHTNING_ANCHOR = new LightningAnchorBlock((FabricBlockSettings) FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresCorrectToolForDrops().color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER));
    public static final Block CHARGED_LIGHTNING_ANCHOR = new ChargedLightningAnchorBlock((FabricBlockSettings) FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresCorrectToolForDrops().color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER).lightLevel((state) -> 15));


    public static final Block ROSE_QUARTZ_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final JaggedRoseQuartzBlock JAGGED_ROSE_QUARTZ = new JaggedRoseQuartzBlock(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final Block POLISHED_ROSE_QUARTZ = new Block(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final SlabBlock POLISHED_ROSE_QUARTZ_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final StairBlock POLISHED_ROSE_QUARTZ_STAIRS = new StairBlock(POLISHED_ROSE_QUARTZ.defaultBlockState(), FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ).noOcclusion());
    public static final WallBlock POLISHED_ROSE_QUARTZ_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final Block ROSE_QUARTZ_TILES = new Block(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final SlabBlock ROSE_QUARTZ_TILE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final StairBlock ROSE_QUARTZ_TILE_STAIRS = new StairBlock(ROSE_QUARTZ_TILES.defaultBlockState(), FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final WallBlock ROSE_QUARTZ_TILE_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ));
    public static final Block ROSE_QUARTZ_CHIMES = new RoseQuartzChimesBlock((FabricBlockSettings) FabricBlockSettings.of(Material.STONE).strength(2F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(ModSounds.ROSE_QUARTZ).noOcclusion());
    public static final RoseQuartzLampBlock ROSE_QUARTZ_LAMP = new RoseQuartzLampBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.LANTERN).lightLevel((state) -> 15));
    public static final RoseQuartzLampBlock SOUL_ROSE_QUARTZ_LAMP = new RoseQuartzLampBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.LANTERN).lightLevel((state) -> 15));

    public static final OxidizableReceiverBlock REDSTONE_RECEIVER = new OxidizableReceiverBlock(WeatheringCopper.WeatherState.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER).instabreak());
    public static final OxidizableReceiverBlock EXPOSED_REDSTONE_RECEIVER = new OxidizableReceiverBlock(WeatheringCopper.WeatherState.EXPOSED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.COPPER).instabreak());
    public static final OxidizableReceiverBlock WEATHERED_REDSTONE_RECEIVER = new OxidizableReceiverBlock(WeatheringCopper.WeatherState.WEATHERED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_STEM).sound(SoundType.COPPER).instabreak());
    public static final OxidizableReceiverBlock OXIDIZED_REDSTONE_RECEIVER = new OxidizableReceiverBlock(WeatheringCopper.WeatherState.OXIDIZED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_NYLIUM).sound(SoundType.COPPER).instabreak());

    public static final ReceiverBlock WAXED_REDSTONE_RECEIVER = new ReceiverBlock(WeatheringCopper.WeatherState.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER).instabreak());
    public static final ReceiverBlock WAXED_EXPOSED_REDSTONE_RECEIVER = new ReceiverBlock(WeatheringCopper.WeatherState.EXPOSED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.COPPER).instabreak());
    public static final ReceiverBlock WAXED_WEATHERED_REDSTONE_RECEIVER = new ReceiverBlock(WeatheringCopper.WeatherState.WEATHERED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_STEM).sound(SoundType.COPPER).instabreak());
    public static final ReceiverBlock WAXED_OXIDIZED_REDSTONE_RECEIVER = new ReceiverBlock(WeatheringCopper.WeatherState.OXIDIZED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_NYLIUM).sound(SoundType.COPPER).instabreak());

    // Block Registry
    public static void register() {
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "goop_block"), GOOP_BLOCK);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "goop_splat"), GOOP_SPLAT);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "goop_trap"), GOOP_TRAP);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "dripping_goop"), DRIPPING_GOOP);

        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "glow_splotch"), GLOW_SPLOTCH);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "spectacle_candle"), SPECTACLE_CANDLE);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "spectacle_candle_cake"), SPECTACLE_CANDLE_CAKE);

        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "lightning_anchor"), LIGHTNING_ANCHOR);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "charged_lightning_anchor"), CHARGED_LIGHTNING_ANCHOR);

        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_block"), ROSE_QUARTZ_BLOCK);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "jagged_rose_quartz"), JAGGED_ROSE_QUARTZ);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz"), POLISHED_ROSE_QUARTZ);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz_slab"), POLISHED_ROSE_QUARTZ_SLAB);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz_stairs"), POLISHED_ROSE_QUARTZ_STAIRS);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "polished_rose_quartz_wall"), POLISHED_ROSE_QUARTZ_WALL);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tiles"), ROSE_QUARTZ_TILES);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tile_slab"), ROSE_QUARTZ_TILE_SLAB);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tile_stairs"), ROSE_QUARTZ_TILE_STAIRS);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_tile_wall"), ROSE_QUARTZ_TILE_WALL);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_chimes"), ROSE_QUARTZ_CHIMES);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_lamp"), ROSE_QUARTZ_LAMP);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "soul_rose_quartz_lamp"), SOUL_ROSE_QUARTZ_LAMP);

        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "redstone_receiver"), REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "exposed_redstone_receiver"), EXPOSED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "weathered_redstone_receiver"), WEATHERED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "oxidized_redstone_receiver"), OXIDIZED_REDSTONE_RECEIVER);

        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "waxed_redstone_receiver"), WAXED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "waxed_exposed_redstone_receiver"), WAXED_EXPOSED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "waxed_weathered_redstone_receiver"), WAXED_WEATHERED_REDSTONE_RECEIVER);
        Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "waxed_oxidized_redstone_receiver"), WAXED_OXIDIZED_REDSTONE_RECEIVER);
    }

    // Block Render Type
    public static void registerClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(GOOP_SPLAT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DRIPPING_GOOP, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GLOW_SPLOTCH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SPECTACLE_CANDLE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JAGGED_ROSE_QUARTZ, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EXPOSED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WEATHERED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OXIDIZED_REDSTONE_RECEIVER, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_EXPOSED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_WEATHERED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WAXED_OXIDIZED_REDSTONE_RECEIVER, RenderType.cutout());
    }

    private static ToIntFunction<BlockState> litBlockEmission(int i) {
        return (blockState) -> (Boolean)blockState.getValue(BlockStateProperties.LIT) ? i : 0;
    }
}
