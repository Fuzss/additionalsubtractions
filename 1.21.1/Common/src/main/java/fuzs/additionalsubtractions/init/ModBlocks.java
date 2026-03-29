package fuzs.additionalsubtractions.init;

import com.google.common.collect.ImmutableSet;
import fuzs.additionalsubtractions.world.level.block.*;
import fuzs.additionalsubtractions.world.level.block.entity.CopperHopperBlockEntity;
import fuzs.additionalsubtractions.world.level.block.entity.PedestalBlockEntity;
import fuzs.additionalsubtractions.world.level.block.entity.TimerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
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
    public static final Holder.Reference<Block> OBSIDIAN_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlock(
            "obsidian_pressure_plate",
            (BlockBehaviour.Properties properties) -> new PlayerPressurePlateBlock(BlockSetType.POLISHED_BLACKSTONE,
                    properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .noCollission()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY));
    public static final Holder.Reference<Block> COPPER_RAIL = ModRegistry.REGISTRIES.whenOnFabricLike()
            .registerBlock("copper_rail",
                    PoweredRailBlock::new,
                    () -> BlockBehaviour.Properties.of().noCollission().strength(0.7F).sound(SoundType.METAL));
    public static final Holder.Reference<Block> COPPER_HOPPER = ModRegistry.REGISTRIES.registerBlock("copper_hopper",
            CopperHopperBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 4.8F)
                    .sound(SoundType.COPPER)
                    .noOcclusion());
    public static final Holder.Reference<Block> SOUL_JACK_O_LANTERN = ModRegistry.REGISTRIES.registerBlock(
            "soul_jack_o_lantern",
            CarvedPumpkinBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN));
    public static final Holder.Reference<Block> REDSTONE_JACK_O_LANTERN = ModRegistry.REGISTRIES.registerBlock(
            "redstone_jack_o_lantern",
            RedstoneCarvedPumpkinBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN)
                    .lightLevel(Blocks.litBlockEmission(15))
                    .isRedstoneConductor(Blocks::never));
    public static final Holder.Reference<Block> REDSTONE_LANTERN = ModRegistry.REGISTRIES.registerBlock(
            "redstone_lantern",
            RedstoneLanternBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)
                    .lightLevel((BlockState blockState) -> blockState.getValue(BlockStateProperties.POWER)));
    public static final Holder.Reference<Block> POLISHED_ANDESITE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "polished_andesite_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE).forceSolidOn());
    public static final Holder.Reference<Block> POLISHED_GRANITE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "polished_granite_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_GRANITE).forceSolidOn());
    public static final Holder.Reference<Block> POLISHED_DIORITE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "polished_diorite_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE).forceSolidOn());
    public static final Holder.Reference<Block> STONE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "stone_brick_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).forceSolidOn());
    public static final Holder.Reference<Block> MOSSY_STONE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "mossy_stone_brick_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSSY_STONE_BRICKS).forceSolidOn());
    public static final Holder.Reference<Block> CRACKED_STONE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "cracked_stone_brick_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_STONE_BRICKS).forceSolidOn());
    public static final Holder.Reference<Block> CUT_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "cut_sandstone_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE).forceSolidOn());
    public static final Holder.Reference<Block> CUT_RED_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "cut_red_sandstone_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_SANDSTONE).forceSolidOn());
    public static final Holder.Reference<Block> CHISELED_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "chiseled_sandstone_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_SANDSTONE).forceSolidOn());
    public static final Holder.Reference<Block> CHISELED_RED_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "chiseled_red_sandstone_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_RED_SANDSTONE).forceSolidOn());
    public static final Holder.Reference<Block> PRISMARINE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "prismarine_brick_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE).forceSolidOn());
    public static final Holder.Reference<Block> BLACKSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "blackstone_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).forceSolidOn());
    public static final Holder.Reference<Block> NETHER_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "nether_brick_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).forceSolidOn());
    public static final Holder.Reference<Block> PURPUR_BLOCK_PEDESTAL = ModRegistry.REGISTRIES.registerBlock(
            "purpur_block_pedestal",
            PedestalBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_BLOCK).forceSolidOn());
    public static final Holder.Reference<Block> TIMER = ModRegistry.REGISTRIES.registerBlock("timer",
            TimerBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
    public static final Holder.Reference<Block> BOOKSHELF_SWITCH = ModRegistry.REGISTRIES.registerBlock(
            "bookshelf_switch",
            BookshelfSwitchBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.5F)
                    .sound(SoundType.WOOD)
                    .requiresCorrectToolForDrops()
                    .isRedstoneConductor(Blocks::never));
    public static final Holder.Reference<Block> REDSTONE_CROSSING = ModRegistry.REGISTRIES.registerBlock(
            "redstone_crossing",
            RedstoneCrossingBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .instabreak()
                    .sound(SoundType.STONE)
                    .pushReaction(PushReaction.DESTROY));
    public static final Holder.Reference<Block> NETHER_BRICK_FENCE_GATE = ModRegistry.REGISTRIES.registerBlock(
            "nether_brick_fence_gate",
            (BlockBehaviour.Properties properties) -> new FenceGateBlock(WoodType.OAK, properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.NETHER_BRICKS));
    public static final Holder.Reference<Block> COPPER_SULFATE_FIRE = ModRegistry.REGISTRIES.whenOnFabricLike()
            .registerBlock("copper_sulfate_fire",
                    ModFireBlock::new,
                    () -> BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE).lightLevel((BlockState blockState) -> 12));
    public static final Holder.Reference<Block> COPPER_SULFATE_TORCH = ModRegistry.REGISTRIES.registerBlock(
            "copper_sulfate_torch",
            (BlockBehaviour.Properties properties) -> new ModTorchBlock(ModRegistry.COPPER_SULFATE_FIRE_FLAME_PARTICLE_TYPE,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).lightLevel((BlockState blockState) -> 12));
    public static final Holder.Reference<Block> COPPER_SULFATE_WALL_TORCH = ModRegistry.REGISTRIES.registerBlock(
            "copper_sulfate_wall_torch",
            (BlockBehaviour.Properties properties) -> new ModWallTorchBlock(ModRegistry.COPPER_SULFATE_FIRE_FLAME_PARTICLE_TYPE,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH)
                    .lightLevel((BlockState blockState) -> 12)
                    .dropsLike(COPPER_SULFATE_TORCH.value()));
    public static final Holder.Reference<Block> COPPER_SULFATE_JACK_O_LANTERN = ModRegistry.REGISTRIES.registerBlock(
            "copper_sulfate_jack_o_lantern",
            CarvedPumpkinBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN));
    public static final Holder.Reference<Block> COPPER_SULFATE_CAMPFIRE = ModRegistry.REGISTRIES.registerBlock(
            "copper_sulfate_campfire",
            (BlockBehaviour.Properties properties) -> new ModCampfireBlock(ModRegistry.COPPER_SULFATE_LAVA_PARTICLE_TYPE,
                    1,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE).lightLevel(Blocks.litBlockEmission(12)));
    public static final Holder.Reference<Block> COPPER_SULFATE_LANTERN = ModRegistry.REGISTRIES.registerBlock(
            "copper_sulfate_lantern",
            LanternBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel((BlockState blockState) -> 12));
    public static final Holder.Reference<Block> BRAZIER = ModRegistry.REGISTRIES.registerBlock("brazier",
            (BlockBehaviour.Properties properties) -> new BrazierBlock((Holder<SimpleParticleType>) (Holder<?>) BuiltInRegistries.PARTICLE_TYPE.wrapAsHolder(
                    ParticleTypes.LAVA), 1, properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .forceSolidOn()
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)
                    .lightLevel((BlockState blockState) -> 15)
                    .noOcclusion());
    public static final Holder.Reference<Block> SOUL_BRAZIER = ModRegistry.REGISTRIES.registerBlock("soul_brazier",
            (BlockBehaviour.Properties properties) -> new BrazierBlock(ModRegistry.SOUL_LAVA_PARTICLE_TYPE,
                    2,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(BRAZIER.value()).lightLevel((BlockState blockState) -> 10));
    public static final Holder.Reference<Block> COPPER_SULFATE_BRAZIER = ModRegistry.REGISTRIES.registerBlock(
            "copper_sulfate_brazier",
            (BlockBehaviour.Properties properties) -> new BrazierBlock(ModRegistry.COPPER_SULFATE_LAVA_PARTICLE_TYPE,
                    1,
                    properties),
            () -> BlockBehaviour.Properties.ofFullCopy(BRAZIER.value()).lightLevel((BlockState blockState) -> 12));
    public static final Holder.Reference<Block> IRON_SPIKES = ModRegistry.REGISTRIES.registerBlock("iron_spikes",
            (BlockBehaviour.Properties properties) -> new SpikesBlock(ModBlocks.IRON_SPIKE_TRAP, 1, 0, properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(2.5F)
                    .noLootTable()
                    .noOcclusion()
                    .noCollission()
                    .pushReaction(PushReaction.BLOCK));
    public static final Holder.Reference<Block> GOLDEN_SPIKES = ModRegistry.REGISTRIES.registerBlock("golden_spikes",
            (BlockBehaviour.Properties properties) -> new SpikesBlock(ModBlocks.GOLDEN_SPIKE_TRAP, 2, 1, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(IRON_SPIKES.value()).mapColor(MapColor.GOLD));
    public static final Holder.Reference<Block> DIAMOND_SPIKES = ModRegistry.REGISTRIES.registerBlock("diamond_spikes",
            (BlockBehaviour.Properties properties) -> new SpikesBlock(ModBlocks.DIAMOND_SPIKE_TRAP, 3, 2, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(IRON_SPIKES.value()).mapColor(MapColor.DIAMOND));
    public static final Holder.Reference<Block> NETHERITE_SPIKES = ModRegistry.REGISTRIES.registerBlock(
            "netherite_spikes",
            (BlockBehaviour.Properties properties) -> new SpikesBlock(ModBlocks.NETHERITE_SPIKE_TRAP, 4, 2, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(IRON_SPIKES.value()).mapColor(MapColor.COLOR_BLACK));
    public static final Holder.Reference<Block> IRON_SPIKE_TRAP = ModRegistry.REGISTRIES.registerBlock("iron_spike_trap",
            (BlockBehaviour.Properties properties) -> new SpikeTrapBlock(IRON_SPIKES, 1, properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(3.5F)
                    .pushReaction(PushReaction.BLOCK));
    public static final Holder.Reference<Block> GOLDEN_SPIKE_TRAP = ModRegistry.REGISTRIES.registerBlock(
            "golden_spike_trap",
            (BlockBehaviour.Properties properties) -> new SpikeTrapBlock(GOLDEN_SPIKES, 3, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(IRON_SPIKE_TRAP.value()));
    public static final Holder.Reference<Block> DIAMOND_SPIKE_TRAP = ModRegistry.REGISTRIES.registerBlock(
            "diamond_spike_trap",
            (BlockBehaviour.Properties properties) -> new SpikeTrapBlock(DIAMOND_SPIKES, 6, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(IRON_SPIKE_TRAP.value()));
    public static final Holder.Reference<Block> NETHERITE_SPIKE_TRAP = ModRegistry.REGISTRIES.registerBlock(
            "netherite_spike_trap",
            (BlockBehaviour.Properties properties) -> new SpikeTrapBlock(NETHERITE_SPIKES, 15, properties),
            () -> BlockBehaviour.Properties.ofFullCopy(IRON_SPIKE_TRAP.value()));

    public static final Holder.Reference<BlockEntityType<CopperHopperBlockEntity>> COPPER_HOPPER_BLOCK_ENTITY_TYPE = ModRegistry.REGISTRIES.registerBlockEntityType(
            "copper_hopper",
            () -> BlockEntityType.Builder.of(CopperHopperBlockEntity::new, COPPER_HOPPER.value()));
    public static final Holder.Reference<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = ModRegistry.REGISTRIES.registerBlockEntityType(
            "pedestal",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new,
                    POLISHED_ANDESITE_PEDESTAL.value(),
                    POLISHED_GRANITE_PEDESTAL.value(),
                    POLISHED_DIORITE_PEDESTAL.value(),
                    STONE_BRICK_PEDESTAL.value(),
                    MOSSY_STONE_BRICK_PEDESTAL.value(),
                    CRACKED_STONE_BRICK_PEDESTAL.value(),
                    CUT_SANDSTONE_PEDESTAL.value(),
                    CUT_RED_SANDSTONE_PEDESTAL.value(),
                    CHISELED_SANDSTONE_PEDESTAL.value(),
                    CHISELED_RED_SANDSTONE_PEDESTAL.value(),
                    PRISMARINE_BRICK_PEDESTAL.value(),
                    BLACKSTONE_PEDESTAL.value(),
                    NETHER_BRICK_PEDESTAL.value(),
                    PURPUR_BLOCK_PEDESTAL.value()));
    public static final Holder.Reference<BlockEntityType<TimerBlockEntity>> TIMER_BLOCK_ENTITY_TYPE = ModRegistry.REGISTRIES.registerBlockEntityType(
            "timer",
            () -> BlockEntityType.Builder.of(TimerBlockEntity::new, TIMER.value()));

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
