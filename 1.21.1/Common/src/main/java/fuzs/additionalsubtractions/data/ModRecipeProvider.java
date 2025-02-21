package fuzs.additionalsubtractions.data;

import dqu.additionaladditions.registry.ModItems;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHICKEN_NUGGET.value(), 3)
                .requires(Items.COOKED_CHICKEN)
                .unlockedBy(getHasName(Items.COOKED_CHICKEN), has(Items.COOKED_CHICKEN))
                .save(recipeOutput);
    }
}
