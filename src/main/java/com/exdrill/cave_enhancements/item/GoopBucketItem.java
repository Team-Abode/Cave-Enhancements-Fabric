package com.exdrill.cave_enhancements.item;

import com.exdrill.cave_enhancements.entity.GoopBucketable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class GoopBucketItem extends BucketItem {
    private final Fluid fluid;
    private final EntityType<?> entityType;
    private final SoundEvent emptyingSound;

    public GoopBucketItem(EntityType<?> type, Fluid fluid, SoundEvent emptyingSound, Properties settings) {
        super(fluid, settings);
        this.fluid = fluid;
        this.entityType = type;
        this.emptyingSound = emptyingSound;
    }

    public void checkExtraContent(@Nullable Player player, Level world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerLevel) {
            this.spawnEntity((ServerLevel)world, stack, pos);
            world.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
            this.playEmptySound(null, world, pos);
        }

    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor world, BlockPos pos) {
        world.playSound(player, pos, this.emptyingSound, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void spawnEntity(ServerLevel world, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.spawn(world, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof GoopBucketable bucketable) {
            bucketable.copyDataFromNbt(stack.getOrCreateTag());
            bucketable.setFromBucket(true);
        }

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            BlockState blockState;
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.relative(direction);
            blockState = world.getBlockState(blockPos);
            BlockPos blockPos3 = blockState.getBlock() instanceof LiquidBlockContainer && this.fluid == Fluids.EMPTY ? blockPos : blockPos2;
            if (world.mayInteract(user, blockPos) && user.mayUseItemAt(blockPos2, direction, itemStack)) {
                this.checkExtraContent(user, world, itemStack, blockPos3);
                user.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemStack, user), world.isClientSide());
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        }
    }
}
