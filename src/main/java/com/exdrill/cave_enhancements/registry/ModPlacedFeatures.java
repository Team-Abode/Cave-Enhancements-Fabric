package com.exdrill.cave_enhancements.registry;

public class ModPlacedFeatures {


    /*
    public static final RegistryEntry<PlacedFeature> ORE_GOOP;
    public static final RegistryEntry<PlacedFeature> DRIPPING_GOOP;
    public static final RegistryEntry<PlacedFeature> GOOP_PATCH;
    public static final RegistryEntry<PlacedFeature> GOOP_SPLAT;


    static {
        ORE_GOOP = PlacedFeatures.register(
                "ore_goop",
                OreConfiguredFeatures.ORE_MAGMA,
                modifiersWithCount(
                        46,
                        HeightRangePlacementModifier.uniform(
                                YOffset.fixed(0),
                                YOffset.fixed(256)
                        )
                )
        );
        DRIPPING_GOOP = PlacedFeatures.register(
                "dripping_goop",
                GoopCavesFeatures.DRIPPING_GOOP,
                CountPlacementModifier.of(188),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(256)),
                EnvironmentScanPlacementModifier.of(
                        Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN),
                        BlockPredicate.IS_AIR_OR_WATER, 12),
                RandomOffsetPlacementModifier.vertically(
                        ConstantIntProvider.create(-1)
                ),
                BiomePlacementModifier.of()
        );
        GOOP_PATCH = PlacedFeatures.register(
                "goop_patch",
                GoopCavesFeatures.GOOP_PATCH,
                CountPlacementModifier.of(110),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(256)),
                EnvironmentScanPlacementModifier.of(
                        Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN),
                        BlockPredicate.IS_AIR_OR_WATER, 12),
                RandomOffsetPlacementModifier.vertically(
                        ConstantIntProvider.create(-1)
                ),
                BiomePlacementModifier.of()
        );
        GOOP_SPLAT = PlacedFeatures.register(
                "goop_splat",
                GoopCavesFeatures.GOOP_SPLAT,
                CountPlacementModifier.of(
                        UniformIntProvider.create(204, 250)
                ),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(256)),
                BiomePlacementModifier.of()
        );
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }


    public static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, List<PlacementModifier> modifiers) {
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, new Identifier(CaveEnhancements.MODID, id), new PlacedFeature(RegistryEntry.upcast(registryEntry), List.copyOf(modifiers)));
    }

    public static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, PlacementModifier... modifiers) {
        return register(new Identifier(CaveEnhancements.MODID, id).toString(), registryEntry, List.of(modifiers));
    }

     */


}
