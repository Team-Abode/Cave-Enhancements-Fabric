package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.block.ReceiverBlock;
import com.exdrill.cave_enhancements.registry.ModTags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {

    @Inject(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModTags.RECEIVERS)) {
            Direction direction = state.getValue(ReceiverBlock.FACING);
            cir.setReturnValue(direction == dir || direction.getOpposite() == dir);
        }
    }
}
