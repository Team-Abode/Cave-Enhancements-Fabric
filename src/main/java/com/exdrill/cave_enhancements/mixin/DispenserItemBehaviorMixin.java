package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.block.VolatileGoopBlock;
import com.exdrill.cave_enhancements.entity.HarmonicArrow;
import com.exdrill.cave_enhancements.entity.goop.ThrownGoop;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModItems;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenseItemBehavior.class)
public interface DispenserItemBehaviorMixin {

    @Inject(method = "bootStrap", at = @At("HEAD"))
    private static void registerDefaults(CallbackInfo ci) {
        DispenserBlock.registerBehavior(ModItems.HARMONIC_ARROW, new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                AbstractArrow persistentProjectileEntity = new HarmonicArrow(world, position.x(), position.y(), position.z());
                persistentProjectileEntity.pickup = AbstractArrow.Pickup.ALLOWED;
                return persistentProjectileEntity;
            }
        });
        DispenserBlock.registerBehavior(ModItems.GOOP, new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
                return Util.make(new ThrownGoop(level, position.x(), position.y(), position.z()), (goop) -> goop.setItem(stack));
            }
        });
        DispenserBlock.registerBehavior(ModItems.VOLATILE_GOOP, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                this.setSuccess(true);
                Level level = source.getLevel();
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockPos = source.getPos().relative(direction);

                if (level.getBlockState(blockPos).isAir()) {
                    level.setBlockAndUpdate(blockPos, ModBlocks.VOLATILE_GOOP.defaultBlockState().setValue(VolatileGoopBlock.FACING, direction));
                    level.gameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                    stack.shrink(1);
                } else {
                    this.setSuccess(false);
                }
                return stack;
            }
        });
    }
}
