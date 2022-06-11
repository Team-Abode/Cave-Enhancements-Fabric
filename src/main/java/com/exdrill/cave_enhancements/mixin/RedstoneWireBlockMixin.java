package com.exdrill.cave_enhancements.mixin;

import com.exdrill.cave_enhancements.block.ReceiverBlock;
import com.exdrill.cave_enhancements.registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlockMixin {

    @Inject(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(ModTags.RECEIVERS)) {
            Direction direction = state.get(ReceiverBlock.FACING);
            cir.setReturnValue(direction == dir || direction.getOpposite() == dir);
        }
    }
}
