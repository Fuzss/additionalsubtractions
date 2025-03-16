package fuzs.additionalsubtractions.data.tags;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(BlockTags.CLIMBABLE).add(ModBlocks.ROPE.value());
        this.add(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.ROPE.value());
        this.add(BlockTags.SWORD_EFFICIENT).add(ModBlocks.ROPE.value());
        this.add(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.AMETHYST_LAMP.value(),
                        Blocks.REDSTONE_LAMP,
                        ModBlocks.OBSIDIAN_PRESSURE_PLATE.value(),
                        ModBlocks.COPPER_HOPPER.value());
        this.add(BlockTags.PRESSURE_PLATES).add(ModBlocks.OBSIDIAN_PRESSURE_PLATE.value());
        this.add(BlockTags.RAILS).add(ModBlocks.COPPER_RAIL.value());
        this.add(ModRegistry.ROTATABLE_BLOCK_TAG)
                .addTag(BlockTags.STANDING_SIGNS,
                        BlockTags.ANVIL,
                        BlockTags.STAIRS,
                        BlockTags.BAMBOO_BLOCKS,
                        BlockTags.SLABS,
                        BlockTags.TRAPDOORS,
                        BlockTags.BUTTONS,
                        BlockTags.CAMPFIRES,
                        BlockTags.LOGS,
                        BlockTags.FENCE_GATES,
                        BlockTags.SHULKER_BOXES)
                .add(Blocks.BARREL,
                        Blocks.BONE_BLOCK,
                        Blocks.SKELETON_SKULL,
                        Blocks.WITHER_SKELETON_SKULL,
                        Blocks.CREEPER_HEAD,
                        Blocks.ZOMBIE_HEAD,
                        Blocks.PLAYER_HEAD,
                        Blocks.DRAGON_HEAD,
                        Blocks.PIGLIN_HEAD,
                        Blocks.BEE_NEST,
                        Blocks.BEEHIVE,
                        Blocks.FURNACE,
                        Blocks.CHAIN,
                        Blocks.HAY_BLOCK,
                        Blocks.DEEPSLATE,
                        Blocks.CHISELED_BOOKSHELF,
                        Blocks.LOOM,
                        Blocks.DROPPER,
                        Blocks.REPEATER,
                        Blocks.COMPARATOR,
                        Blocks.MUDDY_MANGROVE_ROOTS,
                        Blocks.CARVED_PUMPKIN,
                        Blocks.PISTON,
                        Blocks.QUARTZ_PILLAR,
                        Blocks.OBSERVER,
                        Blocks.VERDANT_FROGLIGHT,
                        Blocks.STICKY_PISTON,
                        Blocks.BASALT,
                        Blocks.CALIBRATED_SCULK_SENSOR,
                        Blocks.SMOKER,
                        Blocks.POLISHED_BASALT,
                        Blocks.OCHRE_FROGLIGHT,
                        Blocks.PURPUR_PILLAR,
                        Blocks.DECORATED_POT,
                        Blocks.STONECUTTER,
                        Blocks.JACK_O_LANTERN,
                        Blocks.BLAST_FURNACE,
                        Blocks.DISPENSER,
                        Blocks.LEVER,
                        Blocks.BELL,
                        Blocks.LECTERN,
                        Blocks.PEARLESCENT_FROGLIGHT,
                        Blocks.GRINDSTONE,
                        Blocks.WHITE_GLAZED_TERRACOTTA,
                        Blocks.ORANGE_GLAZED_TERRACOTTA,
                        Blocks.MAGENTA_GLAZED_TERRACOTTA,
                        Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,
                        Blocks.YELLOW_GLAZED_TERRACOTTA,
                        Blocks.LIME_GLAZED_TERRACOTTA,
                        Blocks.PINK_GLAZED_TERRACOTTA,
                        Blocks.GRAY_GLAZED_TERRACOTTA,
                        Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
                        Blocks.CYAN_GLAZED_TERRACOTTA,
                        Blocks.PURPLE_GLAZED_TERRACOTTA,
                        Blocks.BLUE_GLAZED_TERRACOTTA,
                        Blocks.BROWN_GLAZED_TERRACOTTA,
                        Blocks.GREEN_GLAZED_TERRACOTTA,
                        Blocks.RED_GLAZED_TERRACOTTA,
                        Blocks.BLACK_GLAZED_TERRACOTTA);
    }
}
