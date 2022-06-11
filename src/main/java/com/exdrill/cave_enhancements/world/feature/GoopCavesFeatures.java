package com.exdrill.cave_enhancements.world.feature;

public class GoopCavesFeatures {

    /*
    public static final RuleTest BASE_STONE_OVERWORLD = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);

    private static final SimpleBlockStateProvider DRIPPING_GOOP_PLACEMENT = SimpleBlockStateProvider.of(
            ModBlocks.DRIPPING_GOOP.getDefaultState().with(DrippingGoopBlock.HANGING, false)
    );

    private static final SimpleBlockStateProvider DRIPPING_GOOP_HANGING_PLACEMENT = SimpleBlockStateProvider.of(
            ModBlocks.DRIPPING_GOOP.getDefaultState().with(DrippingGoopBlock.HANGING, true)
    );

    private static final SimpleBlockStateProvider GOOP_BLOCK = SimpleBlockStateProvider.of(
            ModBlocks.GOOP_BLOCK.getDefaultState()
    );
    private static final MultifaceGrowthBlock GOOP_SPLAT_BLOCK;

    // Define the configured features
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_GOOP;
    public static final RegistryEntry<ConfiguredFeature<BlockColumnFeatureConfig, ?>> DRIPPING_GOOP;
    public static final RegistryEntry<ConfiguredFeature<SimpleBlockFeatureConfig, ?>> GOOP_BLOCK_SINGLE;
    public static final RegistryEntry<ConfiguredFeature<VegetationPatchFeatureConfig, ?>> GOOP_PATCH;
    public static final RegistryEntry<ConfiguredFeature<MultifaceGrowthFeatureConfig, ?>> GOOP_SPLAT;




    static {
        // Goop Ore Base
        ORE_GOOP = register("ore_goop", Feature.ORE, new OreFeatureConfig(BASE_STONE_OVERWORLD, ModBlocks.GOOP_BLOCK.getDefaultState(), 64));
        // Dripping Goop
        DRIPPING_GOOP = register("dripping_goop", Feature.BLOCK_COLUMN, new BlockColumnFeatureConfig(
                List.of(
                        BlockColumnFeatureConfig.createLayer(
                                new WeightedListIntProvider(
                                        DataPool.<IntProvider>builder()
                                                .add(UniformIntProvider.create(0, 12), 2)
                                                .add(UniformIntProvider.create(0, 8), 2)
                                                .add(UniformIntProvider.create(0, 5), 3)
                                                .add(UniformIntProvider.create(0, 4), 10)
                                                .build()
                                ),
                                DRIPPING_GOOP_PLACEMENT),

                        BlockColumnFeatureConfig.createLayer(
                                ConstantIntProvider.create(1),
                                DRIPPING_GOOP_HANGING_PLACEMENT)),

                Direction.DOWN,
                BlockPredicate.matchingBlocks(
                        List.of(
                                Blocks.AIR, Blocks.WATER
                        )
                ),
                true)
        );
        GOOP_BLOCK_SINGLE = register("goop_block_single", Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(GOOP_BLOCK));
        // Goop Patch Feature
        GOOP_PATCH = register(
                "goop_patch",
                Feature.VEGETATION_PATCH,
                new VegetationPatchFeatureConfig(
                        BlockTags.BASE_STONE_OVERWORLD,
                        BlockStateProvider.of(ModBlocks.GOOP_BLOCK),
                        PlacedFeatures.createEntry(GOOP_BLOCK_SINGLE),
                        VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1),
                        0.0F,
                        5,
                        0.05F,
                        UniformIntProvider.create(3, 5),
                        0.5F
                )
        );
        GOOP_SPLAT_BLOCK = ModBlocks.GOOP_SPLAT;
        GOOP_SPLAT = register(
                "goop_splat",
                Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig(
                        GOOP_SPLAT_BLOCK,
                        20,
                        true,
                        true,
                        true,
                        0.5F,
                        RegistryEntryList.of(
                                Block::getRegistryEntry,
                                Blocks.STONE,
                                Blocks.ANDESITE,
                                Blocks.DIORITE,
                                Blocks.GRANITE,
                                Blocks.DRIPSTONE_BLOCK,
                                Blocks.CALCITE, Blocks.TUFF,
                                Blocks.DEEPSLATE
                        )
                )
        );
    }

     */
}
