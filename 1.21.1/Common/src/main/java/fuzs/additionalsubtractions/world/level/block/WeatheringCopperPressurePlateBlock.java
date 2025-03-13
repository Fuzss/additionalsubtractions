package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

/**
 * Copied from {@link net.minecraft.world.level.block.WeatheringCopperTrapDoorBlock}.
 */
public class WeatheringCopperPressurePlateBlock extends PlayerPressurePlateBlock implements WeatheringCopper {
    public static final MapCodec<? extends PressurePlateBlock> CODEC = RecordCodecBuilder.<WeatheringCopperPressurePlateBlock>mapCodec(
            instance -> instance.group(BlockSetType.CODEC.fieldOf("block_set_type")
                            .forGetter((WeatheringCopperPressurePlateBlock block) -> block.type),
                    WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state")
                            .forGetter(WeatheringCopperPressurePlateBlock::getAge),
                    propertiesCodec()).apply(instance, WeatheringCopperPressurePlateBlock::new));

    private final WeatheringCopper.WeatherState weatherState;

    @Override
    public MapCodec<PressurePlateBlock> codec() {
        return (MapCodec<PressurePlateBlock>) CODEC;
    }

    public WeatheringCopperPressurePlateBlock(BlockSetType type, WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(type, properties);
        this.weatherState = weatherState;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.changeOverTime(state, level, pos, random);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }
}
