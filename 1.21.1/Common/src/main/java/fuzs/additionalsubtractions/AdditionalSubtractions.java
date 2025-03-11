package fuzs.additionalsubtractions;

import fuzs.additionalsubtractions.handler.MobSpawnPreventionHandler;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.network.ClientboundJukeboxSongMessage;
import fuzs.additionalsubtractions.world.item.WrenchItem;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CompostableBlocksContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.puzzleslib.api.event.v1.level.GatherPotentialSpawnsCallback;
import fuzs.puzzleslib.api.event.v1.server.RegisterPotionBrewingMixesCallback;
import fuzs.puzzleslib.api.network.v3.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalSubtractions implements ModConstructor {
    public static final String MOD_ID = "additionalsubtractions";
    public static final String MOD_NAME = "Additional Subtractions";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = NetworkHandler.builder(MOD_ID)
            .registerSerializer(ClientboundJukeboxSongMessage.class, ClientboundJukeboxSongMessage.STREAM_CODEC)
            .registerClientbound(ClientboundJukeboxSongMessage.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        RegisterPotionBrewingMixesCallback.EVENT.register((RegisterPotionBrewingMixesCallback.Builder builder) -> {
            builder.registerPotionRecipe(Potions.SWIFTNESS, Items.AMETHYST_SHARD, ModRegistry.HURRY_POTION);
            builder.registerPotionRecipe(ModRegistry.HURRY_POTION, Items.REDSTONE, ModRegistry.LONG_HURRY_POTION);
            builder.registerPotionRecipe(ModRegistry.HURRY_POTION,
                    Items.GLOWSTONE_DUST,
                    ModRegistry.STRONG_HURRY_POTION);
            builder.registerPotionRecipe(Potions.STRONG_SWIFTNESS,
                    Items.AMETHYST_SHARD,
                    ModRegistry.STRONG_HURRY_POTION);
            builder.registerPotionRecipe(Potions.LONG_SWIFTNESS, Items.AMETHYST_SHARD, ModRegistry.LONG_HURRY_POTION);
        });
        PlayerInteractEvents.USE_BLOCK.register((Player player, Level level, InteractionHand interactionHand, BlockHitResult hitResult) -> {
            ItemStack itemInHand = player.getItemInHand(interactionHand);
            if (itemInHand.getItem() instanceof AxeItem && !playerHasShieldUseIntent(player, interactionHand)) {
                BlockPos blockPos = hitResult.getBlockPos();
                BlockState blockState = level.getBlockState(blockPos);
                if (WeatheringCopper.getPrevious(blockState).isPresent()) {
                    Block.popResourceFromFace(level,
                            blockPos,
                            hitResult.getDirection(),
                            new ItemStack(ModItems.COPPER_PATINA));
                }
            }
            return EventResultHolder.pass();
        });
        GatherPotentialSpawnsCallback.EVENT.register(MobSpawnPreventionHandler::onGatherPotentialSpawns);
    }

    private static boolean playerHasShieldUseIntent(Player player, InteractionHand interactionHand) {
        return interactionHand.equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().is(Items.SHIELD) &&
                !player.isSecondaryUseActive();
    }

    @Override
    public void onCommonSetup() {
        DispenserBlock.registerBehavior(ModItems.WRENCH.value(), new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
                BlockState dispenserBlockState = blockSource.state();
                BlockPos blockPos = blockSource.pos()
                        .relative(dispenserBlockState.getValue(BlockStateProperties.FACING));
                BlockState blockState = blockSource.level().getBlockState(blockPos);
                ((WrenchItem) itemStack.getItem()).dispenserUse(blockSource.level(), blockPos, blockState, itemStack);
                return itemStack;
            }
        });
    }

    @Override
    public void onRegisterCompostableBlocks(CompostableBlocksContext context) {
        context.registerCompostable(0.3F, Items.ROTTEN_FLESH.builtInRegistryHolder());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
