package com.teamabode.cave_enhancements.world.feature;

import com.mojang.serialization.Codec;
import com.teamabode.cave_enhancements.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RoseQuartzCrystalFeature extends Feature<NoneFeatureConfiguration> {

    public RoseQuartzCrystalFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();

        if (level.getBlockState(pos.below()).isAir()) return false;

        placePillar(5, 3, pos, level);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            placePillar(3, 2,  pos.relative(direction), level);
            placePillar(1, 1,  pos.relative(direction).relative(direction.getClockWise()), level);
        }

        return true;
    }

    public void placePillar(int baseValue, int randomValue, BlockPos pos, LevelAccessor level) {
        RandomSource random = level.getRandom();
        for (int i = 0; i < random.nextInt(randomValue) + baseValue; i++) {
            if (level.getBlockState(pos.above(i)).getMaterial().isReplaceable()) {
                level.setBlock(pos.above(i), ModBlocks.ROSE_QUARTZ_BLOCK.defaultBlockState(), 3);
            }
        }
    }
}
