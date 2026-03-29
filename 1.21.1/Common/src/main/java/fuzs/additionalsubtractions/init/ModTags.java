package fuzs.additionalsubtractions.init;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final TagKey<Item> MUSIC_DISCS_ITEM_TAG = TagFactory.COMMON.registerItemTag("music_discs");
    static final TagFactory TAGS = TagFactory.make(AdditionalSubtractions.MOD_ID);
    public static final TagKey<Block> SPIKE_TRAPS_BLOCK_TAG = TAGS.registerBlockTag("spike_traps");
    public static final TagKey<Block> SPIKES_BLOCK_TAG = TAGS.registerBlockTag("spikes");
    public static final TagKey<Block> BRAZIERS_BLOCK_TAG = TAGS.registerBlockTag("braziers");
    public static final TagKey<Block> PEDESTALS_BLOCK_TAG = TAGS.registerBlockTag("pedestals");
    public static final TagKey<Block> ROTATABLE_BLOCK_TAG = TAGS.registerBlockTag("rotatable");

    public static void bootstrap() {
        // NO-OP
    }
}
