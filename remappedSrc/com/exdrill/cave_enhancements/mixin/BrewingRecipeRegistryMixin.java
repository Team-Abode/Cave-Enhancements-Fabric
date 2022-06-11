package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionBrewing.class)
public class BrewingRecipeRegistryMixin {
    @Inject(at=@At("HEAD"), method = "registerDefaults")
    private static void registerDefaults(CallbackInfo info) {
        registerPotionRecipe(Potions.AWKWARD, ModItems.GOOP, Potions.SLOWNESS);
    }

    @Shadow private static void registerPotionRecipe(Potion input, Item item, Potion output) {
        throw new RuntimeException();
    }
}
