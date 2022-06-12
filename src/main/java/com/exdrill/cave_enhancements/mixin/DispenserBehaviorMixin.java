package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.entity.HarmonicArrowEntity;
import com.exdrill.cave_enhancements.registry.ModItems;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenseItemBehavior.class)
public interface DispenserBehaviorMixin {

    @Inject(method = "bootStrap", at = @At("HEAD"))
    private static void registerDefaults(CallbackInfo ci) {
        DispenserBlock.registerBehavior(ModItems.HARMONIC_ARROW, new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                AbstractArrow persistentProjectileEntity = new HarmonicArrowEntity(world, position.x(), position.y(), position.z());
                persistentProjectileEntity.pickup = AbstractArrow.Pickup.ALLOWED;
                return persistentProjectileEntity;
            }
        });
    }
}
