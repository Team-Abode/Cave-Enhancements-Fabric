package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

public class ModSounds {

    public static void init() {}

    public static SoundEvent register(String name) {
        SoundEvent event = new SoundEvent(new ResourceLocation(CaveEnhancements.MODID, name));
        return Registry.register(Registry.SOUND_EVENT, new ResourceLocation(CaveEnhancements.MODID, name), event);
    }

    // Glow Paste
    public static final SoundEvent ITEM_GLOW_PASTE_PLACE = register("item.glow_paste.place");
    public static final SoundEvent ITEM_AMETHYST_FLUTE_USE = register("item.amethyst_flute.use");

    // Goop Blocks
    public static final SoundEvent BLOCK_GOOP_BLOCK_BREAK = register("block.goop_block.break");
    public static final SoundEvent BLOCK_GOOP_BLOCK_PLACE = register("block.goop_block.place");
    public static final SoundEvent BLOCK_GOOP_BLOCK_HIT = register("block.goop_block.hit");
    public static final SoundEvent BLOCK_GOOP_BLOCK_STEP = register("block.goop_block.step");
    public static final SoundEvent BLOCK_GOOP_BLOCK_FALL = register("block.goop_block.fall");
    public static final SoundEvent BLOCK_GOOP_BLOCK_SLIDE = register("block.goop_block.slide");
    public static final SoundEvent BLOCK_GOOP_DECORATION_BREAK = register("block.goop_decoration.break");
    public static final SoundEvent BLOCK_GOOP_DECORATION_PLACE = register("block.goop_decoration.place");
    public static final SoundEvent BLOCK_GOOP_DECORATION_HIT = register("block.goop_decoration.hit");
    public static final SoundEvent BLOCK_GOOP_DECORATION_STEP = register("block.goop_decoration.step");
    public static final SoundEvent BLOCK_GOOP_DECORATION_FALL = register("block.goop_decoration.fall");

    // Goop
    public static final SoundEvent ENTITY_GOOP_HURT = register("entity.goop.hurt");
    public static final SoundEvent ENTITY_GOOP_DEATH = register("entity.goop.death");
    public static final SoundEvent ITEM_GOOP_THROW = register("item.goop.throw");

    // Dripstone Tortoise
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_HURT = register("entity.dripstone_tortoise.hurt");
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_DEATH = register("entity.dripstone_tortoise.death");
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_STEP = register("entity.dripstone_tortoise.step");
    public static final SoundEvent ENTITY_DRIPSTONE_TORTOISE_IDLE = register("entity.dripstone_tortoise.idle");

    // Misc
    public static final SoundEvent ITEM_BUCKET_FILL_GOOP = register("item.bucket.fill.goop");
    public static final SoundEvent ITEM_BUCKET_EMPTY_GOOP = register("item.bucket.empty.goop");
    public static final SoundEvent BLOCK_ROSE_QUARTZ_CHIMES_CHIME = register("block.rose_quartz_chimes.chime");

    // Block Sound Types
    public static final SoundType GOOP_BLOCK = new SoundType(1.0F, 1.0F, BLOCK_GOOP_BLOCK_BREAK, BLOCK_GOOP_BLOCK_STEP, BLOCK_GOOP_BLOCK_PLACE, BLOCK_GOOP_BLOCK_HIT, BLOCK_GOOP_BLOCK_FALL);
    public static final SoundType GOOP_DECORATION = new SoundType(1.0F, 1.0F, BLOCK_GOOP_DECORATION_BREAK, BLOCK_GOOP_DECORATION_STEP, BLOCK_GOOP_DECORATION_PLACE, BLOCK_GOOP_DECORATION_HIT, BLOCK_GOOP_DECORATION_FALL);
}
