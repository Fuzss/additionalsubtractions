package fuzs.additionalsubtractions.init;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.FireworkExplosion;

import java.util.Locale;
import java.util.function.Function;

public class ModEnumConstants {
    public static final FireworkExplosion.Shape BOLT_EXPLOSION_SHAPE = getEnumConstant(AdditionalSubtractions.id("bolt"),
            FireworkExplosion.Shape::valueOf);
    public static final FireworkExplosion.Shape HEART_EXPLOSION_SHAPE = getEnumConstant(AdditionalSubtractions.id(
            "heart"), FireworkExplosion.Shape::valueOf);

    public static void bootstrap() {
        // NO-OP
    }

    static <E extends Enum<E>> E getEnumConstant(ResourceLocation resourceLocation, Function<String, E> valueOfInvoker) {
        return valueOfInvoker.apply(resourceLocation.toDebugFileName().toUpperCase(Locale.ROOT));
    }
}
