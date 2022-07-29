package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {

    @Shadow protected abstract void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter parameter, Climate.Parameter parameter2, Climate.Parameter parameter3, Climate.Parameter parameter4, Climate.Parameter parameter5, float f, ResourceKey<Biome> resourceKey);

    @Shadow @Final private Climate.Parameter deepOceanContinentalness;

    @Final
    private final Climate.Parameter defaultParameter = Climate.Parameter.span(-1.0F, 1.0F);

    public OverworldBiomeBuilderMixin() {
    }


    @Inject(method="addUndergroundBiomes", at = @At("TAIL"))
    private void writeCaveBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, CallbackInfo ci) {

        // Rose Quartz Caves
        this.addUndergroundBiome(parameters, Climate.Parameter.span(0.7F, 1F), this.defaultParameter, this.deepOceanContinentalness, Climate.Parameter.span(-0.5F, 0.5F), Climate.Parameter.span(-0.75F, 0.4F), 0.0F, ModBiomes.ROSE_QUARTZ_CAVES);
        
        // Goop Caves
        this.addUndergroundBiome(parameters, Climate.Parameter.span(-1F, 0.2F), Climate.Parameter.span(-0.4F, 0.5F), Climate.Parameter.span(0.32F, 0.72F), Climate.Parameter.span(0.4F, 0.7F), Climate.Parameter.span(0.8F, 0.875F), 0.0F, ModBiomes.GOOP_CAVES);
    }
}