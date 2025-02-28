package fuzs.additionalsubtractions.data;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.AMETHYST_LAMP.value())
                .define('#', Items.AMETHYST_SHARD)
                .define('X', Items.GLOWSTONE)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DEPTH_METER.value())
                .define('#', Items.COPPER_INGOT)
                .define('X', Items.REDSTONE)
                .pattern(" # ")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BERRY_PIE.value())
                .requires(Items.WHEAT)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.EGG)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES))
                .save(recipeOutput);
        this.foodCooking(recipeOutput, ModItems.FRIED_EGG.value(), Items.EGG);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.GLOW_STICK.value(), 3)
                .requires(Items.GLOW_INK_SAC)
                .requires(Items.STICK)
                .unlockedBy(getHasName(Items.GLOW_INK_SAC), has(Items.GLOW_INK_SAC))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHICKEN_NUGGET.value(), 3)
                .requires(Items.COOKED_CHICKEN)
                .unlockedBy(getHasName(Items.COOKED_CHICKEN), has(Items.COOKED_CHICKEN))
                .save(recipeOutput);
    }

    public void foodCooking(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient) {
        this.foodCooking(recipeOutput, result, ingredient, 0.35F, 200);
    }

    public void foodCooking(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, float experience, int baseCookingTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient),
                RecipeCategory.FOOD,
                result,
                experience,
                baseCookingTime).unlockedBy(getHasName(ingredient), has(ingredient)).save(recipeOutput);
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient),
                        RecipeCategory.FOOD,
                        result,
                        experience,
                        baseCookingTime / 2)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, getCraftingMethodRecipeName(result, RecipeSerializer.SMOKING_RECIPE));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient),
                        RecipeCategory.FOOD,
                        result,
                        experience,
                        baseCookingTime * 3)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, getCraftingMethodRecipeName(result, RecipeSerializer.CAMPFIRE_COOKING_RECIPE));
    }

    public static String getCraftingMethodRecipeName(ItemLike result, RecipeSerializer<?> recipeSerializer) {
        ResourceLocation resourceLocation = BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipeSerializer);
        Objects.requireNonNull(resourceLocation, "resource location is null");
        return getCraftingMethodRecipeName(result, resourceLocation.getPath());
    }

    public static String getCraftingMethodRecipeName(ItemLike result, String craftingMethod) {
        return getItemName(result) + "_from_" + craftingMethod;
    }
}
