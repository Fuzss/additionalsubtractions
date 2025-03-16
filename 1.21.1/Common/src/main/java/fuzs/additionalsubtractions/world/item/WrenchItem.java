package fuzs.additionalsubtractions.world.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WrenchItem extends Item {

    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return this.useOn(context.getLevel(), context.getClickedPos(), context.getPlayer());
    }

    public InteractionResult useOn(Level level, BlockPos blockPos, @Nullable Player player) {
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.is(ModRegistry.ROTATABLE_BLOCK_TAG)) {
            BlockState newBlockState = this.rotateBlockState(level, blockPos, blockState, blockState);
            if (newBlockState == blockState) {
                return InteractionResult.PASS;
            } else if (newBlockState == null) {
                return InteractionResult.FAIL;
            } else {
                level.playSound(null,
                        blockPos,
                        SoundEvents.SPYGLASS_USE,
                        SoundSource.BLOCKS,
                        1.0F,
                        (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
                level.setBlockAndUpdate(blockPos, newBlockState);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Nullable
    protected BlockState rotateBlockState(Level level, BlockPos blockPos, BlockState blockState, BlockState originalBlockState) {
        BlockState newBlockState = this.updateBlockState(blockState);
        if (newBlockState != blockState && newBlockState != originalBlockState) {
            if (newBlockState.canSurvive(level, blockPos)) {
                return newBlockState;
            } else {
                newBlockState = this.rotateBlockState(level, blockPos, newBlockState, originalBlockState);
                return newBlockState != originalBlockState ? newBlockState : null;
            }
        } else {
            return originalBlockState;
        }
    }

    protected BlockState updateBlockState(BlockState blockState) {
        if (blockState.getOptionalValue(BlockStateProperties.OPEN).filter(Boolean::booleanValue).isPresent()) {
            return blockState;
        } else if (blockState.getOptionalValue(BlockStateProperties.EXTENDED)
                .filter(Boolean::booleanValue)
                .isPresent()) {
            return blockState;
        } else if (blockState.getOptionalValue(BlockStateProperties.ATTACH_FACE)
                .filter((AttachFace attachFace) -> attachFace == AttachFace.WALL)
                .isPresent()) {
            return blockState;
        } else if (blockState.hasProperty(BlockStateProperties.AXIS)) {
            return blockState.cycle(BlockStateProperties.AXIS);
        } else if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            return blockState.cycle(BlockStateProperties.HORIZONTAL_AXIS);
        } else if (blockState.hasProperty(BlockStateProperties.FACING)) {
            return blockState.cycle(BlockStateProperties.FACING);
        } else if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return blockState.cycle(BlockStateProperties.HORIZONTAL_FACING);
        } else if (blockState.hasProperty(BlockStateProperties.SLAB_TYPE)) {
            SlabType slabType = blockState.getValue(BlockStateProperties.SLAB_TYPE);
            if (slabType != SlabType.DOUBLE) {
                return blockState.setValue(BlockStateProperties.SLAB_TYPE,
                        slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM);
            } else {
                return blockState;
            }
        } else if (blockState.hasProperty(BlockStateProperties.ROTATION_16)) {
            return blockState.cycle(BlockStateProperties.ROTATION_16);
        } else {
            return blockState;
        }
    }

    static void printRotatableBlocks() {
        AdditionalSubtractions.LOGGER.info("{}:\n{}",
                ModRegistry.ROTATABLE_BLOCK_TAG,
                gatherRotatableBlocks().collect(Collectors.joining("\n")));
    }

    static Stream<String> gatherRotatableBlocks() {
        Collection<Property<?>> properties = ImmutableSet.of(BlockStateProperties.AXIS,
                BlockStateProperties.HORIZONTAL_AXIS,
                BlockStateProperties.FACING,
                BlockStateProperties.HORIZONTAL_FACING,
                BlockStateProperties.FACING_HOPPER,
                BlockStateProperties.SLAB_TYPE,
                BlockStateProperties.ROTATION_16);
        Set<Block> blocks = BuiltInRegistries.BLOCK.stream().filter((Block block) -> {
            return block.getStateDefinition().getProperties().stream().anyMatch(properties::contains);
        }).collect(Collectors.toCollection(Sets::newIdentityHashSet));
        Set<TagKey<Block>> tagKeys = blocks.stream()
                .flatMap((Block block) -> block.builtInRegistryHolder().tags())
                .distinct()
                .filter((TagKey<Block> tagKey) -> {
                    return StreamSupport.stream(BuiltInRegistries.BLOCK.getTagOrEmpty(tagKey).spliterator(), false)
                            .map(Holder::value)
                            .allMatch(blocks::contains);
                })
                .collect(Collectors.toSet());
        blocks.removeIf((Block block) -> {
            return tagKeys.stream().anyMatch((TagKey<Block> tagKey) -> block.builtInRegistryHolder().is(tagKey));
        });
        return Stream.concat(blocks.stream()
                        .map((Block block) -> block.builtInRegistryHolder().key().location().toString()),
                tagKeys.stream()
                        .map(TagKey::location)
                        .map((ResourceLocation resourceLocation) -> "#" + resourceLocation));
    }
}