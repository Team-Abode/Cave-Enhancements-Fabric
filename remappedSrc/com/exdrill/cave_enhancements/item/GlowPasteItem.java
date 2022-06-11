package com.exdrill.cave_enhancements.item;

import java.util.List;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.item.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public class GlowPasteItem extends Item {
    public GlowPasteItem(Properties settings) {
        super(settings);
    }

    public InteractionResult useOn(UseOnContext context) {
        InteractionResult actionResult = this.place(new BlockPlaceContext(context));
        if (!actionResult.consumesAction() && this.isEdible()) {
            InteractionResult actionResult2 = this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
            return actionResult2 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : actionResult2;
        } else {
            return actionResult;
        }
    }

    public InteractionResult place(BlockPlaceContext context) {
        if (!context.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext itemPlacementContext = this.getPlacementContext(context);
            if (itemPlacementContext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext);
                if (blockState == null) {
                    return InteractionResult.FAIL;
                } else if (!this.place(itemPlacementContext, blockState)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockPos = itemPlacementContext.getClickedPos();
                    Level world = itemPlacementContext.getLevel();
                    Player playerEntity = itemPlacementContext.getPlayer();
                    ItemStack itemStack = itemPlacementContext.getItemInHand();
                    BlockState blockState2 = world.getBlockState(blockPos);
                    if (blockState2.is(blockState.getBlock())) {
                        blockState2.getBlock().setPlacedBy(world, blockPos, blockState2, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerEntity, blockPos, itemStack);
                        }
                    }

                    world.gameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
                    if (playerEntity == null || !playerEntity.getAbilities().instabuild) {
                        itemStack.hurtAndBreak(1, playerEntity, (player) -> {
                            player.broadcastBreakEvent(context.getHand());
                        });
                    }
                    if (!world.isClientSide) {
                        world.playSound(null, blockPos, ModSounds.GLOW_PASTE_PLACE_SOUND_EVENT, SoundSource.BLOCKS, 1f, 1.1f);
                    }
                    return InteractionResult.sidedSuccess(world.isClientSide);
                }
            }
        }
    }

    @Nullable
    public BlockPlaceContext getPlacementContext(BlockPlaceContext context) {
        return context;
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockState = this.getBlock().getStateForPlacement(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return property.getValue(name).map((value) -> state.setValue(property, value)).orElse(state);
    }

    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        Player playerEntity = context.getPlayer();
        CollisionContext shapeContext = playerEntity == null ? CollisionContext.empty() : CollisionContext.of(playerEntity);
        return (!this.checkStatePlacement() || state.canSurvive(context.getLevel(), context.getClickedPos())) && context.getLevel().isUnobstructed(state, context.getClickedPos(), shapeContext);
    }

    protected boolean checkStatePlacement() {
        return true;
    }
    protected boolean place(BlockPlaceContext context, BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 11);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        this.getBlock().appendHoverText(stack, world, tooltip, context);
    }

    public Block getBlock() {
        return ModBlocks.GLOW_SPLOTCH;
    }
}