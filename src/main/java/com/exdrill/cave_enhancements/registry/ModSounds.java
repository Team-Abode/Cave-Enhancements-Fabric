package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

public class ModSounds {

    public static void init() {}

    public static SoundEvent init(String name) {
        SoundEvent event = new SoundEvent(new ResourceLocation(CaveEnhancements.MODID, name));
        return Registry.register(Registry.SOUND_EVENT, new ResourceLocation(CaveEnhancements.MODID, name), event);
    }

    // Glow Paste
    public static final SoundEvent ITEM_GLOW_PASTE_PLACE = init("item.glow_paste.place");

    // Goop Blocks
    public static final SoundEvent BLOCK_GOOP_BLOCK_BREAK = init("block.goop_block.break");
    public static final SoundEvent BLOCK_GOOP_BLOCK_PLACE = init("block.goop_block.place");
    public static final SoundEvent BLOCK_GOOP_BLOCK_HIT = init("block.goop_block.hit");
    public static final SoundEvent BLOCK_GOOP_BLOCK_STEP = init("block.goop_block.step");
    public static final SoundEvent BLOCK_GOOP_BLOCK_FALL = init("block.goop_block.fall");
    public static final SoundEvent BLOCK_GOOP_BLOCK_SLIDE = init("block.goop_block.slide");
    public static final SoundEvent BLOCK_GOOP_DECORATION_BREAK = init("block.goop_decoration.break");
    public static final SoundEvent BLOCK_GOOP_DECORATION_PLACE = init("block.goop_decoration.place");
    public static final SoundEvent BLOCK_GOOP_DECORATION_HIT = init("block.goop_decoration.hit");
    public static final SoundEvent BLOCK_GOOP_DECORATION_STEP = init("block.goop_decoration.step");
    public static final SoundEvent BLOCK_GOOP_DECORATION_FALL = init("block.goop_decoration.fall");

    // Goop
    public static final SoundEvent ENTITY_GOOP_HURT = init("entity.goop.hurt");
    public static final SoundEvent ENTITY_GOOP_DEATH = init("entity.goop.death");

    // Dripstone Tortoise
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_HURT = init("entity.dripstone_tortoise.hurt");
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_DEATH = init("entity.dripstone_tortoise.death");
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_STEP = init("entity.dripstone_tortoise.step");
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_IDLE = init("entity.dripstone_tortoise.idle");

    // Misc
    public static final SoundEvent ITEM_BUCKET_FILL_GOOP = init("item.bucket.fill.goop");
    public static final SoundEvent ITEM_BUCKET_EMPTY_GOOP = init("item.bucket.empty.goop");

    // Block Sound Types
    public static final SoundType GOOP_BLOCK = new SoundType(1.0F, 1.0F, BLOCK_GOOP_BLOCK_BREAK, BLOCK_GOOP_BLOCK_STEP, BLOCK_GOOP_BLOCK_PLACE, BLOCK_GOOP_BLOCK_HIT, BLOCK_GOOP_BLOCK_FALL);
    public static final SoundType GOOP_DECORATION = new SoundType(1.0F, 1.0F, BLOCK_GOOP_DECORATION_BREAK, BLOCK_GOOP_DECORATION_STEP, BLOCK_GOOP_DECORATION_PLACE, BLOCK_GOOP_DECORATION_HIT, BLOCK_GOOP_DECORATION_FALL);
}
