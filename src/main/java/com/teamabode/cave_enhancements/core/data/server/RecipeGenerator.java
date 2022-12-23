package com.teamabode.cave_enhancements.core.data.server;

import com.teamabode.cave_enhancements.core.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
        createPackedRecipe(ModItems.GOOP, ModItems.GOOP_BLOCK, exporter);

        ShapedRecipeBuilder.shaped(ModItems.DRIPPING_GOOP)
        .define('X', ModItems.GOOP)
        .pattern("X")
        .pattern("X")
        .pattern("X")
        .unlockedBy("has_goop", has(ModItems.GOOP))
        .save(exporter, getItemName(ModItems.DRIPPING_GOOP));

        ShapedRecipeBuilder.shaped(ModItems.HARMONIC_ARROW, 2)
        .define('#', Items.STICK)
        .define('X', Items.AMETHYST_SHARD)
        .define('Y', Items.FEATHER)
        .pattern("X")
        .pattern("#")
        .pattern("Y")
        .unlockedBy("has_feather", has(Items.FEATHER))
        .unlockedBy("has_flint", has(Items.FLINT))
        .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
        .save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.GLOW_PASTE)
        .define('G', Items.GLOW_INK_SAC)
        .define('X', Items.IRON_INGOT)
        .pattern("X ")
        .pattern(" G")
        .unlockedBy("has_glow_ink_sac", has(Items.GLOW_INK_SAC))
        .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
        .save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.SPECTACLE_CANDLE)
        .define('S', Items.STRING)
        .define('H', Items.HONEYCOMB)
        .define('G', Items.GLOW_INK_SAC)
        .pattern("S")
        .pattern("H")
        .pattern("G")
        .unlockedBy("has_string", has(Items.STRING))
        .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
        .unlockedBy("has_glow_ink_sac", has(Items.GLOW_INK_SAC))
        .save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.AMETHYST_FLUTE)
        .define('#', Items.AMETHYST_SHARD)
        .pattern("  #")
        .pattern(" # ")
        .pattern("#  ")
        .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
        .save(exporter);

        generateRoseQuartzRecipes(exporter);
        generateShapelessRecipe(exporter, new Item[]{Items.GLOW_BERRIES, Items.SUGAR, Items.GLASS_BOTTLE}, ModItems.GLOW_BERRY_JUICE);
        generateShapelessRecipe(exporter, new Item[]{ModItems.GOOP, Items.PAPER}, ModItems.GOOP_BANNER_PATTERN);

        createRedstoneReceiver(Items.COPPER_BLOCK, ModItems.REDSTONE_RECEIVER, exporter);
        createRedstoneReceiver(Items.EXPOSED_COPPER, ModItems.EXPOSED_REDSTONE_RECEIVER, exporter);
        createRedstoneReceiver(Items.WEATHERED_COPPER, ModItems.WEATHERED_REDSTONE_RECEIVER, exporter);
        createRedstoneReceiver(Items.OXIDIZED_COPPER, ModItems.OXIDIZED_REDSTONE_RECEIVER, exporter);
        createWaxedReceiver(ModItems.REDSTONE_RECEIVER, Items.WAXED_COPPER_BLOCK, ModItems.WAXED_REDSTONE_RECEIVER, exporter);
        createWaxedReceiver(ModItems.EXPOSED_REDSTONE_RECEIVER, Items.WAXED_EXPOSED_COPPER, ModItems.WAXED_EXPOSED_REDSTONE_RECEIVER, exporter);
        createWaxedReceiver(ModItems.WEATHERED_REDSTONE_RECEIVER, Items.WAXED_WEATHERED_COPPER, ModItems.WAXED_WEATHERED_REDSTONE_RECEIVER, exporter);
        createWaxedReceiver(ModItems.OXIDIZED_REDSTONE_RECEIVER, Items.WAXED_OXIDIZED_COPPER, ModItems.WAXED_OXIDIZED_REDSTONE_RECEIVER, exporter);
    }

    private void generateShapelessRecipe(Consumer<FinishedRecipe> exporter, Item[] ingredients, Item result) {
        ShapelessRecipeBuilder recipeBuilder = ShapelessRecipeBuilder.shapeless(result);
        for (Item ingredient : ingredients) {
            recipeBuilder.requires(ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient));
        }
        recipeBuilder.save(exporter);
    }

    private void generateRoseQuartzRecipes(Consumer<FinishedRecipe> exporter) {
        createPackedRecipe(ModItems.ROSE_QUARTZ, ModItems.ROSE_QUARTZ_BLOCK, exporter);
        polishRecipe(ModItems.ROSE_QUARTZ_BLOCK, ModItems.POLISHED_ROSE_QUARTZ, exporter);
        polishRecipe(ModItems.POLISHED_ROSE_QUARTZ, ModItems.ROSE_QUARTZ_TILES, exporter);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.POLISHED_ROSE_QUARTZ, ModItems.ROSE_QUARTZ_BLOCK);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.POLISHED_ROSE_QUARTZ_SLAB, ModItems.ROSE_QUARTZ_BLOCK);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.POLISHED_ROSE_QUARTZ_STAIRS, ModItems.ROSE_QUARTZ_BLOCK);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.POLISHED_ROSE_QUARTZ_WALL, ModItems.ROSE_QUARTZ_BLOCK);

        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.POLISHED_ROSE_QUARTZ_SLAB, ModItems.POLISHED_ROSE_QUARTZ);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.POLISHED_ROSE_QUARTZ_STAIRS, ModItems.POLISHED_ROSE_QUARTZ);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.POLISHED_ROSE_QUARTZ_WALL, ModItems.POLISHED_ROSE_QUARTZ);

        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILES, ModItems.ROSE_QUARTZ_BLOCK);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_SLAB, ModItems.ROSE_QUARTZ_BLOCK);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_STAIRS, ModItems.ROSE_QUARTZ_BLOCK);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_WALL, ModItems.ROSE_QUARTZ_BLOCK);

        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILES, ModItems.POLISHED_ROSE_QUARTZ);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_SLAB, ModItems.POLISHED_ROSE_QUARTZ);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_STAIRS, ModItems.POLISHED_ROSE_QUARTZ);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_WALL, ModItems.POLISHED_ROSE_QUARTZ);

        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_SLAB, ModItems.ROSE_QUARTZ_TILES);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_STAIRS, ModItems.ROSE_QUARTZ_TILES);
        RecipeProvider.stonecutterResultFromBase(exporter, ModItems.ROSE_QUARTZ_TILE_WALL, ModItems.ROSE_QUARTZ_TILES);

        RecipeProvider.slab(exporter, ModItems.POLISHED_ROSE_QUARTZ_SLAB, ModItems.POLISHED_ROSE_QUARTZ);
        stairs(exporter, ModItems.POLISHED_ROSE_QUARTZ_STAIRS, ModItems.POLISHED_ROSE_QUARTZ);
        RecipeProvider.wall(exporter, ModItems.POLISHED_ROSE_QUARTZ_WALL, ModItems.POLISHED_ROSE_QUARTZ);

        RecipeProvider.slab(exporter, ModItems.ROSE_QUARTZ_TILE_SLAB, ModItems.ROSE_QUARTZ_TILES);
        stairs(exporter, ModItems.ROSE_QUARTZ_TILE_STAIRS, ModItems.ROSE_QUARTZ_TILES);
        RecipeProvider.wall(exporter, ModItems.ROSE_QUARTZ_TILE_WALL, ModItems.ROSE_QUARTZ_TILES);

        ShapedRecipeBuilder.shaped(ModItems.LIGHTNING_ANCHOR)
        .define('C', Items.COPPER_BLOCK)
        .define('R', ModItems.ROSE_QUARTZ)
        .pattern("CCC")
        .pattern("CRC")
        .pattern("CCC")
        .unlockedBy("has_copper_block", has(Items.COPPER_BLOCK))
        .unlockedBy("has_rose_quartz", has(ModItems.ROSE_QUARTZ))
        .save(exporter);

        createRoseQuartzLamp(Items.TORCH, ModItems.ROSE_QUARTZ_LAMP, exporter);
        createRoseQuartzLamp(Items.SOUL_TORCH, ModItems.SOUL_ROSE_QUARTZ_LAMP, exporter);
        createRoseQuartzChimes(exporter);
    }

    private void createPackedRecipe(Item ingredient, Item result, Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(result)
        .define('#', ingredient)
        .pattern("##").pattern("##")
        .unlockedBy(getHasName(ingredient), has(ingredient))
        .save(exporter);
    }

    private void stairs(Consumer<FinishedRecipe> exporter, Item stairs, Item material) {
        ShapedRecipeBuilder.shaped(stairs, 4)
        .define('#', material)
        .pattern("#  ")
        .pattern("## ")
        .pattern("###")
        .unlockedBy(getHasName(material), has(material))
        .save(exporter);
    }

    private void polishRecipe(Item ingredient, Item result, Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(result, 4)
        .define('#', ingredient)
        .pattern("##")
        .pattern("##")
        .unlockedBy(getHasName(ingredient), has(ingredient))
        .save(exporter);
    }

    private void createRoseQuartzLamp(Item torchIngredient, Item result, Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(result, 1)
        .define('r', ModItems.ROSE_QUARTZ)
        .define('i', Items.IRON_NUGGET)
        .define('t', torchIngredient)
        .pattern(" r ")
        .pattern("iti")
        .pattern(" i ")
        .unlockedBy(getHasName(torchIngredient), has(torchIngredient))
        .unlockedBy("has_rose_quartz", has(ModItems.ROSE_QUARTZ))
        .save(exporter);
    }

    private void createRoseQuartzChimes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(ModItems.ROSE_QUARTZ_CHIMES)
        .define('s', Items.STONE_BRICKS)
        .define('g', Items.GHAST_TEAR)
        .define('t', Items.STRING)
        .define('r', ModItems.ROSE_QUARTZ)
        .pattern("sgs")
        .pattern("t t")
        .pattern("r r")
        .unlockedBy("has_rose_quartz", has(ModItems.ROSE_QUARTZ))
        .save(exporter);
    }

    private void createRedstoneReceiver(Item copperBlock, Item result, Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(result)
        .define('r', Items.REDSTONE)
        .define('C', copperBlock)
        .define('T', Items.REDSTONE_TORCH)
        .pattern("rTr")
        .pattern("CCC")
        .unlockedBy("has_redstone", has(Items.REDSTONE))
        .unlockedBy(getHasName(copperBlock), has(copperBlock))
        .unlockedBy("has_redstone_torch", has(Items.REDSTONE_TORCH))
        .save(exporter);
    }

    private void createWaxedReceiver(Item receiverType, Item copperBlock, Item result, Consumer<FinishedRecipe> exporter) {
        ShapelessRecipeBuilder.shapeless(result)
        .requires(receiverType).requires(Items.HONEYCOMB)
        .unlockedBy(getHasName(receiverType), has(receiverType))
        .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
        .save(exporter, getItemName(result) + "_from_honeycomb");

        createRedstoneReceiver(copperBlock, result, exporter);
    }
}
