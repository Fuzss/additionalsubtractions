package fuzs.additionalsubtractions.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderHandEvents;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CrossbowInHandHandler {

    public static RenderHandEvents.MainHand onRenderHand(InteractionHand interactionHand) {
        return (ItemInHandRenderer itemInHandRenderer, AbstractClientPlayer player, HumanoidArm humanoidArm, ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress) -> {
            return onRenderHand(itemInHandRenderer,
                    interactionHand,
                    player,
                    humanoidArm,
                    itemStack,
                    poseStack,
                    multiBufferSource,
                    combinedLight,
                    partialTick,
                    interpolatedPitch,
                    swingProgress,
                    equipProgress);
        };
    }

    public static EventResult onRenderHand(ItemInHandRenderer itemInHandRenderer, InteractionHand interactionHand, AbstractClientPlayer player, HumanoidArm humanoidArm, ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress) {
        ItemStack itemInHand;
        if (interactionHand == InteractionHand.MAIN_HAND) {
            itemInHand = player.getItemInHand(InteractionHand.OFF_HAND);
        } else {
            itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        }
        if (itemInHand.getItem() instanceof CrossbowItem &&
                (player.isUsingItem() && player.getUseItem() == itemInHand || CrossbowItem.isCharged(itemInHand))) {
            // prevent rendering of the other hand while charging / holding a charged crossbow
            return EventResult.INTERRUPT;
        } else if (itemStack.getItem() instanceof CrossbowItem) {
            // take over crossbow rendering, so we can properly disable the charging animation while using the item when it is already charged
            renderArmWithCrossbow(itemInHandRenderer,
                    player,
                    partialTick,
                    interactionHand,
                    swingProgress,
                    itemStack,
                    equipProgress,
                    poseStack,
                    multiBufferSource,
                    combinedLight);
            return EventResult.INTERRUPT;
        } else {
            return EventResult.PASS;
        }
    }

    /**
     * Copied from
     * {@link ItemInHandRenderer#renderArmWithItem(AbstractClientPlayer, float, float, InteractionHand, float,
     * ItemStack, float, PoseStack, MultiBufferSource, int)}.
     */
    private static void renderArmWithCrossbow(ItemInHandRenderer itemInHandRenderer, AbstractClientPlayer player, float partialTick, InteractionHand interactionHand, float swingProgress, ItemStack itemStack, float equippedProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight) {
        if (!player.isScoping()) {
            boolean bl = interactionHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidArm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
            poseStack.pushPose();
            boolean bl2 = CrossbowItem.isCharged(itemStack);
            boolean bl3 = humanoidArm == HumanoidArm.RIGHT;
            int i = bl3 ? 1 : -1;
            // check the crossbow is not charged, we need this for our implementation
            if (!bl2 && player.isUsingItem() && player.getUseItemRemainingTicks() > 0 &&
                    player.getUsedItemHand() == interactionHand) {
                itemInHandRenderer.applyItemArmTransform(poseStack, humanoidArm, equippedProgress);
                poseStack.translate((float) i * -0.4785682F, -0.094387F, 0.05731531F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-11.935F));
                poseStack.mulPose(Axis.YP.rotationDegrees((float) i * 65.3F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) i * -9.785F));
                float f = (float) itemStack.getUseDuration(player) -
                        ((float) player.getUseItemRemainingTicks() - partialTick + 1.0F);
                float g = f / (float) CrossbowItem.getChargeDuration(itemStack, player);
                if (g > 1.0F) {
                    g = 1.0F;
                }

                if (g > 0.1F) {
                    float h = Mth.sin((f - 0.1F) * 1.3F);
                    float j = g - 0.1F;
                    float k = h * j;
                    poseStack.translate(k * 0.0F, k * 0.004F, k * 0.0F);
                }

                poseStack.translate(g * 0.0F, g * 0.0F, g * 0.04F);
                poseStack.scale(1.0F, 1.0F, 1.0F + g * 0.2F);
                poseStack.mulPose(Axis.YN.rotationDegrees((float) i * 45.0F));
            } else {
                float fx = -0.4F * Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI);
                float gx = 0.2F * Mth.sin(Mth.sqrt(swingProgress) * (float) (Math.PI * 2));
                float h = -0.2F * Mth.sin(swingProgress * (float) Math.PI);
                poseStack.translate((float) i * fx, gx, h);
                itemInHandRenderer.applyItemArmTransform(poseStack, humanoidArm, equippedProgress);
                itemInHandRenderer.applyItemArmAttackTransform(poseStack, humanoidArm, swingProgress);
                if (bl2 && swingProgress < 0.001F && bl) {
                    poseStack.translate((float) i * -0.641864F, 0.0F, 0.0F);
                    poseStack.mulPose(Axis.YP.rotationDegrees((float) i * 10.0F));
                }
            }

            itemInHandRenderer.renderItem(player,
                    itemStack,
                    bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    !bl3,
                    poseStack,
                    multiBufferSource,
                    combinedLight);
            poseStack.popPose();
        }
    }
}
