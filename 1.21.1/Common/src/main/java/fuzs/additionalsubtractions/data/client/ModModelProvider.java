package fuzs.additionalsubtractions.data.client;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators blockModelGenerators) {
        this.createRedstoneLamp(ModBlocks.AMETHYST_LAMP.value(), blockModelGenerators);
    }

    public final void createRedstoneLamp(Block block, BlockModelGenerators blockModelGenerators) {
        ResourceLocation resourceLocation = TexturedModel.CUBE.create(block, blockModelGenerators.modelOutput);
        ResourceLocation litResourceLocation = blockModelGenerators.createSuffixedVariant(block,
                "_on",
                ModelTemplates.CUBE_ALL,
                TextureMapping::cube);
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.LIT,
                        litResourceLocation,
                        resourceLocation)));
    }

    @Override
    public void addItemModels(ItemModelGenerators itemModelGenerators) {
        // TODO implement this properly for 1.21.4
        this.skipItem(ModItems.CROSSBOW_WITH_SPYGLASS.value());
    }
}
