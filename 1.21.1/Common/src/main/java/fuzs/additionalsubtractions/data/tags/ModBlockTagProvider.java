package fuzs.additionalsubtractions.data.tags;

import fuzs.additionalsubtractions.init.ModBlocks;
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
        this.add(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.AMETHYST_LAMP.value(), Blocks.REDSTONE_LAMP);
    }
}
