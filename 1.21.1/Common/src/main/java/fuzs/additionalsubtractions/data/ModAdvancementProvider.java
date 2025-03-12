package fuzs.additionalsubtractions.data;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.data.v2.AbstractAdvancementProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;

import java.util.function.Consumer;

public class ModAdvancementProvider extends AbstractAdvancementProvider {
    public static final AdvancementToken ROOT_ADVANCEMENT = new AdvancementToken(AdditionalSubtractions.id("root"));
    public static final AdvancementToken OBTAIN_MUSIC_DISCS_ADVANCEMENT = new AdvancementToken(AdditionalSubtractions.id(
            "obtain_music_discs"));
    public static final AdvancementToken OBTAIN_GLOW_STICK_ADVANCEMENT = new AdvancementToken(AdditionalSubtractions.id(
            "obtain_glow_stick"));
    public static final AdvancementToken OBTAIN_MYSTERIOUS_BUNDLE_ADVANCEMENT = new AdvancementToken(
            AdditionalSubtractions.id("obtain_mysterious_bundle"));
    public static final AdvancementToken OBTAIN_POCKET_JUKEBOX_ADVANCEMENT = new AdvancementToken(AdditionalSubtractions.id(
            "obtain_pocket_jukebox"));
    public static final AdvancementToken OBTAIN_ROSE_GOLD_ADVANCEMENT = new AdvancementToken(AdditionalSubtractions.id(
            "obtain_rose_gold"));
    public static final AdvancementToken SHOOT_SCOPED_CROSSBOW_ADVANCEMENT = new AdvancementToken(AdditionalSubtractions.id(
            "shoot_scoped_crossbow"));
    public static final AdvancementToken SHOOT_SELF_SCOPED_CROSSBOW_ADVANCEMENT = new AdvancementToken(
            AdditionalSubtractions.id("shoot_self_scoped_crossbow"));
    public static final AdvancementToken USE_WATERING_CAN_ADVANCEMENT = new AdvancementToken(AdditionalSubtractions.id(
            "use_watering_can"));

    public ModAdvancementProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addAdvancements(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {

    }
}
