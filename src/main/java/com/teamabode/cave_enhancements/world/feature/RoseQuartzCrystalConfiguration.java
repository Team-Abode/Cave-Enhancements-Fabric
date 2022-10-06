package com.teamabode.cave_enhancements.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class RoseQuartzCrystalConfiguration implements FeatureConfiguration {

    public static final Codec<RoseQuartzCrystalConfiguration> CODEC = RecordCodecBuilder.create(codec -> {
        return codec.group(RoseQuartzCrystalFeature.RoseQuartzCrystalFormation.CODEC.fieldOf("crystal_formation").forGetter( (instance) -> {
            return instance.formation;
        })).apply(codec, RoseQuartzCrystalConfiguration::new);
    });

    public final RoseQuartzCrystalFeature.RoseQuartzCrystalFormation formation;

    public RoseQuartzCrystalConfiguration(RoseQuartzCrystalFeature.RoseQuartzCrystalFormation formation) {
        this.formation = formation;
    }

}
