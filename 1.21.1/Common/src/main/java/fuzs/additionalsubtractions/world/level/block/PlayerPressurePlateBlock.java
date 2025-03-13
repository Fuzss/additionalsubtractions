package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class PlayerPressurePlateBlock extends PressurePlateBlock {
    public static final MapCodec<? extends PressurePlateBlock> CODEC = RecordCodecBuilder.<PlayerPressurePlateBlock>mapCodec(
            instance -> instance.group(BlockSetType.CODEC.fieldOf("block_set_type")
                            .forGetter((PlayerPressurePlateBlock block) -> block.type), propertiesCodec())
                    .apply(instance, PlayerPressurePlateBlock::new));

    public PlayerPressurePlateBlock(BlockSetType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public MapCodec<PressurePlateBlock> codec() {
        return (MapCodec<PressurePlateBlock>) CODEC;
    }

    @Override
    protected int getSignalStrength(Level level, BlockPos pos) {
        return getEntityCount(level, TOUCH_AABB.move(pos), Player.class) > 0 ? 15 : 0;
    }
}
