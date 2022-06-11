package com.exdrill.cave_enhancements.item;


import com.exdrill.cave_enhancements.entity.DripstoneTortoiseEntity;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import javax.annotation.ParametersAreNonnullByDefault;

public class AmethystFluteItem extends Item {
    private static final long fearDuration = 40L;
    public AmethystFluteItem(Settings settings) {
        super(settings);
    }

    @Override
    @ParametersAreNonnullByDefault
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        if (world.isClient) {
            world.addParticle(ModParticles.SOOTHING_NOTE, pos.getX(), pos.getY() + 1D, pos.getZ(), 0, 0.5, 0);
        }
        if (!world.isClient) {
            itemStack.damage(1, user, (userx) -> userx.sendToolBreakStatus(hand));
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            user.world.getOtherEntities(user, user.getBoundingBox().expand(10D), user::canSee).forEach(entity -> {
                if (entity instanceof RavagerEntity ravagerEntity) {
                    ravagerEntity.handleStatus((byte)39);
                    ravagerEntity.playSound(SoundEvents.ENTITY_RAVAGER_STUNNED, 1.0F, 1.0F);
                    world.sendEntityStatus(ravagerEntity, (byte)39);
                } else if (entity instanceof VexEntity vexEntity) {
                    vexEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 0));
                } else if (entity instanceof DripstoneTortoiseEntity dripstoneTortoiseEntity) {
                    dripstoneTortoiseEntity.sooth();
                }
            });

            NbtCompound nbt = itemStack.getOrCreateNbt();
            nbt.putLong("AmethystFluteScary", world.getTime());
            itemStack.setNbt(nbt);
            user.getItemCooldownManager().set(this, 400);
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }
        return TypedActionResult.success(itemStack);
    }



    @ParametersAreNonnullByDefault
    public static boolean isScary(LivingEntity livingEntity) {
        long scaryTime = 0L;
        if (livingEntity.getMainHandStack().getItem() instanceof AmethystFluteItem) {
            scaryTime = livingEntity.getMainHandStack().getOrCreateNbt().getLong("AmethystFluteScary");
        }
        if (livingEntity.getOffHandStack().getItem() instanceof AmethystFluteItem) {
            scaryTime = Math.max(scaryTime, livingEntity.getOffHandStack().getOrCreateNbt().getLong("AmethystFluteScary"));
        }
        return scaryTime != 0L && livingEntity.world.getTime() - scaryTime <= fearDuration;
    }
}

