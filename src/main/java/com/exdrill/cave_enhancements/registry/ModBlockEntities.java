package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static BlockEntityType<SpectacleCandleBlockEntity> SPECTACLE_CANDLE;
    public static BlockEntityType<SpectacleCandleCakeBlockEntity> SPECTACLE_CANDLE_CAKE;
    public static BlockEntityType<LightningAnchorBlockEntity> LIGHTNING_ANCHOR;
    public static BlockEntityType<RoseQuartzChimesBlockEntity> ROSE_QUARTZ_CHIMES;
    public static BlockEntityType<ReceiverBlockEntity> RECEIVER_BLOCK;

    public static void register() {
        SPECTACLE_CANDLE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new ResourceLocation(CaveEnhancements.MODID, "spectacle_candle"),
                FabricBlockEntityTypeBuilder.create(
                        SpectacleCandleBlockEntity::new, ModBlocks.SPECTACLE_CANDLE
                ).build(null)
        );
        SPECTACLE_CANDLE_CAKE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new ResourceLocation(CaveEnhancements.MODID, "spectacle_candle_cake"),
                FabricBlockEntityTypeBuilder.create(SpectacleCandleCakeBlockEntity::new, ModBlocks.SPECTACLE_CANDLE_CAKE).build(null)
        );

        LIGHTNING_ANCHOR = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new ResourceLocation(CaveEnhancements.MODID, "lightning_anchor"),
                FabricBlockEntityTypeBuilder.create(LightningAnchorBlockEntity::new, ModBlocks.LIGHTNING_ANCHOR).build(null)
        );

        ROSE_QUARTZ_CHIMES = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_chimes"),
                FabricBlockEntityTypeBuilder.create(RoseQuartzChimesBlockEntity::new, ModBlocks.ROSE_QUARTZ_CHIMES).build(null)
        );

        RECEIVER_BLOCK = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new ResourceLocation(CaveEnhancements.MODID, "redstone_receiver"),
                FabricBlockEntityTypeBuilder.create(
                        ReceiverBlockEntity::new,
                        ModBlocks.REDSTONE_RECEIVER,
                        ModBlocks.EXPOSED_REDSTONE_RECEIVER,
                        ModBlocks.WEATHERED_REDSTONE_RECEIVER,
                        ModBlocks.OXIDIZED_REDSTONE_RECEIVER,
                        ModBlocks.WAXED_REDSTONE_RECEIVER,
                        ModBlocks.WAXED_EXPOSED_REDSTONE_RECEIVER,
                        ModBlocks.WAXED_WEATHERED_REDSTONE_RECEIVER,
                        ModBlocks.WAXED_OXIDIZED_REDSTONE_RECEIVER).build(null)
        );
    }
}
