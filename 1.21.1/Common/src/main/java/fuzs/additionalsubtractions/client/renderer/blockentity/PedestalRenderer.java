package fuzs.additionalsubtractions.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.additionalsubtractions.world.level.block.entity.PedestalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {

    public final ItemRenderer itemRenderer;

    public PedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    /**
     * Copied from {@link ItemEntityRenderer#render(ItemEntity, float, float, PoseStack, MultiBufferSource, int)}.
     */
    @Override
    public void render(PedestalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        ItemStack itemStack = blockEntity.getItem(0);
        blockEntity.random.setSeed(ItemEntityRenderer.getSeedForItemStack(itemStack));
        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, blockEntity.getLevel(), null, 0);
        boolean bl = bakedModel.isGui3d();
        float g = Mth.sin((blockEntity.getAge() + partialTick) / 10.0F + blockEntity.bobOffs) * 0.1F + 0.1F;
        float h = bakedModel.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
        poseStack.translate(0.5F, 1.0F, 0.5F);
        poseStack.translate(0.0F, g + 0.25F * h, 0.0F);
        float i = blockEntity.getSpin(partialTick);
        poseStack.mulPose(Axis.YP.rotation(i));
        ItemEntityRenderer.renderMultipleFromCount(this.itemRenderer,
                poseStack,
                bufferSource,
                packedLight,
                itemStack,
                bakedModel,
                bl,
                blockEntity.random);
        poseStack.popPose();
    }
}
