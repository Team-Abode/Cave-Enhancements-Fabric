package com.teamabode.cave_enhancements.world.feature;

import com.mojang.serialization.Codec;
import com.teamabode.cave_enhancements.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RoseQuartzCrystalFeature extends Feature<NoneFeatureConfiguration> {

    public RoseQuartzCrystalFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();

        placeCrystals(RoseQuartzCrystalFormation.DIAGONAL_UP, pos, level, 3);

        return true;
    }

    private void placeCrystals(RoseQuartzCrystalFormation formation, BlockPos pos, WorldGenLevel level, int length) {

        switch (formation) {
            case DIAGONAL_UP -> drawDiagonalLine(level, length, pos, Direction.UP);
            case DIAGONAL_DOWN -> drawDiagonalLine(level, length, pos, Direction.DOWN);
            case VERTICAL_UP -> {

            }
            case VERTICAL_DOWN -> {

            }
        }
    }

    private void drawDiagonalLine(WorldGenLevel level, int length, BlockPos pos, Direction direction) {

        Direction randomDirection = Direction.values()[Mth.nextInt(level.getRandom(), 2, 5)];

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());

        for (int i = 0; i <= length ; i++) {

            placeRoseQuartzBlock(level, mutableBlockPos);
            mutableBlockPos.move(direction, i).move(randomDirection, i);

            BlockPos relativePos = pos.relative(direction, i).relative(randomDirection, i);

            for (Direction eachDirection : Direction.values()) {
                if (i != length) {
                    placeRoseQuartzBlock(level, relativePos.relative(eachDirection, 1));
                }
            }
        }
    }

    private void placeRoseQuartzBlock(WorldGenLevel level, BlockPos pos) {
        if (DripstoneUtils.isEmptyOrWater(level.getBlockState(pos))) {
            level.setBlock(pos, ModBlocks.ROSE_QUARTZ_BLOCK.defaultBlockState(), 3);
        }
    }

    public enum RoseQuartzCrystalFormation implements StringRepresentable {
        VERTICAL_UP("vertical_up"),
        VERTICAL_DOWN("vertical_down"),
        DIAGONAL_UP("diagonal_up"),
        DIAGONAL_DOWN("diagonal_down");

        private final String id;

        public static final Codec<RoseQuartzCrystalFormation> CODEC = StringRepresentable.fromEnum(RoseQuartzCrystalFormation::values);

        RoseQuartzCrystalFormation(String id) {
            this.id = id;
        }

        public String getSerializedName() {
            return null;
        }
    }
}
