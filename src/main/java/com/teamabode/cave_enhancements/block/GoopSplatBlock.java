package com.teamabode.cave_enhancements.block;

import com.teamabode.cave_enhancements.registry.ModItems;
import net.minecraft.world.item.Item;

public class GoopSplatBlock extends SplatBlock {
    public GoopSplatBlock(Properties properties) {
        super(properties);
    }

    public Item asItem() {
        return ModItems.GOOP;
    }
}
