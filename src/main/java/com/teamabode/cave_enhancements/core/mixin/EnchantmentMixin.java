package com.teamabode.cave_enhancements.core.mixin;

import com.teamabode.cave_enhancements.core.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method="canEnchant", at = @At("HEAD"), cancellable=true)
    private void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        if (stack.getItem() == ModItems.GLOW_PASTE){
            ci.setReturnValue(false);
        }
    }
}
