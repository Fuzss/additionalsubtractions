package fuzs.additionalsubtractions.data.loot;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModBlockLootProvider extends AbstractLootProvider.Blocks {

    public ModBlockLootProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addLootTables() {
        this.dropSelf(ModBlocks.ROPE.value());
        this.dropSelf(ModBlocks.AMETHYST_LAMP.value());
        this.dropSelf(ModBlocks.GLOW_STICK.value());
        this.dropSelf(ModBlocks.PATINA_BLOCK.value());
        this.dropSelf(ModBlocks.COPPER_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.OXIDIZED_COPPER_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.WAXED_COPPER_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.WAXED_EXPOSED_COPPER_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.WAXED_WEATHERED_COPPER_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.WAXED_OXIDIZED_COPPER_PRESSURE_PLATE.value());
    }
}
