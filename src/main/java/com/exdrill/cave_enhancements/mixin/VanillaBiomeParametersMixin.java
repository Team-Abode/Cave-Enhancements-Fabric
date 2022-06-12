package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;

@Mixin(OverworldBiomeBuilder.class)
public class VanillaBiomeParametersMixin {

    @Shadow @Final private Climate.Parameter deepOceanContinentalness;

    @Final
    private final Climate.Parameter defaultParameter = Climate.Parameter.span(-1.0F, 1.0F);

    public VanillaBiomeParametersMixin() {
    }


    @Inject(method="addUndergroundBiomes", at = @At("TAIL"))
    private void writeCaveBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, CallbackInfo ci) {
        this.writeCaveBiomeParameters(parameters, this.defaultParameter, this.defaultParameter, Climate.Parameter.span(0.8F, 1.0F), this.defaultParameter, this.defaultParameter, 0.0F, Biomes.DRIPSTONE_CAVES);
        this.writeCaveBiomeParameters(parameters, this.defaultParameter, Climate.Parameter.span(0.7F, 1.0F), this.defaultParameter, this.defaultParameter, this.defaultParameter, 0.0F, Biomes.LUSH_CAVES);

        // Rose Quartz Caves
        this.writeCaveBiomeParameters(parameters, Climate.Parameter.span(0.7F, 1F), this.defaultParameter, this.deepOceanContinentalness, Climate.Parameter.span(-0.5F, 0.5F), Climate.Parameter.span(-0.75F, 0.4F), 0.0F, ModBiomes.ROSE_QUARTZ_CAVES);

        // Goop Caves
        this.writeCaveBiomeParameters(parameters, Climate.Parameter.span(-1F, 0.215F), Climate.Parameter.span(-0.4F, 0.5F), Climate.Parameter.span(0.32F, 0.72F), Climate.Parameter.span(0.4F, 0.7F), Climate.Parameter.span(0.8F, 0.875F), 0.0F, ModBiomes.GOOP_CAVES);
    }

    private void writeCaveBiomeParameters(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome) {}
}