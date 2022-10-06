package com.teamabode.cave_enhancements.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class RoseQuartzCrystalFeature extends Feature<RoseQuartzCrystalConfiguration> {

    public RoseQuartzCrystalFeature() {
        super(RoseQuartzCrystalConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<RoseQuartzCrystalConfiguration> context) {

        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        RoseQuartzCrystalFormation formation = context.config().formation;

        Direction direction = Direction.values()[Mth.nextInt(random, 0, Direction.values().length)];

        return true;
    }

    private void placeCrystals(RoseQuartzCrystalFormation formation, BlockPos pos, WorldGenLevel level, int length) {

        switch (formation) {
            case DIAGONAL_UP -> {

            }
            case DIAGONAL_DOWN -> {

            }
            case VERTICAL_UP -> {

            }
            case VERTICAL_DOWN -> {

            }
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
