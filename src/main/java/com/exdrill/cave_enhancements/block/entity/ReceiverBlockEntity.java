package com.exdrill.cave_enhancements.block.entity;

import com.exdrill.cave_enhancements.block.OxidizableReceiverBlock;
import com.exdrill.cave_enhancements.block.ReceiverBlock;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ReceiverBlockEntity extends BlockEntity {

    public int poweredTicks = 0;

    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.RECEIVER_BLOCK_ENTITY, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        ReceiverBlock block = (ReceiverBlock) state.getBlock();
        int maxPower = block.getMaxPower();

        if (state.get(ReceiverBlock.POWERED)) {
            this.poweredTicks++;
        } else {
            world.setBlockState(pos, state.with(ReceiverBlock.CAN_PASS, false));
            poweredTicks = 0;
        }
        if (poweredTicks == maxPower) {
            world.setBlockState(pos, state.with(ReceiverBlock.CAN_PASS, true));
        }

        List<Entity> list = world.getEntitiesByClass(Entity.class, new Box(pos).expand(5), (e) -> true);

        if (block instanceof OxidizableReceiverBlock) {
            for (Entity entity : list) {
                if (entity instanceof LightningEntity && entity.age % 5 == 0) {
                    Optional<BlockState> optionalReceiver = Oxidizable.getDecreasedOxidationState(state);
                    if (optionalReceiver.isPresent()) {
                        world.syncWorldEvent(3005, pos, 0);
                        world.setBlockState(pos, optionalReceiver.get(), 11);
                    }
                }
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("PoweredTicks", this.poweredTicks);
        this.markDirty();
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.poweredTicks = nbt.getInt("PoweredTicks");
        super.readNbt(nbt);
    }



}
