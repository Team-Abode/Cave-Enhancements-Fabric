package com.teamabode.cave_enhancements.block;

import com.teamabode.cave_enhancements.registry.ModItems;
import net.minecraft.world.item.Item;

public class GlowSplotchBlock extends SplatBlock {
    public GlowSplotchBlock(Properties properties) {
        super(properties);
    }

    public Item asItem() {
        return ModItems.GLOW_PASTE;
    }
}
