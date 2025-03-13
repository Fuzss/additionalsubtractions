package fuzs.additionalsubtractions.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;

public class CopperPatinaItem extends Item {

    public CopperPatinaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);

        Optional<BlockState> optional = WeatheringCopper.getNext(blockState.getBlock())
                .map((Block block) -> block.withPropertiesOf(blockState));

        if (optional.isPresent()) {

            ItemStack itemInHand = context.getItemInHand();
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.USING_ITEM.trigger(serverPlayer, itemInHand);
            }

            level.playSound(player, blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, LevelEvent.PARTICLES_SCRAPE, blockPos, 0);
            level.setBlock(blockPos, optional.get(), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, optional.get()));
            itemInHand.shrink(1);

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.useOn(context);
        }
    }
}
