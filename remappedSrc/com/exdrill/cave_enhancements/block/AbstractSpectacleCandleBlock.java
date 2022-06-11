
package com.exdrill.cave_enhancements.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSpectacleCandleBlock extends BaseEntityBlock {
    public static final int field_30987 = 3;
    public static final BooleanProperty LIT;

    protected AbstractSpectacleCandleBlock(Properties settings) {
        super(settings);
    }

    protected abstract Iterable<Vec3> getParticleOffsets(BlockState state);

    public static boolean isLitCandle(BlockState state) {
        return state.hasProperty(LIT) && (state.is(BlockTags.CANDLES) || state.is(BlockTags.CANDLE_CAKES)) && state.getValue(LIT);
    }

    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide && projectile.isOnFire() && this.isNotLit(state)) {
            setLit(world, state, hit.getBlockPos(), true);
        }

    }

    protected boolean isNotLit(BlockState state) {
        return !(Boolean)state.getValue(LIT);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            this.getParticleOffsets(state).forEach((offset) -> spawnCandleParticles(world, offset.add(pos.getX(), pos.getY(), pos.getZ()), random));
        }
    }

    private static void spawnCandleParticles(Level world, Vec3 vec3d, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0D, 0.0D, 0.0D);
            if (f < 0.17F) {
                world.playLocalSound(vec3d.x + 0.5D, vec3d.y + 0.5D, vec3d.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }

        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0D, 0.0D, 0.0D);
    }

    public static void extinguish(@Nullable Player player, BlockState state, LevelAccessor world, BlockPos pos) {
        setLit(world, state, pos, false);
        if (state.getBlock() instanceof AbstractSpectacleCandleBlock) {
            ((AbstractSpectacleCandleBlock)state.getBlock()).getParticleOffsets(state).forEach((offset) -> {
                world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + offset.x(), (double)pos.getY() + offset.y(), (double)pos.getZ() + offset.z(), 0.0D, 0.10000000149011612D, 0.0D);
            });
        }

        world.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    private static void setLit(LevelAccessor world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlock(pos, state.setValue(LIT, lit), 11);
    }

    static {
        LIT = BlockStateProperties.LIT;
    }
}
