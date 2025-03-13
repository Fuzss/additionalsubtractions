package fuzs.additionalsubtractions.world.item;

import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

/**
 * Do this by copying the armor material to the proper namespace in the future.
 */
@Deprecated
public class ModAnimalArmorItem extends AnimalArmorItem {

    public ModAnimalArmorItem(Holder<ArmorMaterial> armorMaterial, BodyType bodyType, boolean hasOverlay, Properties properties) {
        super(armorMaterial, bodyType, hasOverlay, properties);
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocationHelper.fromNamespaceAndPath(this.builtInRegistryHolder().key().location().getNamespace(),
                super.getTexture().getPath());
    }

    @Override
    public @Nullable ResourceLocation getOverlayTexture() {
        ResourceLocation resourceLocation = super.getOverlayTexture();
        if (resourceLocation != null) {
            return ResourceLocationHelper.fromNamespaceAndPath(this.builtInRegistryHolder()
                    .key()
                    .location()
                    .getNamespace(), resourceLocation.getPath());
        } else {
            return null;
        }
    }
}
