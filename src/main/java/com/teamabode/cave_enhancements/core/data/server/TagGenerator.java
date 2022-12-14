package com.teamabode.cave_enhancements.core.data.server;

import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagGenerator {

    public static class BlockTagGenerator extends FabricTagProvider<Block> {
        public BlockTagGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator, Registry.BLOCK);
        }

        protected void generateTags() {
            this.appendPickaxeMineable();
            this.appendHoeMineable();
            this.appendStoneTool();
            this.appendWallBlocks();
            this.getOrCreateTagBuilder(BlockTags.CANDLES).setReplace(false).add(ModBlocks.SPECTACLE_CANDLE);
            this.getOrCreateTagBuilder(BlockTags.CANDLE_CAKES).setReplace(false).add(ModBlocks.SPECTACLE_CANDLE_CAKE);
            this.getOrCreateTagBuilder(BlockTags.FALL_DAMAGE_RESETTING).setReplace(false).add(ModBlocks.DRIPPING_GOOP);
        }

        private void appendPickaxeMineable() {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
            .setReplace(false)
            .add(ModBlocks.LIGHTNING_ANCHOR)
            .add(ModBlocks.CHARGED_LIGHTNING_ANCHOR)
            .add(ModBlocks.ROSE_QUARTZ_BLOCK)
            .add(ModBlocks.JAGGED_ROSE_QUARTZ)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ_SLAB)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ_STAIRS)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ_WALL)
            .add(ModBlocks.ROSE_QUARTZ_TILES)
            .add(ModBlocks.ROSE_QUARTZ_TILE_SLAB)
            .add(ModBlocks.ROSE_QUARTZ_TILE_STAIRS)
            .add(ModBlocks.ROSE_QUARTZ_TILE_WALL)
            .add(ModBlocks.ROSE_QUARTZ_CHIMES)
            .add(ModBlocks.ROSE_QUARTZ_LAMP)
            .add(ModBlocks.SOUL_ROSE_QUARTZ_LAMP);
        }

        private void appendHoeMineable() {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE)
            .setReplace(false)
            .add(ModBlocks.GOOP_BLOCK)
            .add(ModBlocks.GOOP_TRAP)
            .add(ModBlocks.VOLATILE_GOOP);
        }

        private void appendStoneTool() {
            this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .setReplace(false)
            .add(ModBlocks.LIGHTNING_ANCHOR)
            .add(ModBlocks.CHARGED_LIGHTNING_ANCHOR)
            .add(ModBlocks.ROSE_QUARTZ_BLOCK)
            .add(ModBlocks.JAGGED_ROSE_QUARTZ)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ_SLAB)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ_STAIRS)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ_WALL)
            .add(ModBlocks.ROSE_QUARTZ_TILES)
            .add(ModBlocks.ROSE_QUARTZ_TILE_SLAB)
            .add(ModBlocks.ROSE_QUARTZ_TILE_STAIRS)
            .add(ModBlocks.ROSE_QUARTZ_TILE_WALL)
            .add(ModBlocks.ROSE_QUARTZ_CHIMES)
            .add(ModBlocks.ROSE_QUARTZ_LAMP)
            .add(ModBlocks.SOUL_ROSE_QUARTZ_LAMP);
        }

        private void appendWallBlocks() {
            this.getOrCreateTagBuilder(BlockTags.WALLS)
            .setReplace(false)
            .add(ModBlocks.POLISHED_ROSE_QUARTZ_WALL)
            .add(ModBlocks.ROSE_QUARTZ_TILE_WALL);
        }
    }

    public static class ItemTagGenerator extends FabricTagProvider<Item> {
        public ItemTagGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator, Registry.ITEM);
        }

        protected void generateTags() {
            this.getOrCreateTagBuilder(ItemTags.ARROWS).setReplace(false).add(ModItems.HARMONIC_ARROW);
            this.getOrCreateTagBuilder(ItemTags.CANDLES).setReplace(false).add(ModItems.SPECTACLE_CANDLE);
        }
    }

    public static class EntityTypeTagGenerator extends FabricTagProvider<EntityType<?>> {
        public EntityTypeTagGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator, Registry.ENTITY_TYPE);
        }

        protected void generateTags() {
            this.getOrCreateTagBuilder(EntityTypeTags.FROG_FOOD).setReplace(false).add(ModEntities.GOOP);
        }
    }
}
