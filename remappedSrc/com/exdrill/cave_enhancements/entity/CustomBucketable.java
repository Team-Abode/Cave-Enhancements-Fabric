package com.exdrill.cave_enhancements.entity;

import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public interface CustomBucketable {
    boolean isFromBucket();

    void setFromBucket(boolean fromBucket);

    void copyDataToStack(ItemStack stack);

    void copyDataFromNbt(CompoundTag nbt);

    ItemStack getBucketItem();

    SoundEvent getBucketedSound();

    static void copyDataToStack(Mob entity, ItemStack stack) {
        CompoundTag nbtCompound = stack.getOrCreateTag();
        if (entity.hasCustomName()) {
            stack.setHoverName(entity.getCustomName());
        }

        if (entity.isNoAi()) {
            nbtCompound.putBoolean("NoAI", entity.isNoAi());
        }

        if (entity.isSilent()) {
            nbtCompound.putBoolean("Silent", entity.isSilent());
        }

        if (entity.isNoGravity()) {
            nbtCompound.putBoolean("NoGravity", entity.isNoGravity());
        }

        if (entity.hasGlowingTag()) {
            nbtCompound.putBoolean("Glowing", entity.hasGlowingTag());
        }

        if (entity.isInvulnerable()) {
            nbtCompound.putBoolean("Invulnerable", entity.isInvulnerable());
        }

        nbtCompound.putFloat("Health", entity.getHealth());
    }

    static void copyDataFromNbt(Mob entity, CompoundTag nbt) {
        if (nbt.contains("NoAI")) {
            entity.setNoAi(nbt.getBoolean("NoAI"));
        }

        if (nbt.contains("Silent")) {
            entity.setSilent(nbt.getBoolean("Silent"));
        }

        if (nbt.contains("NoGravity")) {
            entity.setNoGravity(nbt.getBoolean("NoGravity"));
        }

        if (nbt.contains("Glowing")) {
            entity.setGlowingTag(nbt.getBoolean("Glowing"));
        }

        if (nbt.contains("Invulnerable")) {
            entity.setInvulnerable(nbt.getBoolean("Invulnerable"));
        }

        if (nbt.contains("Health", 99)) {
            entity.setHealth(nbt.getFloat("Health"));
        }

    }

    static <T extends LivingEntity & CustomBucketable> Optional<InteractionResult> tryBucket(Player player, InteractionHand hand, T entity) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() == Items.BUCKET && entity.isAlive()) {
            entity.playSound(((CustomBucketable)entity).getBucketedSound(), 1.0F, 1.0F);
            ItemStack itemStack2 = ((CustomBucketable)entity).getBucketItem();
            ((CustomBucketable)entity).copyDataToStack(itemStack2);
            ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
            player.setItemInHand(hand, itemStack3);
            Level world = entity.level;
            if (!world.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, itemStack2);
            }

            entity.discard();
            return Optional.of(InteractionResult.sidedSuccess(world.isClientSide));
        } else {
            return Optional.empty();
        }
    }
}