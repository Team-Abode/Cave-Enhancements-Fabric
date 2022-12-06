package com.teamabode.cave_enhancements.common.item;


import com.teamabode.cave_enhancements.core.registry.ModParticles;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import com.teamabode.cave_enhancements.core.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Monster;
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
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockPos pos = user.blockPosition();

        world.playSound(null, pos, ModSounds.ITEM_AMETHYST_FLUTE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        user.level.addParticle(ModParticles.SOOTHING_NOTE, user.getX(), user.getEyeY(), user.getZ(), 0.0D, 0.15D, 0.0D);
        user.level.getEntities(user, user.getBoundingBox().inflate(10D)).forEach(entity -> {

            if (!world.isClientSide()) {
                if (entity instanceof PathfinderMob mob) {
                    if ( (mob instanceof Monster || mob instanceof NeutralMob) && !mob.getType().is(ModTags.AMETHYST_FLUTE_IMMUNE) )  {
                        mob.goalSelector.addGoal(0, new AvoidEntityGoal<>(mob, Player.class, 16.0F, 1.0D, 1.0D, AmethystFluteItem::isScary));
                        mob.level.addParticle(ModParticles.SOOTHING_NOTE, user.getX(), user.getEyeY(), user.getZ(), 0.0D, 0.15D, 0.0D);
                    }
                }
            }
        });

        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.putLong("AmethystFluteScary", world.getGameTime());
        itemStack.setTag(nbt);

        itemStack.hurtAndBreak(1, user, (player) -> player.broadcastBreakEvent(hand));
        user.gameEvent(GameEvent.INSTRUMENT_PLAY);
        user.getCooldowns().addCooldown(this, 200);

        return InteractionResultHolder.success(itemStack);
    }

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

