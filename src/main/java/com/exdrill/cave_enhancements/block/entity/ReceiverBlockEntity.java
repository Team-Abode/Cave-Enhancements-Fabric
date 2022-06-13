package com.exdrill.cave_enhancements.block.entity;

import com.exdrill.cave_enhancements.block.OxidizableReceiverBlock;
import com.exdrill.cave_enhancements.block.ReceiverBlock;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ReceiverBlockEntity extends BlockEntity {

    public int poweredTicks = 0;

    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.RECEIVER_BLOCK_ENTITY, pos, state);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {
        ReceiverBlock block = (ReceiverBlock) state.getBlock();
        int maxPower = block.getMaxPower();

        if (state.getValue(ReceiverBlock.POWERED)) {
            this.poweredTicks++;
        } else {
            world.setBlockAndUpdate(pos, state.setValue(ReceiverBlock.CAN_PASS, false));
            poweredTicks = 0;
        }
        if (poweredTicks == maxPower) {
            world.setBlockAndUpdate(pos, state.setValue(ReceiverBlock.CAN_PASS, true));
        }

        List<Entity> list = world.getEntitiesOfClass(Entity.class, new AABB(pos).inflate(5), (e) -> true);

        if (block instanceof OxidizableReceiverBlock) {
            for (Entity entity : list) {
                if (entity instanceof LightningBolt && entity.tickCount % 5 == 0) {
                    Optional<BlockState> optionalReceiver = WeatheringCopper.getPrevious(state);
                    if (optionalReceiver.isPresent()) {
                        world.levelEvent(3005, pos, 0);
                        world.setBlock(pos, optionalReceiver.get(), 11);
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("PoweredTicks", this.poweredTicks);
        this.setChanged();
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.poweredTicks = nbt.getInt("PoweredTicks");
        super.load(nbt);
    }



}
