package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.init.ModLootTables;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.util.GiveItemHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class MysteriousBundleItem extends Item {
    public MysteriousBundleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        ItemStack itemInHand = player.getItemInHand(interactionHand);
        // consume first, so the slot can potentially be used for received items
        itemInHand.consume(1, player);

        if (level instanceof ServerLevel serverLevel) {
            LootTable lootTable = serverLevel.getServer()
                    .reloadableRegistries()
                    .getLootTable(ModLootTables.MYSTERIOUS_BUNDLE);
            List<ItemStack> items = lootTable.getRandomItems(new LootParams.Builder(serverLevel).withParameter(
                            LootContextParams.ORIGIN,
                            player.position())
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .create(ModRegistry.MYSTERIOUS_BUNDLE_LOOT_CONTEXT_PARAM_SET));
            for (ItemStack itemStack : items) {
                GiveItemHelper.giveItem(itemStack, (ServerPlayer) player);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide);
    }
}
