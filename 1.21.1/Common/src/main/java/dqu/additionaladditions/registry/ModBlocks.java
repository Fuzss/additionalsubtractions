package dqu.additionaladditions.registry;

import com.google.common.collect.ImmutableSet;
import dqu.additionaladditions.block.CopperPatinaBlock;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.block.PatinaBlock;
import dqu.additionaladditions.block.RopeBlock;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static final Holder.Reference<Block> ROPE = ModRegistry.REGISTRIES.registerBlock("rope",
            () -> new RopeBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .pushReaction(PushReaction.DESTROY)
                    .noCollission()
                    .sound(SoundType.WOOL)
                    .instabreak()));
    public static final Holder.Reference<Block> AMETHYST_LAMP = ModRegistry.REGISTRIES.registerBlock("amethyst_lamp",
            () -> new RedstoneLampBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_PURPLE)
                    .pushReaction(PushReaction.NORMAL)
                    .sound(SoundType.GLASS)
                    .strength(0.3F)));
    public static final Holder.Reference<Block> COPPER_PATINA = ModRegistry.REGISTRIES.registerBlock("copper_patina",
            () -> new CopperPatinaBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .pushReaction(PushReaction.DESTROY)
                    .noCollission()
                    .sound(SoundType.TUFF)
                    .instabreak()));
    public static final Holder.Reference<Block> GLOW_STICK = ModRegistry.REGISTRIES.registerBlock("glow_stick",
            () -> new GlowStickBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NONE)
                    .pushReaction(PushReaction.DESTROY)
                    .noCollission()
                    .lightLevel((BlockState blockState) -> 12)
                    .instabreak()));
    public static final Holder.Reference<Block> PATINA_BLOCK = ModRegistry.REGISTRIES.registerBlock("patina_block",
            () -> new PatinaBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .pushReaction(PushReaction.NORMAL)
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.5F)));

    public static final Holder.Reference<PoiType> AMETHYST_LAMP_POI_TYPE = ModRegistry.REGISTRIES.registerPoiType(
            "amethyst_lamp",
            () -> ModBlocks.AMETHYST_LAMP.value()
                    .getStateDefinition()
                    .getPossibleStates()
                    .stream()
                    .filter((BlockState blockState) -> blockState.getValue(BlockStateProperties.LIT))
                    .collect(ImmutableSet.toImmutableSet()),
            0,
            1);

    public static void bootstrap() {
        // NO-OP
    }
}
