package fuzs.additionalsubtractions.data.tags;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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
        this.add(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.CROSSBOW_WITH_SPYGLASS.value());
        this.add(ItemTags.CROSSBOW_ENCHANTABLE).add(ModItems.CROSSBOW_WITH_SPYGLASS.value());
        this.add(ItemTags.FOOT_ARMOR).add(ModItems.ROSE_GOLD_BOOTS.value());
        this.add(ItemTags.LEG_ARMOR).add(ModItems.ROSE_GOLD_LEGGINGS.value());
        this.add(ItemTags.CHEST_ARMOR).add(ModItems.ROSE_GOLD_CHESTPLATE.value());
        this.add(ItemTags.HEAD_ARMOR).add(ModItems.ROSE_GOLD_HELMET.value());
        this.add(ItemTags.CLUSTER_MAX_HARVESTABLES).add(ModItems.ROSE_GOLD_PICKAXE.value());
        this.add(ItemTags.SWORDS).add(ModItems.ROSE_GOLD_SWORD.value());
        this.add(ItemTags.AXES).add(ModItems.ROSE_GOLD_AXE.value());
        this.add(ItemTags.PICKAXES).add(ModItems.ROSE_GOLD_PICKAXE.value());
        this.add(ItemTags.SHOVELS).add(ModItems.ROSE_GOLD_SHOVEL.value());
        this.add(ItemTags.HOES).add(ModItems.ROSE_GOLD_HOE.value());
        this.add(ItemTags.PIG_FOOD).add(Items.POISONOUS_POTATO, ModItems.HEARTBEET.value());
    }
}
