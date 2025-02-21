package dqu.additionaladditions.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class CopperPatinaItem extends BlockItem {

    public CopperPatinaItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);

        Optional<BlockState> optional = WeatheringCopper.getNext(blockState.getBlock())
                .map((Block block) -> block.withPropertiesOf(blockState));
        if (optional.isPresent() && player != null && !player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.USING_ITEM.trigger(serverPlayer, context.getItemInHand());
            }

            level.playSound(player, blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, blockPos, 0);
            level.setBlock(blockPos, optional.get(), 11);
            context.getItemInHand().shrink(1);

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
