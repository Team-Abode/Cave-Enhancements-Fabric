package com.teamabode.cave_enhancements.core.integration.terrablender;

import com.teamabode.cave_enhancements.CaveEnhancements;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class TerrablenderIntegration implements TerraBlenderApi {

    public static final ResourceLocation REGION = new ResourceLocation(CaveEnhancements.MODID, "region");


    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new CaveEnhancementsRegion(REGION, RegionType.OVERWORLD, 1));
    }
}
