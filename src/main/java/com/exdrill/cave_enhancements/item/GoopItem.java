package com.exdrill.cave_enhancements.item;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class GoopItem extends Item {

    public GoopItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        BlockPlaceContext blockPlaceContext = new BlockPlaceContext(context);
        if (!blockPlaceContext.canPlace()) return InteractionResult.FAIL;

        Level level = blockPlaceContext.getLevel();
        BlockPos pos = blockPlaceContext.getClickedPos();
        Player player = blockPlaceContext.getPlayer();
        ItemStack itemStack = blockPlaceContext.getItemInHand();

        BlockState state = ModBlocks.GOOP_SPLAT.getStateForPlacement(blockPlaceContext);
        BlockState clickedState = level.getBlockState(pos);

        if (state != null) {
            RandomSource randomSource = level.getRandom();

            level.setBlock(pos, state, 11);
            level.playSound(player, pos, ModSounds.BLOCK_GOOP_DECORATION_PLACE, SoundSource.BLOCKS, 0.5F, randomSource.nextFloat() * 0.2F + 0.9F);
            level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, clickedState));

            if (player instanceof ServerPlayer serverPlayer) CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos, itemStack);
            clickedState.getBlock().setPlacedBy(level, pos, clickedState, player, itemStack);

            if (player == null || !player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
