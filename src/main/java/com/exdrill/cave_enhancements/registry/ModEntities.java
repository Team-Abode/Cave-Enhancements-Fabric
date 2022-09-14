package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.*;
import com.exdrill.cave_enhancements.entity.cruncher.Cruncher;
import com.exdrill.cave_enhancements.entity.dripstone_tortoise.DripstonePike;
import com.exdrill.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import com.exdrill.cave_enhancements.entity.goop.BigGoopDripProjectile;
import com.exdrill.cave_enhancements.entity.goop.Goop;
import com.exdrill.cave_enhancements.entity.goop.ThrownGoop;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;

public class ModEntities {

    public static final EntityType<Goop> GOOP = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "goop"), FabricEntityTypeBuilder.create(MobCategory.AMBIENT, Goop::new).dimensions(EntityDimensions.fixed(0.6f, 0.9f)).build());

    public static final EntityType<Cruncher> CRUNCHER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "cruncher"), FabricEntityTypeBuilder.create(MobCategory.CREATURE, Cruncher::new).dimensions(EntityDimensions.fixed(0.8f, 0.8f)).build());

    public static final EntityType<DripstoneTortoise> DRIPSTONE_TORTOISE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "dripstone_tortoise"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, DripstoneTortoise::new).dimensions(EntityDimensions.fixed(1.3F, 0.8F)).build());

    public static final EntityType<BigGoopDripProjectile> BIG_GOOP_DRIP_PROJECTILE_ENTITY = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "big_goop_drip"), FabricEntityTypeBuilder.<BigGoopDripProjectile>create(MobCategory.MISC, BigGoopDripProjectile::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).build());

    public static final EntityType<DripstonePike> DRIPSTONE_PIKE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "dripstone_pike"), FabricEntityTypeBuilder.create(MobCategory.MISC, DripstonePike::new).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());

    public static final EntityType<HarmonicArrow> HARMONIC_ARROW = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "harmonic_arrow"), FabricEntityTypeBuilder.<HarmonicArrow>create(MobCategory.MISC, HarmonicArrow::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());

    public static final EntityType<ThrownGoop> THROWN_GOOP = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, "thrown_goop"), FabricEntityTypeBuilder.<ThrownGoop>create(MobCategory.MISC, ThrownGoop::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).build());

    public static void init() {
        FabricDefaultAttributeRegistry.register(GOOP, Goop.createGoopAttributes());
        FabricDefaultAttributeRegistry.register(CRUNCHER, Cruncher.createCruncherAttributes());
        FabricDefaultAttributeRegistry.register(DRIPSTONE_TORTOISE, DripstoneTortoise.createDripstoneTortoiseAttributes());
        FabricDefaultAttributeRegistry.register(DRIPSTONE_PIKE, DripstonePike.createDripstonePikeAttributes());
    }
}
