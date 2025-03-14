package fuzs.additionalsubtractions.world.item.crafting;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ModFireworkStarRecipe extends FireworkStarRecipe {
    private static final Ingredient SHAPE_INGREDIENT = Ingredient.of(ModItems.COPPER_PATINA.value(),
            ModItems.HEARTBEET.value());
    private static final Ingredient TRAIL_INGREDIENT = Ingredient.of(Items.DIAMOND);
    private static final Ingredient TWINKLE_INGREDIENT = Ingredient.of(Items.GLOWSTONE_DUST);
    private static final Map<Item, FireworkExplosion.Shape> SHAPE_BY_ITEM = Util.make(new HashMap<>(),
            (Map<Item, FireworkExplosion.Shape> shapeMap) -> {
                shapeMap.put(ModItems.COPPER_PATINA.value(), ModRegistry.BOLT_EXPLOSION_SHAPE.get());
                shapeMap.put(ModItems.HEARTBEET.value(), ModRegistry.HEART_EXPLOSION_SHAPE.get());
            });
    private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(Items.GUNPOWDER);

    public ModFireworkStarRecipe(CraftingBookCategory category) {
        super(category);
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
                if (SHAPE_INGREDIENT.test(itemStack)) {
                    if (bl3) {
                        return false;
                    }

                    bl3 = true;
                } else if (TWINKLE_INGREDIENT.test(itemStack)) {
                    if (bl5) {
                        return false;
                    }

                    bl5 = true;
                } else if (TRAIL_INGREDIENT.test(itemStack)) {
                    if (bl4) {
                        return false;
                    }

                    bl4 = true;
                } else if (GUNPOWDER_INGREDIENT.test(itemStack)) {
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
                if (SHAPE_INGREDIENT.test(itemStack)) {
                    shape = SHAPE_BY_ITEM.get(itemStack.getItem());
                } else if (TWINKLE_INGREDIENT.test(itemStack)) {
                    bl = true;
                } else if (TRAIL_INGREDIENT.test(itemStack)) {
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
