package fuzs.additionalsubtractions.data.tags;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        this.add(ItemTags.WOLF_FOOD).add(ModItems.CHICKEN_NUGGET.value());
        this.add(ModRegistry.MUSIC_DISCS_ITEM_TAG)
                .add(ModItems.MUSIC_DISC_0308.value(),
                        ModItems.MUSIC_DISC_1007.value(),
                        ModItems.MUSIC_DISC_1507.value());
    }
}
