package fuzs.additionalsubtractions.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.client.handler.CrossbowInHandHandler;
import fuzs.additionalsubtractions.client.init.ModModelLayerLocations;
import fuzs.additionalsubtractions.client.renderer.blockentity.PedestalRenderer;
import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.world.item.PocketJukeboxItem;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.*;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderHandEvents;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class AdditionalSubtractionsClient implements ClientModConstructor {
    public static final ResourceLocation PULL_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("pull");
    public static final ResourceLocation PULLING_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("pulling");
    public static final ResourceLocation CHARGED_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("charged");
    public static final ResourceLocation FIREWORK_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("firework");
    public static final ResourceLocation ANGLE_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("angle");
    public static final ResourceLocation DISC_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("filled");

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        RenderHandEvents.MAIN_HAND.register(CrossbowInHandHandler.onRenderHand(InteractionHand.MAIN_HAND));
        RenderHandEvents.OFF_HAND.register(CrossbowInHandHandler.onRenderHand(InteractionHand.OFF_HAND)::onRenderMainHand);
    }

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.GLOW_STICK_ENTITY_TYPE.value(), ThrownItemRenderer::new);
        context.registerEntityRenderer(ModRegistry.PATINA_BLOCK_ENTITY_TYPE.value(), FallingBlockRenderer::new);
        context.registerEntityRenderer(ModRegistry.COPPER_HOPPER_MINECART_ENTITY_TYPE.value(),
                (EntityRendererProvider.Context context1) -> new MinecartRenderer<>(context1,
                        ModModelLayerLocations.COPPER_HOPPER_MINECART));
    }

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModBlocks.PEDESTAL_BLOCK_ENTITY.value(), PedestalRenderer::new);
    }

    @Override
    public void onRegisterBlockRenderTypes(RenderTypesContext<Block> context) {
        context.registerRenderType(RenderType.cutout(),
                ModBlocks.ROPE.value(),
                ModBlocks.GLOW_STICK.value(),
                ModBlocks.COPPER_RAIL.value(),
                ModBlocks.COPPER_HOPPER.value(),
                ModBlocks.REDSTONE_LANTERN.value());
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModModelLayerLocations.COPPER_HOPPER_MINECART, MinecartModel::createBodyLayer);
    }

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        context.registerItemProperty(PULL_ITEM_MODEL_PROPERTY,
                getItemPropertyFunction(Items.CROSSBOW, PULL_ITEM_MODEL_PROPERTY),
                ModItems.CROSSBOW_WITH_SPYGLASS.value());
        context.registerItemProperty(PULLING_ITEM_MODEL_PROPERTY,
                getItemPropertyFunction(Items.CROSSBOW, PULLING_ITEM_MODEL_PROPERTY),
                ModItems.CROSSBOW_WITH_SPYGLASS.value());
        context.registerItemProperty(CHARGED_ITEM_MODEL_PROPERTY,
                getItemPropertyFunction(Items.CROSSBOW, CHARGED_ITEM_MODEL_PROPERTY),
                ModItems.CROSSBOW_WITH_SPYGLASS.value());
        context.registerItemProperty(FIREWORK_ITEM_MODEL_PROPERTY,
                getItemPropertyFunction(Items.CROSSBOW, FIREWORK_ITEM_MODEL_PROPERTY),
                ModItems.CROSSBOW_WITH_SPYGLASS.value());
        context.registerItemProperty(DISC_ITEM_MODEL_PROPERTY,
                (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
                    return PocketJukeboxItem.getJukeboxPlayableItem(itemStack).has(DataComponents.JUKEBOX_PLAYABLE) ?
                            1.0F : 0.0F;
                },
                ModItems.POCKET_JUKEBOX.value());
        context.registerItemProperty(ANGLE_ITEM_MODEL_PROPERTY,
                (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
                    if (livingEntity != null && clientLevel != null) {
                        float seaLevel = clientLevel.getSeaLevel();
                        float blockY = livingEntity.getBlockY();
                        float maxBuildHeight = clientLevel.getMaxBuildHeight();
                        float minBuildHeight = clientLevel.getMinBuildHeight();
                        if (blockY > maxBuildHeight) {
                            return 0.0F;
                        } else if (blockY < minBuildHeight) {
                            return 1.0F;
                        } else if (blockY >= seaLevel) {
                            return Mth.clamp((blockY / (2.0F * (seaLevel - maxBuildHeight))) + 0.25F -
                                    ((seaLevel + maxBuildHeight) / (4.0F * (seaLevel - maxBuildHeight))), 0.0F, 1.0F);
                        } else {
                            return Mth.clamp((blockY / (2.0F * (minBuildHeight - seaLevel)) + 0.75F -
                                    (minBuildHeight + seaLevel) / (4.0F * (minBuildHeight - seaLevel))), 0.0F, 1.0F);
                        }
                    } else {
                        return 0.3125F;
                    }
                },
                ModItems.DEPTH_METER.value());
    }

    static ClampedItemPropertyFunction getItemPropertyFunction(Item item, ResourceLocation resourceLocation) {
        resourceLocation = ResourceLocationHelper.withDefaultNamespace(resourceLocation.getPath());
        ClampedItemPropertyFunction itemPropertyFunction = (ClampedItemPropertyFunction) ItemProperties.getProperty(new ItemStack(
                item), resourceLocation);
        Objects.requireNonNull(itemPropertyFunction, "item property function " + resourceLocation + " is null");
        return itemPropertyFunction;
    }
}
