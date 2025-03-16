package fuzs.additionalsubtractions.fabric.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class AdditionalSubtractionsFabricCore implements Runnable {

    @Override
    public void run() {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();
        // net/minecraft/world/item/component/FireworkExplosion$Shape
        String enumType = mappingResolver.mapClassName("intermediary", "net.minecraft.class_9283$class_1782");
        // setting the id like this is bad, it will not work with other mods that have the same one
        // unfortunately we cannot dynamically determine the size of the enum, so just hope no other mods expand this enum
        ClassTinkerers.enumBuilder(enumType, int.class, String.class)
                .addEnum("ADDITIONALSUBTRACTIONS_BOLT", 5, "additionalsubtractions:bolt")
                .addEnum("ADDITIONALSUBTRACTIONS_HEART", 6, "additionalsubtractions:heart")
                .build();
    }
}
