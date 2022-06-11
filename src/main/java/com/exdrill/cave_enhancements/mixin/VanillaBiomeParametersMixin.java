package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public class VanillaBiomeParametersMixin {

    @Shadow @Final private MultiNoiseUtil.ParameterRange deepOceanContinentalness;

    @Final
    @Shadow private final MultiNoiseUtil.ParameterRange defaultParameter = MultiNoiseUtil.ParameterRange.of(-1.0F, 1.0F);

    public VanillaBiomeParametersMixin() {
    }


    @Inject(method="writeCaveBiomes", at = @At("TAIL"))
    private void writeCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
        this.writeCaveBiomeParameters(parameters, this.defaultParameter, this.defaultParameter, MultiNoiseUtil.ParameterRange.of(0.8F, 1.0F), this.defaultParameter, this.defaultParameter, 0.0F, BiomeKeys.DRIPSTONE_CAVES);
        this.writeCaveBiomeParameters(parameters, this.defaultParameter, MultiNoiseUtil.ParameterRange.of(0.7F, 1.0F), this.defaultParameter, this.defaultParameter, this.defaultParameter, 0.0F, BiomeKeys.LUSH_CAVES);

        // Rose Quartz Caves
        this.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(0.7F, 1F), this.defaultParameter, this.deepOceanContinentalness, MultiNoiseUtil.ParameterRange.of(-0.5F, 0.5F), MultiNoiseUtil.ParameterRange.of(-0.75F, 0.4F), 0.0F, ModBiomes.ROSE_QUARTZ_CAVES);

        // Goop Caves
        this.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(-1F, 0.215F), MultiNoiseUtil.ParameterRange.of(-0.4F, 0.5F), MultiNoiseUtil.ParameterRange.of(0.32F, 0.72F), MultiNoiseUtil.ParameterRange.of(0.4F, 0.7F), MultiNoiseUtil.ParameterRange.of(0.8F, 0.875F), 0.0F, ModBiomes.GOOP_CAVES);
    }

    @Shadow
    private void writeCaveBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {}
}