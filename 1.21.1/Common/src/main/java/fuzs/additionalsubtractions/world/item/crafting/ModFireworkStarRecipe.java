package fuzs.additionalsubtractions.world.item.crafting;

import com.google.common.collect.ImmutableMap;
import fuzs.additionalsubtractions.init.ModEnumConstants;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Map;

/**
 * Copied from super, only altering shape ingredients.
 */
public class ModFireworkStarRecipe extends FireworkStarRecipe {
    private final Ingredient shapeIngredient;
    private final Ingredient trailIngredient;
    private final Ingredient twinkleIngredient;
    private final Map<Item, FireworkExplosion.Shape> shapeByItem;
    private final Ingredient gunpowderIngredient;

    public ModFireworkStarRecipe(CraftingBookCategory category) {
        this(category,
                Ingredient.of(Items.DIAMOND),
                Ingredient.of(Items.GLOWSTONE_DUST),
                Ingredient.of(Items.GUNPOWDER),
                ImmutableMap.of(ModItems.COPPER_PATINA.value(),
                        ModEnumConstants.BOLT_EXPLOSION_SHAPE,
                        ModItems.HEARTBEET.value(),
                        ModEnumConstants.HEART_EXPLOSION_SHAPE));
    }

    public ModFireworkStarRecipe(CraftingBookCategory category, Ingredient trailIngredient, Ingredient twinkleIngredient, Ingredient gunpowderIngredient, Map<Item, FireworkExplosion.Shape> shapeByItem) {
        super(category);
        this.shapeIngredient = Ingredient.of(shapeByItem.keySet().stream().map(ItemStack::new));
        this.trailIngredient = trailIngredient;
        this.twinkleIngredient = twinkleIngredient;
        this.shapeByItem = shapeByItem;
        this.gunpowderIngredient = gunpowderIngredient;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;

        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getItem(i);
            if (!itemStack.isEmpty()) {
                if (this.shapeIngredient.test(itemStack)) {
                    if (bl3) {
                        return false;
                    }

                    bl3 = true;
                } else if (this.twinkleIngredient.test(itemStack)) {
                    if (bl5) {
                        return false;
                    }

                    bl5 = true;
                } else if (this.trailIngredient.test(itemStack)) {
                    if (bl4) {
                        return false;
                    }

                    bl4 = true;
                } else if (this.gunpowderIngredient.test(itemStack)) {
                    if (bl) {
                        return false;
                    }

                    bl = true;
                } else {
                    if (!(itemStack.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    bl2 = true;
                }
            }
        }

        // require shape ingredient to be present via the third boolean, otherwise this should always use vanilla
        return bl && bl2 && bl3;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        FireworkExplosion.Shape shape = FireworkExplosion.Shape.SMALL_BALL;
        boolean bl = false;
        boolean bl2 = false;
        IntList intList = new IntArrayList();

        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getItem(i);
            if (!itemStack.isEmpty()) {
                if (this.shapeIngredient.test(itemStack)) {
                    shape = this.shapeByItem.get(itemStack.getItem());
                } else if (this.twinkleIngredient.test(itemStack)) {
                    bl = true;
                } else if (this.trailIngredient.test(itemStack)) {
                    bl2 = true;
                } else if (itemStack.getItem() instanceof DyeItem) {
                    intList.add(((DyeItem) itemStack.getItem()).getDyeColor().getFireworkColor());
                }
            }
        }

        ItemStack itemStack2 = new ItemStack(Items.FIREWORK_STAR);
        itemStack2.set(DataComponents.FIREWORK_EXPLOSION, new FireworkExplosion(shape, intList, IntList.of(), bl2, bl));
        return itemStack2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.FIREWORK_STAR_RECIPE_SERIALIZER.value();
    }
}
