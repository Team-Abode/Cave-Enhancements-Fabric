package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.entity.Cruncher;
import com.exdrill.cave_enhancements.entity.DripstoneTortoise;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModSpawnPlacements {

    public static void register() {
        SpawnRestrictionAccessor.callRegister(ModEntities.DRIPSTONE_TORTOISE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, DripstoneTortoise::checkDripstoneTortoiseSpawnRules);
        SpawnRestrictionAccessor.callRegister(ModEntities.CRUNCHER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Cruncher::checkCruncherSpawnRules);
    }
}
