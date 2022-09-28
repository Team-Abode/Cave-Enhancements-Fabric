package com.teamabode.cave_enhancements.world.feature;

import com.mojang.serialization.Codec;
import com.teamabode.cave_enhancements.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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

        placePillar(4, 4, pos, level);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            placePillar(3, 3,  pos.relative(direction), level);
            placePillar(1, 1,  pos.relative(direction).relative(direction.getClockWise()), level);
        }

        return true;
    }

    private void placePillar(int baseValue, int randomValue, BlockPos pos, LevelAccessor level) {
        RandomSource random = level.getRandom();
        int height = random.nextInt(randomValue) + baseValue;

        for (int i = 1; i < 3; i++) {
            if (level.getBlockState(pos.below(i)).is(Blocks.WATER)) return;
        }


        for (int i = -3; i < height; i++) {
            BlockPos abovePos = pos.above(i);

            if (level.getBlockState(abovePos.above(1)).isAir()) {
                break;
            }

            if (getBlockState(level, abovePos, BlockTags.BASE_STONE_OVERWORLD)) {
                level.setBlock(abovePos, ModBlocks.ROSE_QUARTZ_BLOCK.defaultBlockState(), 3);
            }

        }
        if (getBlockState(level, pos.above(height), BlockTags.BASE_STONE_OVERWORLD) && random.nextFloat() < 0.1 && level.getBlockState(pos.above(height).below()).is(ModBlocks.ROSE_QUARTZ_BLOCK)) {
            level.setBlock(pos.above(height), ModBlocks.JAGGED_ROSE_QUARTZ.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), 3);
        }
    }

    private static boolean getBlockState(LevelAccessor level, BlockPos pos, TagKey<Block> blockTag) {
        return level.getBlockState(pos).is(blockTag) || level.getBlockState(pos).getMaterial().isLiquid();
    }
}
