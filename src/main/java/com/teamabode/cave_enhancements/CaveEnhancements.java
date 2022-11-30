package com.teamabode.cave_enhancements;

import com.teamabode.cave_enhancements.block.VolatileGoopBlock;
import com.teamabode.cave_enhancements.entity.HarmonicArrow;
import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import com.teamabode.cave_enhancements.entity.goop.Goop;
import com.teamabode.cave_enhancements.entity.goop.ThrownGoop;
import com.teamabode.cave_enhancements.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;

public class CaveEnhancements implements ModInitializer {
    public static final String MODID = "cave_enhancements";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final BannerPattern GOOP = new BannerPattern("goop");

    public void onInitialize() {
        ModSounds.init();
        ModEntities.init();
        ModBiomes.init();
        ModParticles.init();
        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModEffects.init();
        ModPotions.init();
        ModFeatures.init();
        Registry.register(Registry.BANNER_PATTERN, new ResourceLocation(MODID, "goop"), GOOP);

        registerOxidizableBlockPairs();
        registerSpawnPlacements();
        registerBiomeModifications();
        registerDispenserBehaviors();

        if (!FabricLoaderImpl.INSTANCE.isModLoaded("terrablender")) LOGGER.info("Terrablender not found, skipping integration...");
    }

    public static void registerOxidizableBlockPairs() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.REDSTONE_RECEIVER, ModBlocks.EXPOSED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.EXPOSED_REDSTONE_RECEIVER, ModBlocks.WEATHERED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.WEATHERED_REDSTONE_RECEIVER, ModBlocks.OXIDIZED_REDSTONE_RECEIVER);

        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.REDSTONE_RECEIVER, ModBlocks.WAXED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.EXPOSED_REDSTONE_RECEIVER, ModBlocks.WAXED_EXPOSED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.WEATHERED_REDSTONE_RECEIVER, ModBlocks.WAXED_WEATHERED_REDSTONE_RECEIVER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.OXIDIZED_REDSTONE_RECEIVER, ModBlocks.WAXED_OXIDIZED_REDSTONE_RECEIVER);
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(ModEntities.DRIPSTONE_TORTOISE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, DripstoneTortoise::checkDripstoneTortoiseSpawnRules);
        SpawnPlacements.register(ModEntities.CRUNCHER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Cruncher::checkCruncherSpawnRules);
        SpawnPlacements.register(ModEntities.GOOP, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, Goop::checkGoopSpawnRules);
    }

    public static void registerBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES), MobCategory.MONSTER, ModEntities.DRIPSTONE_TORTOISE, 50, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES), MobCategory.MONSTER, ModEntities.CRUNCHER, 10, 1, 1);
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(ModItems.HARMONIC_ARROW, new AbstractProjectileDispenseBehavior() {
            @ParametersAreNonnullByDefault
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                AbstractArrow persistentProjectileEntity = new HarmonicArrow(world, position.x(), position.y(), position.z());
                persistentProjectileEntity.pickup = AbstractArrow.Pickup.ALLOWED;
                return persistentProjectileEntity;
            }
        });
        DispenserBlock.registerBehavior(ModItems.GOOP, new AbstractProjectileDispenseBehavior() {
            @ParametersAreNonnullByDefault
            protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
                return Util.make(new ThrownGoop(level, position.x(), position.y(), position.z()), (goop) -> goop.setItem(stack));
            }
        });
        DispenserBlock.registerBehavior(ModItems.VOLATILE_GOOP, new OptionalDispenseItemBehavior() {
            @ParametersAreNonnullByDefault
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                this.setSuccess(true);
                Level level = source.getLevel();

                if (!level.getBlockState(source.getPos()).is(Blocks.AIR)) {
                    Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);

                    BlockPos blockPos = source.getPos().relative(direction);

                    if (level.getBlockState(blockPos).getMaterial().isReplaceable()) {
                        BlockState blockState = ModBlocks.VOLATILE_GOOP.defaultBlockState().setValue(VolatileGoopBlock.FACING, direction);

                        level.setBlockAndUpdate(blockPos, blockState);
                        level.gameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                        stack.shrink(1);
                    } else {
                        this.setSuccess(false);
                    }
                }

                return stack;
            }
        });
    }
}

