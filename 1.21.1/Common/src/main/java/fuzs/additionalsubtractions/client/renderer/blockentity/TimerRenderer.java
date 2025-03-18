package fuzs.additionalsubtractions.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.additionalsubtractions.client.init.ModModelLayerLocations;
import fuzs.additionalsubtractions.world.level.block.TimerBlock;
import fuzs.additionalsubtractions.world.level.block.entity.TimerBlockEntity;
import fuzs.puzzleslib.api.client.data.v2.models.ModelLocationHelper;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Blocks;

public class TimerRenderer implements BlockEntityRenderer<TimerBlockEntity> {
    public static final Material TIMER_POINTER_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS,
            ModelLocationHelper.getBlockTexture(Blocks.STONE));

    private final ModelPart modelPart;

    public TimerRenderer(BlockEntityRendererProvider.Context context) {
        this.modelPart = context.bakeLayer(ModModelLayerLocations.TIMER).getChild("timer_pointer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition partDefinition1 = partDefinition.addOrReplaceChild("timer_pointer",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 2.0F, 6.0F),
                PartPose.offset(8.0F, 0.0F, 8.0F));
        // 8^(1/2)
        final float width = 2.8284271F;
        partDefinition1.addOrReplaceChild("timer_pointer_front",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-width / 2.0F, 7.0F, -width / 2.0F, width, 2.0F, width, new CubeDeformation(-0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.0F, (float) (Math.PI / 4), 0.0F));
        partDefinition1.addOrReplaceChild("timer_pointer_back",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-width / 2.0F, 7.0F, -width / 2.0F, width, 2.0F, width, new CubeDeformation(-0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, (float) (Math.PI / 4), 0.0F));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void render(TimerBlockEntity timerBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        Direction direction = timerBlockEntity.getBlockState().getValue(TimerBlock.FACING);
        float rotationAngle = timerBlockEntity.getRotationAngle(partialTick) - direction.toYRot();
        rotationAngle *= 0.017453292F;
        this.modelPart.yRot = rotationAngle;
        VertexConsumer vertexConsumer = TIMER_POINTER_TEXTURE.buffer(multiBufferSource, RenderType::entitySolid);
        this.modelPart.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}
