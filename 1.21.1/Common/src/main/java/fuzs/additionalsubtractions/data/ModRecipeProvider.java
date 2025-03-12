package fuzs.additionalsubtractions.data;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SWEET_BERRY_PIE.value())
                .requires(Items.WHEAT)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.EGG)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.CROSSBOW_WITH_SPYGLASS.value())
                .requires(Items.SPYGLASS)
                .requires(Items.CROSSBOW)
                .unlockedBy(getHasName(Items.SPYGLASS), has(Items.SPYGLASS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DEPTH_METER.value())
                .define('#', Items.COPPER_INGOT)
                .define('X', Items.REDSTONE)
                .pattern(" # ")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(recipeOutput);
        this.foodCooking(recipeOutput, ModItems.FRIED_EGG.value(), Items.EGG);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.GLOW_STICK.value(), 4)
                .requires(Items.GLOW_INK_SAC)
                .requires(Items.STICK)
                .unlockedBy(getHasName(Items.GLOW_INK_SAC), has(Items.GLOW_INK_SAC))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHICKEN_NUGGET.value(), 3)
                .requires(Items.COOKED_CHICKEN)
                .unlockedBy(getHasName(Items.COOKED_CHICKEN), has(Items.COOKED_CHICKEN))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.HONEYED_APPLE.value())
                .requires(Items.APPLE)
                .requires(Items.HONEY_BOTTLE)
                .unlockedBy(getHasName(Items.HONEY_BOTTLE), has(Items.HONEY_BOTTLE))
                .save(recipeOutput);
        twoByTwoPacker(recipeOutput,
                RecipeCategory.BUILDING_BLOCKS,
                ModItems.PATINA_BLOCK.value(),
                ModItems.COPPER_PATINA.value());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CYAN_DYE)
                .requires(ModItems.COPPER_PATINA.value())
                .group("cyan_dye")
                .unlockedBy(getHasName(ModItems.COPPER_PATINA.value()), has(ModItems.COPPER_PATINA.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.POCKET_JUKEBOX.value())
                .define('#', Items.REDSTONE)
                .define('X', Items.JUKEBOX)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy(getHasName(Items.JUKEBOX), has(Items.JUKEBOX))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Blocks.POWERED_RAIL, 6)
                .define('R', Items.REDSTONE)
                .define('#', Items.STICK)
                .define('X', Items.COPPER_INGOT)
                .pattern("X X")
                .pattern("X#X")
                .pattern("XRX")
                .unlockedBy(getHasName(Blocks.RAIL), has(Blocks.RAIL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.ROPE.value())
                .define('X', Items.STRING)
                .pattern("X")
                .pattern("X")
                .pattern("X")
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ROSE_GOLD_ALLOY.value())
                .requires(Items.GOLD_INGOT)
                .requires(Items.GOLD_INGOT)
                .requires(Items.GOLD_INGOT)
                .requires(Items.GOLD_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(recipeOutput);
        roseGoldSmithing(recipeOutput,
                Items.IRON_CHESTPLATE,
                RecipeCategory.COMBAT,
                ModItems.ROSE_GOLD_CHESTPLATE.value());
        roseGoldSmithing(recipeOutput, Items.IRON_LEGGINGS, RecipeCategory.COMBAT, ModItems.ROSE_GOLD_LEGGINGS.value());
        roseGoldSmithing(recipeOutput, Items.IRON_HELMET, RecipeCategory.COMBAT, ModItems.ROSE_GOLD_HELMET.value());
        roseGoldSmithing(recipeOutput, Items.IRON_BOOTS, RecipeCategory.COMBAT, ModItems.ROSE_GOLD_BOOTS.value());
        roseGoldSmithing(recipeOutput, Items.IRON_SWORD, RecipeCategory.COMBAT, ModItems.ROSE_GOLD_SWORD.value());
        roseGoldSmithing(recipeOutput, Items.IRON_AXE, RecipeCategory.TOOLS, ModItems.ROSE_GOLD_AXE.value());
        roseGoldSmithing(recipeOutput, Items.IRON_PICKAXE, RecipeCategory.TOOLS, ModItems.ROSE_GOLD_PICKAXE.value());
        roseGoldSmithing(recipeOutput, Items.IRON_HOE, RecipeCategory.TOOLS, ModItems.ROSE_GOLD_HOE.value());
        roseGoldSmithing(recipeOutput, Items.IRON_SHOVEL, RecipeCategory.TOOLS, ModItems.ROSE_GOLD_SHOVEL.value());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value(), 2)
                .define('#', Items.IRON_INGOT)
                .define('C', Items.COBBLESTONE)
                .define('S', ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value())
                .pattern("#S#")
                .pattern("#C#")
                .pattern("###")
                .unlockedBy(getHasName(ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()),
                        has(ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.TRIDENT)
                .define('#', Items.PRISMARINE_SHARD)
                .define('X', ModItems.TRIDENT_SHARD.value())
                .pattern(" XX")
                .pattern(" #X")
                .pattern("#  ")
                .unlockedBy(getHasName(ModItems.TRIDENT_SHARD.value()), has(ModItems.TRIDENT_SHARD.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WATERING_CAN.value())
                .define('#', Items.COPPER_INGOT)
                .define('X', Items.BUCKET)
                .pattern("  #")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy(getHasName(Items.BUCKET), has(Items.BUCKET))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WRENCH.value())
                .define('#', Items.COPPER_INGOT)
                .pattern("# #")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(recipeOutput);
    }

    public static void roseGoldSmithing(RecipeOutput recipeOutput, Item ingredientItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()),
                        Ingredient.of(ingredientItem),
                        Ingredient.of(ModItems.ROSE_GOLD_ALLOY.value()),
                        category,
                        resultItem)
                .unlocks(getHasName(ModItems.ROSE_GOLD_ALLOY.value()), has(ModItems.ROSE_GOLD_ALLOY.value()))
                .save(recipeOutput, getSmithingRecipeName(resultItem));
    }

    public static void smithing(RecipeOutput recipeOutput, Item ingredientItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()),
                        Ingredient.of(ingredientItem),
                        Ingredient.of(ModItems.ROSE_GOLD_ALLOY.value()),
                        category,
                        resultItem)
                .unlocks(getHasName(ModItems.ROSE_GOLD_ALLOY.value()), has(ModItems.ROSE_GOLD_ALLOY.value()))
                .save(recipeOutput, getSmithingRecipeName(resultItem));
    }

    public static String getSmithingRecipeName(ItemLike result) {
        return getItemName(result) + "_smithing";
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
