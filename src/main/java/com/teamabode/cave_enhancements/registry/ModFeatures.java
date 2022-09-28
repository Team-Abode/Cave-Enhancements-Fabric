package com.teamabode.cave_enhancements.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.block.DrippingGoopBlock;
import com.teamabode.cave_enhancements.world.feature.RoseQuartzCrystalFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Properties;

public class ModFeatures {

    public static final Feature<NoneFeatureConfiguration> ROSE_QUARTZ_CRYSTAL = new RoseQuartzCrystalFeature(NoneFeatureConfiguration.CODEC);

    public static void init() {
        Registry.register(Registry.FEATURE, new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_crystal"), ROSE_QUARTZ_CRYSTAL);
    }

    public static class ModConfiguredFeatures {

        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> NOTHING = new Holder.Direct<>(new ConfiguredFeature<>(Feature.NO_OP, new NoneFeatureConfiguration()));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_GOOP = register("ore_goop", new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD), ModBlocks.GOOP_BLOCK.defaultBlockState(), 64)));
        public static final Holder<ConfiguredFeature<MultifaceGrowthConfiguration, ?>> GOOP_SPLAT = register("goop_splat", new ConfiguredFeature<>(Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) ModBlocks.GOOP_SPLAT, 35, false, true, true, 0.9F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE))));
        public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> FLOOR_GOOP_PATCH = register("floor_goop_patch", new ConfiguredFeature<>(Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.BASE_STONE_OVERWORLD, BlockStateProvider.simple(ModBlocks.GOOP_BLOCK), PlacementUtils.inlinePlaced(NOTHING), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 2, 0.05F, UniformInt.of(8, 10), 0.5F)));
        public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> CEILING_GOOP_PATCH = register("ceiling_goop_patch", new ConfiguredFeature<>(Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.BASE_STONE_OVERWORLD, BlockStateProvider.simple(ModBlocks.GOOP_BLOCK), PlacementUtils.inlinePlaced(NOTHING), CaveSurface.CEILING, ConstantInt.of(1), 0.0F, 3, 0.05F, UniformInt.of(12, 14), 0.5F)));
        public static final Holder<ConfiguredFeature<BlockColumnConfiguration, ?>> DRIPPING_GOOP = register("dripping_goop", new ConfiguredFeature<>(Feature.BLOCK_COLUMN, new BlockColumnConfiguration( List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(6, 12), 1).add(UniformInt.of(2, 5), 1).add(UniformInt.of(8, 10), 2).build()), BlockStateProvider.simple(ModBlocks.DRIPPING_GOOP.defaultBlockState().setValue(DrippingGoopBlock.HANGING, false))), BlockColumnConfiguration.layer(ConstantInt.of(1), BlockStateProvider.simple(ModBlocks.DRIPPING_GOOP.defaultBlockState().setValue(DrippingGoopBlock.HANGING, true)))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, true)));

        //public static final Holder<ConfiguredFeature<BlockColumnConfiguration, ?>> ROSE_QUARTZ_CRYSTALS = register("rose_quartz_crystals", new ConfiguredFeature<>(Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(4), 1).add(ConstantInt.of(3), 3).add(ConstantInt.of(2), 2).build()), BlockStateProvider.simple(ModBlocks.ROSE_QUARTZ_BLOCK.defaultBlockState())), BlockColumnConfiguration.layer(ConstantInt.of(1), new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.ROSE_QUARTZ_BLOCK.defaultBlockState(), 9).add(ModBlocks.JAGGED_ROSE_QUARTZ.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), 1).build()))), Direction.UP, BlockPredicate.matchesBlocks(Blocks.WATER), false)));
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> ROSE_QUARTZ_CRYSTALS = register("rose_quartz_crystals", new ConfiguredFeature<>(ModFeatures.ROSE_QUARTZ_CRYSTAL, new NoneFeatureConfiguration()));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_CALCITE = register("ore_calcite", new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.CALCITE.defaultBlockState(), 64)));

        public static <FC extends FeatureConfiguration> Holder<ConfiguredFeature<FC, ?>> register(String name, ConfiguredFeature<FC, ?> feature) {
            return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(CaveEnhancements.MODID, name).toString(), feature);
        }
    }

    public static class ModPlacedFeatures {

        public static final Holder<PlacedFeature> ORE_GOOP = register("ore_goop", ModConfiguredFeatures.ORE_GOOP, List.of(
                CountPlacement.of(60),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        ));
        public static final Holder<PlacedFeature> GOOP_SPLAT = register("goop_splat", ModConfiguredFeatures.GOOP_SPLAT, List.of(
                CountPlacement.of(UniformInt.of(104, 157)),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                InSquarePlacement.spread(),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
                BiomeFilter.biome()
        ));
        public static final Holder<PlacedFeature> FLOOR_GOOP_PATCH = register("floor_goop_patch", ModConfiguredFeatures.FLOOR_GOOP_PATCH, List.of(
                CountPlacement.of(100),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        ));
        public static final Holder<PlacedFeature> CEILING_GOOP_PATCH = register("ceiling_goop_patch", ModConfiguredFeatures.CEILING_GOOP_PATCH, List.of(
                CountPlacement.of(200),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        ));
        public static final Holder<PlacedFeature> DRIPPING_GOOP = register("dripping_goop", ModConfiguredFeatures.DRIPPING_GOOP, List.of(
                CountPlacement.of(200),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        ));

        public static final Holder<PlacedFeature> ROSE_QUARTZ_CRYSTALS = register("rose_quartz_crystals", ModConfiguredFeatures.ROSE_QUARTZ_CRYSTALS, List.of(
                CountPlacement.of(188),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.matchesBlocks(Blocks.WATER), 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        ));

        public static final Holder<PlacedFeature> ORE_CALCITE = register("ore_calcite", ModConfiguredFeatures.ORE_CALCITE,
                commonOrePlacement(25, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.TOP))
        );

        private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
            return orePlacement(CountPlacement.of(count), heightRange);
        }

        private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
            return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
        }

        public static <FC extends FeatureConfiguration> Holder<PlacedFeature> register(String name, Holder<ConfiguredFeature<FC, ?>> feature, List<PlacementModifier> placementModifiers) {
            return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(CaveEnhancements.MODID, name), new PlacedFeature(Holder.hackyErase(feature), placementModifiers));
        }
    }
}
