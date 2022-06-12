package com.exdrill.cave_enhancements.item;


import com.exdrill.cave_enhancements.entity.DripstoneTortoiseEntity;
import com.exdrill.cave_enhancements.registry.ModParticles;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class AmethystFluteItem extends Item {
    private static final long fearDuration = 40L;
    public AmethystFluteItem(Properties settings) {
        super(settings);
    }

    @Override
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        if (world.isClientSide) {
            world.addParticle(ModParticles.SOOTHING_NOTE, pos.getX(), pos.getY() + 1D, pos.getZ(), 0, 0.5, 0);
        }
        if (!world.isClientSide) {
            itemStack.hurtAndBreak(1, user, (userx) -> userx.broadcastBreakEvent(hand));
            world.playSound(null, pos, SoundEvents.NOTE_BLOCK_FLUTE, SoundSource.PLAYERS, 1.0F, 1.0F);
            user.level.getEntities(user, user.getBoundingBox().inflate(10D), user::hasLineOfSight).forEach(entity -> {
                if (entity instanceof Ravager ravagerEntity) {
                    ravagerEntity.handleEntityEvent((byte)39);
                    ravagerEntity.playSound(SoundEvents.RAVAGER_STUNNED, 1.0F, 1.0F);
                    world.broadcastEntityEvent(ravagerEntity, (byte)39);
                } else if (entity instanceof Vex vexEntity) {
                    vexEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 0));
                } else if (entity instanceof DripstoneTortoiseEntity dripstoneTortoiseEntity) {
                    dripstoneTortoiseEntity.sooth();
                }
            });

            CompoundTag nbt = itemStack.getOrCreateTag();
            nbt.putLong("AmethystFluteScary", world.getGameTime());
            itemStack.setTag(nbt);
            user.getCooldowns().addCooldown(this, 400);
            user.gameEvent(GameEvent.ITEM_INTERACT_START);
        }
        return InteractionResultHolder.success(itemStack);
    }



    @ParametersAreNonnullByDefault
    public static boolean isScary(LivingEntity livingEntity) {
        long scaryTime = 0L;
        if (livingEntity.getMainHandItem().getItem() instanceof AmethystFluteItem) {
            scaryTime = livingEntity.getMainHandItem().getOrCreateTag().getLong("AmethystFluteScary");
        }
        if (livingEntity.getOffhandItem().getItem() instanceof AmethystFluteItem) {
            scaryTime = Math.max(scaryTime, livingEntity.getOffhandItem().getOrCreateTag().getLong("AmethystFluteScary"));
        }
        return scaryTime != 0L && livingEntity.level.getGameTime() - scaryTime <= fearDuration;
    }
}

