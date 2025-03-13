package fuzs.additionalsubtractions.init;

import com.google.common.collect.ImmutableSet;
import fuzs.additionalsubtractions.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public class ModBlocks {
    /**
     * A block model offset function for a model of type {@code builtin/generated} for rendering attached to surrounding
     * block faces.
     */
    static final BlockBehaviour.OffsetFunction FACE_ATTACHED_ITEM_MODEL_OFFSET = (BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) -> {
        return blockState.getOptionalValue(BlockStateProperties.FACING)
                .map(Direction::getOpposite)
                .map((Direction direction) -> {
                    // the model is one pixel wide (1/16) and is exactly centered, so we have to move it by 7.5 pixels
                    return new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(
                            15.0 / 32.0);
                })
                .orElse(Vec3.ZERO);
    };

    public static final Holder.Reference<Block> ROPE = ModRegistry.REGISTRIES.registerBlock("rope",
            RopeBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .pushReaction(PushReaction.DESTROY)
                    .noCollission()
                    .sound(SoundType.WOOL)
                    .instabreak());
    public static final Holder.Reference<Block> AMETHYST_LAMP = ModRegistry.REGISTRIES.registerBlock("amethyst_lamp",
            RedstoneLampBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP)
                    .mapColor(MapColor.TERRACOTTA_PURPLE)
                    .lightLevel((BlockState blockState) -> 0));
    public static final Holder.Reference<Block> GLOW_STICK = ModRegistry.REGISTRIES.registerBlock("glow_stick",
            GlowStickBlock::new,
            () -> offsetFunction(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NONE)
                    .sound(SoundType.GLASS)
                    .pushReaction(PushReaction.DESTROY)
                    .noCollission()
                    .lightLevel((BlockState blockState) -> 15)
                    .instabreak(), FACE_ATTACHED_ITEM_MODEL_OFFSET));
    public static final Holder.Reference<Block> PATINA_BLOCK = ModRegistry.REGISTRIES.registerBlock("patina_block",
            PatinaBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .instrument(NoteBlockInstrument.SNARE)
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.5F));
    public static final Holder.Reference<Block> COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new WeatheringCopperPressurePlateBlock(BlockSetType.COPPER,
                    WeatheringCopper.WeatherState.UNAFFECTED,
                    properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(Blocks.COPPER_BLOCK.defaultMapColor())
                    .forceSolidOn()
                    .requiresCorrectToolForDrops()
                    .noCollission()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY));
    public static final Holder.Reference<Block> EXPOSED_COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "exposed_copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new WeatheringCopperPressurePlateBlock(BlockSetType.COPPER,
                    WeatheringCopper.WeatherState.EXPOSED,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(COPPER_PRESSURE_PLATE.value())
                    .mapColor(Blocks.EXPOSED_COPPER.defaultMapColor()));
    public static final Holder.Reference<Block> WEATHERED_COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "weathered_copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new WeatheringCopperPressurePlateBlock(BlockSetType.COPPER,
                    WeatheringCopper.WeatherState.WEATHERED,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(COPPER_PRESSURE_PLATE.value())
                    .mapColor(Blocks.WEATHERED_COPPER.defaultMapColor()));
    public static final Holder.Reference<Block> OXIDIZED_COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "oxidized_copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new WeatheringCopperPressurePlateBlock(BlockSetType.COPPER,
                    WeatheringCopper.WeatherState.OXIDIZED,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(COPPER_PRESSURE_PLATE.value())
                    .mapColor(Blocks.OXIDIZED_COPPER.defaultMapColor()));
    public static final Holder.Reference<Block> WAXED_COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "waxed_copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new PlayerPressurePlateBlock(BlockSetType.COPPER, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(COPPER_PRESSURE_PLATE.value()));
    public static final Holder.Reference<Block> WAXED_EXPOSED_COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "waxed_exposed_copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new PlayerPressurePlateBlock(BlockSetType.COPPER, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(EXPOSED_COPPER_PRESSURE_PLATE.value()));
    public static final Holder.Reference<Block> WAXED_WEATHERED_COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "waxed_weathered_copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new PlayerPressurePlateBlock(BlockSetType.COPPER, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(WEATHERED_COPPER_PRESSURE_PLATE.value()));
    public static final Holder.Reference<Block> WAXED_OXIDIZED_COPPER_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "waxed_oxidized_copper_pressure_plate",
            (BlockBehaviour.Properties properties) -> new PlayerPressurePlateBlock(BlockSetType.COPPER, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(OXIDIZED_COPPER_PRESSURE_PLATE.value()));

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

    static BlockBehaviour.Properties offsetFunction(BlockBehaviour.Properties properties, BlockBehaviour.OffsetFunction offsetFunction) {
        properties.offsetFunction = offsetFunction;
        return properties;
    }
}
