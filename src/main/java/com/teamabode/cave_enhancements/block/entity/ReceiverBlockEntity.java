package com.teamabode.cave_enhancements.block.entity;

import com.teamabode.cave_enhancements.block.ReceiverBlock;
import com.teamabode.cave_enhancements.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReceiverBlockEntity extends BlockEntity {

    public int timePoweredTicks = 0;
    public int output;

    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RECEIVER_BLOCK, pos, state);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {
        int requiredPowerDurationTicks = ((ReceiverBlock) state.getBlock()).getRequiredPowerDurationTicks();

        if (state.getValue(ReceiverBlock.POWERED)) {
            if(timePoweredTicks < requiredPowerDurationTicks) timePoweredTicks++;
        }else{
            timePoweredTicks = 0;
        }

        world.setBlockAndUpdate(pos, state.setValue(ReceiverBlock.CAN_PASS, timePoweredTicks == requiredPowerDurationTicks));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("PoweredTicks", this.timePoweredTicks);
        nbt.putInt("OutputSignal", this.output);

        setChanged();

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        timePoweredTicks = nbt.getInt("PoweredTicks");
        output = nbt.getInt("OutputSignal");

        super.load(nbt);
    }



}
