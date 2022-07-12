package com.exdrill.cave_enhancements.item;


import com.exdrill.cave_enhancements.entity.DripstoneTortoise;
import com.exdrill.cave_enhancements.registry.ModParticles;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Phantom;
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
            world.addParticle(ModParticles.SOOTHING_NOTE, x, y + 1.0D, z, 0, 0.2, 0);
        }
        if (!world.isClientSide) {

            world.playSound(null, pos, SoundEvents.NOTE_BLOCK_FLUTE, SoundSource.PLAYERS, 1.0F, 1.0F);
            itemStack.hurtAndBreak(1, user, (userx) -> userx.broadcastBreakEvent(hand));



            user.level.getEntities(user, user.getBoundingBox().inflate(10D), user::hasLineOfSight).forEach(entity -> {

                if (entity instanceof Ravager ravagerEntity) {

                    ravagerEntity.handleEntityEvent((byte)39);
                    ravagerEntity.playSound(SoundEvents.RAVAGER_STUNNED, 1.0F, 1.0F);
                    world.broadcastEntityEvent(ravagerEntity, (byte)39);

                } else if (entity instanceof Vex vexEntity) {

                    world.addParticle(ModParticles.SOOTHING_NOTE, entity.getX(), entity.getY() + 1.0D, entity.getZ(), 0, 0, 0);
                    vexEntity.kill();

                } else if (entity instanceof DripstoneTortoise dripstoneTortoise) {
                    dripstoneTortoise.sooth();
                }

                if (entity instanceof DripstoneTortoise || entity instanceof Ravager || entity instanceof Vex || entity instanceof Creeper || entity instanceof Phantom) {
                    if (world instanceof ServerLevel server) {
                        server.sendParticles(ModParticles.SOOTHING_NOTE, entity.getX(), entity.getEyeY() + 0.5D, entity.getZ(), 1, 0, 0, 0,0);
                    }
                }


            });

            user.gameEvent(GameEvent.NOTE_BLOCK_PLAY);
            CompoundTag nbt = itemStack.getOrCreateTag();
            nbt.putLong("AmethystFluteScary", world.getGameTime());
            itemStack.setTag(nbt);
            user.getCooldowns().addCooldown(this, 200);
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

