package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.block.*;
import com.exdrill.cave_enhancements.block.entity.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.ToIntFunction;

public class ModBlocks {

    public static void register() {
        CaveEnhancements.LOGGER.debug("BLOCKS BEING REGISTERED!!!");
        System.out.println("BLOCKS BEING REGISTERED!!!");
    }

    public static final Block GOOP_BLOCK = register("goop_block", new Block(FabricBlockSettings.of(Material.CLAY).speedFactor(0.3F).strength(0.5F, 1.0F).sound(ModSounds.GOOP_BLOCK).jumpFactor(0.9F)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block GOOP_TRAP = register("goop_trap", new GoopTrapBlock(FabricBlockSettings.of(Material.VEGETABLE).strength(2.0F, 5.0F).sound(ModSounds.GOOP_BLOCK).speedFactor( 0.01F).jumpFactor(0.3F).friction(0.8F).color(MaterialColor.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block GOOP_SPLAT = registerWithAliasedItem("goop_splat", "goop", new SplatBlock(FabricBlockSettings.of(Material.CLAY).instabreak().sound(ModSounds.GOOP_DECORATION).noCollission().noOcclusion().color(MaterialColor.SAND)), CreativeModeTab.TAB_MATERIALS);
    public static final Block DRIPPING_GOOP = register("dripping_goop", new DrippingGoopBlock(FabricBlockSettings.of(Material.CLAY).sound(ModSounds.GOOP_DECORATION).noOcclusion().noCollission().lightLevel((state) -> 2).color(MaterialColor.SAND)), CreativeModeTab.TAB_DECORATIONS);
    public static final Block VOLATILE_GOOP = register("volatile_goop", new VolatileGoopBlock(FabricBlockSettings.of(Material.CLAY).strength(2.0F, 5.0F).sound(ModSounds.GOOP_BLOCK).color(MaterialColor.SAND)), CreativeModeTab.TAB_DECORATIONS);

    public static final Block GLOW_SPLOTCH = registerWithNoItem("glow_splotch", new SplatBlock(FabricBlockSettings.of(Material.CLAY).sound(SoundType.HONEY_BLOCK).noOcclusion().noCollission().lightLevel((state) -> 8).color(MaterialColor.SAND)));
    public static final Block SPECTACLE_CANDLE = register("spectacle_candle", new SpectacleCandleBlock(FabricBlockSettings.of(Material.DECORATION).sound(SoundType.CANDLE).lightLevel(CandleBlock.LIGHT_EMISSION).strength(0.1F, 0F)), CreativeModeTab.TAB_DECORATIONS);
    public static final Block SPECTACLE_CANDLE_CAKE = registerWithNoItem("spectacle_candle_cake", new SpectacleCandleCakeBlock(ModBlocks.SPECTACLE_CANDLE, FabricBlockSettings.of(Material.CAKE).sound(SoundType.CANDLE).lightLevel(litBlockEmission(3)).strength(0.5F, 0F)));

    public static final Block LIGHTNING_ANCHOR = register("lightning_anchor", new LightningAnchorBlock((FabricBlockSettings) FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresCorrectToolForDrops().color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER)), CreativeModeTab.TAB_REDSTONE);
    public static final Block CHARGED_LIGHTNING_ANCHOR = register("charged_lightning_anchor", new ChargedLightningAnchorBlock((FabricBlockSettings) FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresCorrectToolForDrops().color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER).lightLevel((state) -> 15)), CreativeModeTab.TAB_REDSTONE);

    public static final Block ROSE_QUARTZ_BLOCK = register("rose_quartz_block", new Block(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block JAGGED_ROSE_QUARTZ = register("jagged_rose_quartz", new JaggedRoseQuartzBlock(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_DECORATIONS);

    public static final Block POLISHED_ROSE_QUARTZ = register("polished_rose_quartz", new Block(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block POLISHED_ROSE_QUARTZ_SLAB = register("polished_rose_quartz_slab", new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block POLISHED_ROSE_QUARTZ_STAIRS = register("polished_rose_quartz_stairs", new StairBlock(POLISHED_ROSE_QUARTZ.defaultBlockState(), FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block POLISHED_ROSE_QUARTZ_WALL = register("polished_rose_quartz_wall", new WallBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final Block ROSE_QUARTZ_TILES = register("rose_quartz_tiles", new Block(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block ROSE_QUARTZ_TILE_SLAB = register("rose_quartz_tile_slab", new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block ROSE_QUARTZ_TILE_STAIRS = register("rose_quartz_tile_stairs", new StairBlock(ROSE_QUARTZ_TILES.defaultBlockState(), FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block ROSE_QUARTZ_TILE_WALL = register("rose_quartz_tile_wall", new WallBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block ROSE_QUARTZ_CHIMES = register("rose_quartz_chimes", new RoseQuartzChimesBlock((FabricBlockSettings) FabricBlockSettings.of(Material.STONE).strength(2F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.CALCITE).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final Block ROSE_QUARTZ_LAMP = register("rose_quartz_lamp", new RoseQuartzLampBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.LANTERN).lightLevel((state) -> 15)), CreativeModeTab.TAB_DECORATIONS);
    public static final Block SOUL_ROSE_QUARTZ_LAMP = register("soul_rose_quartz_lamp", new RoseQuartzLampBlock(FabricBlockSettings.of(Material.STONE).strength(1F, 10).requiresCorrectToolForDrops().color(MaterialColor.COLOR_PINK).sound(SoundType.LANTERN).lightLevel((state) -> 15)), CreativeModeTab.TAB_DECORATIONS);

    public static final Block REDSTONE_RECEIVER = register("redstone_receiver", new OxidizableReceiverBlock(WeatheringCopper.WeatherState.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);
    public static final Block EXPOSED_REDSTONE_RECEIVER = register("exposed_redstone_receiver", new OxidizableReceiverBlock(WeatheringCopper.WeatherState.EXPOSED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);
    public static final Block WEATHERED_REDSTONE_RECEIVER = register("weathered_redstone_receiver", new OxidizableReceiverBlock(WeatheringCopper.WeatherState.WEATHERED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_STEM).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);
    public static final Block OXIDIZED_REDSTONE_RECEIVER = register("oxidized_redstone_receiver", new OxidizableReceiverBlock(WeatheringCopper.WeatherState.OXIDIZED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_NYLIUM).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);

    public static final Block WAXED_REDSTONE_RECEIVER = register("waxed_redstone_receiver", new ReceiverBlock(WeatheringCopper.WeatherState.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.COLOR_ORANGE).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);
    public static final Block WAXED_EXPOSED_REDSTONE_RECEIVER = register("waxed_exposed_redstone_receiver", new ReceiverBlock(WeatheringCopper.WeatherState.EXPOSED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);
    public static final Block WAXED_WEATHERED_REDSTONE_RECEIVER = register("waxed_weathered_redstone_receiver", new ReceiverBlock(WeatheringCopper.WeatherState.WEATHERED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_STEM).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);
    public static final Block WAXED_OXIDIZED_REDSTONE_RECEIVER = register("waxed_oxidized_redstone_receiver", new ReceiverBlock(WeatheringCopper.WeatherState.OXIDIZED, FabricBlockSettings.of(Material.DECORATION).color(MaterialColor.WARPED_NYLIUM).sound(SoundType.COPPER).instabreak()), CreativeModeTab.TAB_REDSTONE);

    public static Block register(String id, Block block, CreativeModeTab tab) {
        registerBlockItem(id, block, tab);
        return Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, id), block);
    }

    public static Block registerWithAliasedItem(String blockID, String itemID, Block block, CreativeModeTab tab) {
        registerBlockItem(itemID, block, tab);
        return Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, blockID), block);
    }

    public static Item registerBlockItem(String id, Block block, CreativeModeTab tab) {
        return Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, id), new BlockItem(block, new Item.Properties().tab(tab)));
    }

    public static Block registerWithNoItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, id), block);
    }

    private static ToIntFunction<BlockState> litBlockEmission(int i) {
        return (blockState) -> (Boolean)blockState.getValue(BlockStateProperties.LIT) ? i : 0;
    }
}
