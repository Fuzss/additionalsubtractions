package fuzs.additionalsubtractions.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.client.handler.CrossbowScopingHandler;
import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.ItemModelPropertiesContext;
import fuzs.puzzleslib.api.client.core.v1.context.RenderTypesContext;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerTickEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class AdditionalSubtractionsClient implements ClientModConstructor {
    public static final ResourceLocation PULL_ITEM_MODEL_PROPERTY = ResourceLocation.withDefaultNamespace("pull");
    public static final ResourceLocation PULLING_ITEM_MODEL_PROPERTY = ResourceLocation.withDefaultNamespace("pulling");
    public static final ResourceLocation CHARGED_ITEM_MODEL_PROPERTY = ResourceLocation.withDefaultNamespace("charged");
    public static final ResourceLocation FIREWORK_ITEM_MODEL_PROPERTY = ResourceLocation.withDefaultNamespace("firework");
    public static final ResourceLocation ANGLE_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("angle");
    public static final ResourceLocation DISC_ITEM_MODEL_PROPERTY = AdditionalSubtractions.id("disc");

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        PlayerTickEvents.END.register(CrossbowScopingHandler::onEndPlayerTick);
    }

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.GLOW_STICK_ENTITY_TYPE.value(), ThrownItemRenderer::new);
    }

    @Override
    public void onRegisterBlockRenderTypes(RenderTypesContext<Block> context) {
        context.registerRenderType(RenderType.cutout(),
                ModBlocks.COPPER_PATINA.value(),
                ModBlocks.ROPE.value(),
                ModBlocks.GLOW_STICK.value());
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
                    return !itemStack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY).isEmpty() ?
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
        ClampedItemPropertyFunction itemPropertyFunction = (ClampedItemPropertyFunction) ItemProperties.getProperty(new ItemStack(
                item), resourceLocation);
        Objects.requireNonNull(itemPropertyFunction, "item property function " + resourceLocation + " is null");
        return itemPropertyFunction;
    }
}
