package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class VolatileGoopBlock extends Block {


    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public VolatileGoopBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Player player = context.getPlayer();
        if (player.isCrouching()) {
            return this.defaultBlockState().setValue(FACING, context.getClickedFace());
        }
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE)) {
            Direction direction = state.getValue(FACING);

            for (int i = 0; i < 5; i++) {

                if (level.isClientSide()) {
                    level.addParticle(ModParticles.GOOP_EXPLOSION, pos.relative(direction, i).getX() + 0.5, pos.relative(direction, i).getY() + 0.5, pos.relative(direction, i).getZ() + 0.5, 0, 0, 0);
                }
                this.explode(level, null, pos.relative(direction, i).getX() + 0.5, pos.relative(direction, i).getY() + 0.5, pos.relative(direction, i).getZ() + 0.5, 1.5F, false, Explosion.BlockInteraction.BREAK);
               //level.explode(null, pos.relative(direction, i).getX() + 0.5, pos.relative(direction, i).getY() + 0.5, pos.relative(direction, i).getZ() + 0.5, 1.5F, false, Explosion.BlockInteraction.BREAK);
            }

            if (!player.isCreative()) {
                if (stack.is(Items.FLINT_AND_STEEL)) {
                    stack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(hand));
                } else {
                    stack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public Explosion explode(Level level, @Nullable Entity exploder, double x, double y, double z, float size, boolean causesFire, Explosion.BlockInteraction mode) {
        Explosion explosion = new Explosion(level, exploder, x, y, z, size, causesFire, mode);
        explosion.explode();
        explosion.finalizeExplosion(false);
        return explosion;
    }

}
