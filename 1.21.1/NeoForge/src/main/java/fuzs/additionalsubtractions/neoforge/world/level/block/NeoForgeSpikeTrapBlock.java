package fuzs.additionalsubtractions.neoforge.world.level.block;

import fuzs.additionalsubtractions.world.level.block.SpikeTrapBlock;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class NeoForgeSpikeTrapBlock extends SpikeTrapBlock {

    public NeoForgeSpikeTrapBlock(Holder<Block> spikesBlock, float pushingStrength, Properties properties) {
        super(spikesBlock, pushingStrength, properties);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return state.getValue(EXTENDED) == 0 ? PushReaction.NORMAL : super.getPistonPushReaction(state);
    }
}
