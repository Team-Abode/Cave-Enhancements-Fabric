package com.teamabode.cave_enhancements.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.HarmonicArrow;
import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstonePike;
import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import com.teamabode.cave_enhancements.entity.goop.BigGoopDripProjectile;
import com.teamabode.cave_enhancements.entity.goop.Goop;
import com.teamabode.cave_enhancements.entity.goop.ThrownGoop;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static final EntityType<Goop> GOOP = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "goop"), FabricEntityTypeBuilder.create(MobCategory.AMBIENT, Goop::new).dimensions(EntityDimensions.fixed(0.6f, 0.9f)).build());

    public static final EntityType<Cruncher> CRUNCHER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "cruncher"), FabricEntityTypeBuilder.create(MobCategory.CREATURE, Cruncher::new).dimensions(EntityDimensions.scalable(0.8f, 0.8f)).build());

    public static final EntityType<DripstoneTortoise> DRIPSTONE_TORTOISE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "dripstone_tortoise"), FabricEntityTypeBuilder.create(MobCategory.CREATURE, DripstoneTortoise::new).dimensions(EntityDimensions.scalable(1.3F, 0.8F)).build());

    public static final EntityType<BigGoopDripProjectile> BIG_GOOP_DRIP = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "big_goop_drip"), FabricEntityTypeBuilder.<BigGoopDripProjectile>create(MobCategory.MISC, BigGoopDripProjectile::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackedUpdateRate(10).trackRangeBlocks(4).build());

    public static final EntityType<DripstonePike> DRIPSTONE_PIKE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "dripstone_pike"), FabricEntityTypeBuilder.<DripstonePike>create(MobCategory.MISC, DripstonePike::new).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());

    public static final EntityType<HarmonicArrow> HARMONIC_ARROW = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "harmonic_arrow"), FabricEntityTypeBuilder.<HarmonicArrow>create(MobCategory.MISC, HarmonicArrow::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());

    public static final EntityType<ThrownGoop> THROWN_GOOP = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "thrown_goop"), FabricEntityTypeBuilder.<ThrownGoop>create(MobCategory.MISC, ThrownGoop::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackedUpdateRate(10).trackRangeBlocks(4).trackRangeChunks(4).build());

    public static void init() {
        FabricDefaultAttributeRegistry.register(GOOP, Goop.createGoopAttributes());
        FabricDefaultAttributeRegistry.register(CRUNCHER, Cruncher.createCruncherAttributes());
        FabricDefaultAttributeRegistry.register(DRIPSTONE_TORTOISE, DripstoneTortoise.createDripstoneTortoiseAttributes());
    }
}
