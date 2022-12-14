package com.teamabode.cave_enhancements.core.data;

import com.teamabode.cave_enhancements.core.data.server.RecipeGenerator;
import com.teamabode.cave_enhancements.core.data.server.TagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CaveEnhancementsData implements DataGeneratorEntrypoint {

    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(RecipeGenerator::new);
        fabricDataGenerator.addProvider(TagGenerator.BlockTagGenerator::new);
        fabricDataGenerator.addProvider(TagGenerator.ItemTagGenerator::new);
        fabricDataGenerator.addProvider(TagGenerator.EntityTypeTagGenerator::new);
    }
}
