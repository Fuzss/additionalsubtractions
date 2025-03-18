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
        this.dropSelf(ModBlocks.OBSIDIAN_PRESSURE_PLATE.value());
        this.dropSelf(ModBlocks.COPPER_RAIL.value());
        this.dropSelf(ModBlocks.COPPER_HOPPER.value());
        this.dropSelf(ModBlocks.SOUL_JACK_O_LANTERN.value());
        this.dropSelf(ModBlocks.REDSTONE_JACK_O_LANTERN.value());
        this.dropSelf(ModBlocks.REDSTONE_LANTERN.value());
        this.dropSelf(ModBlocks.POLISHED_ANDESITE_PEDESTAL.value());
        this.dropSelf(ModBlocks.POLISHED_GRANITE_PEDESTAL.value());
        this.dropSelf(ModBlocks.POLISHED_DIORITE_PEDESTAL.value());
        this.dropSelf(ModBlocks.STONE_BRICK_PEDESTAL.value());
        this.dropSelf(ModBlocks.MOSSY_STONE_BRICK_PEDESTAL.value());
        this.dropSelf(ModBlocks.CRACKED_STONE_BRICK_PEDESTAL.value());
        this.dropSelf(ModBlocks.CUT_SANDSTONE_PEDESTAL.value());
        this.dropSelf(ModBlocks.CUT_RED_SANDSTONE_PEDESTAL.value());
        this.dropSelf(ModBlocks.CHISELED_SANDSTONE_PEDESTAL.value());
        this.dropSelf(ModBlocks.CHISELED_RED_SANDSTONE_PEDESTAL.value());
        this.dropSelf(ModBlocks.PRISMARINE_BRICK_PEDESTAL.value());
        this.dropSelf(ModBlocks.BLACKSTONE_PEDESTAL.value());
        this.dropSelf(ModBlocks.NETHER_BRICK_PEDESTAL.value());
        this.dropSelf(ModBlocks.PURPUR_BLOCK_PEDESTAL.value());
        this.dropSelf(ModBlocks.TIMER.value());
        this.dropSelf(ModBlocks.BOOKSHELF_SWITCH.value());
        this.dropSelf(ModBlocks.REDSTONE_CROSSING.value());
    }
}
