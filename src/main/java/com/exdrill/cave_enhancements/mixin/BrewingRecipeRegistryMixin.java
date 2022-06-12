package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.registry.ModItems;
import net.minecraft.world.item.BlockItem;
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
public abstract class BrewingRecipeRegistryMixin {

    @Shadow
    private static void addMix(Potion potion, Item item, Potion potion2) {
    }

    @Inject(at=@At("HEAD"), method = "bootStrap")
    private static void registerDefaults(CallbackInfo info) {
        addMix(Potions.AWKWARD, ModItems.GOOP, Potions.SLOWNESS);
    }


}
