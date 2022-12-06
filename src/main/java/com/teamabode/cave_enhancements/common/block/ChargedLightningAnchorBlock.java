package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.List;

@ParametersAreNonnullByDefault
public class ChargedLightningAnchorBlock extends Block {
    public ChargedLightningAnchorBlock(FabricBlockSettings settings){
        super(settings);
    }

    private void knockBack(LivingEntity entity, BlockPos pos, double power, double verticlePower) {
        double d = entity.getX() - pos.getX();
        double e = entity.getZ() - pos.getZ();
        double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
        entity.push((d / f) * power, verticlePower, (e / f) * power);

        entity.hurtMarked = true;
    }

    private boolean isPowered(Level world, BlockPos pos){
        for (Direction dir : Direction.values()) {
            BlockPos pos2 = pos.relative(dir);
            BlockState state = world.getBlockState(pos2);

            if (world.getSignal(pos2, dir) > 0 && !state.is(Blocks.LIGHTNING_ROD)) {
                return true;
            }
        }
        return false;
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        spawnParticles(level, blockPos);
    }

    private static void spawnParticles(Level level, BlockPos pos) {
        RandomSource randomSource = level.random;

        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.relative(direction);
            if (!level.getBlockState(blockPos).isSolidRender(level, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.7 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.7 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.7 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                if (randomSource.nextInt(4) == 0) {
                    level.addParticle(ModParticles.CHARGE, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    private void activate(Level world, BlockPos pos, boolean interact){

        boolean powered = isPowered(world, pos);

        if((powered || interact)) {

            List<? extends LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(4.0D), (e) -> true);

            double power = 0.9D;
            double verticalPower = 0.5D;

            LivingEntity livingEntity;
            for (Iterator<? extends LivingEntity> var2 = list.iterator(); var2.hasNext(); knockBack(livingEntity, pos, power, verticalPower)) {
                livingEntity = var2.next();
                livingEntity.hurt(DamageSource.LIGHTNING_BOLT, 10.0F);
            }

            if (!world.isClientSide) {
                ((ServerLevel)world).sendParticles(ModParticles.SHOCKWAVE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }


            world.setBlockAndUpdate(pos, ModBlocks.LIGHTNING_ANCHOR.defaultBlockState());
            world.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        activate(world, pos, true);

        return InteractionResult.SUCCESS;
    }

    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        activate(world, pos, false);
    }

    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        activate(world, pos, false);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
}
