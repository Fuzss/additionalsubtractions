package fuzs.additionalsubtractions.data.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.client.data.v2.models.ModelLocationHelper;
import fuzs.puzzleslib.api.client.data.v2.models.ModelTemplateHelper;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ModModelProvider extends AbstractModelProvider {
    public static final TextureSlot HOPPER_OUTSIDE_TEXTURE_SLOT = TextureSlot.create("hopper_outside");
    public static final TextureSlot HOPPER_TOP_TEXTURE_SLOT = TextureSlot.create("hopper_top");
    public static final TextureSlot HOPPER_INSIDE_TEXTURE_SLOT = TextureSlot.create("hopper_inside");
    public static final ModelTemplate BUILTIN_GENERATED_TEMPLATE = new ModelTemplate(Optional.of(ResourceLocationHelper.withDefaultNamespace(
            "builtin/generated")), Optional.empty(), TextureSlot.LAYER0);
    public static final ModelTemplate HOPPER_TEMPLATE = ModelTemplates.create("hopper",
            TextureSlot.PARTICLE,
            TextureSlot.SIDE,
            TextureSlot.TOP,
            TextureSlot.INSIDE);
    public static final ModelTemplate HOPPER_SIDE_TEMPLATE = ModelTemplates.create("hopper_side",
            TextureSlot.PARTICLE,
            TextureSlot.SIDE,
            TextureSlot.TOP,
            TextureSlot.INSIDE);

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    protected boolean skipValidation() {
        return true;
    }

    @Override
    public void addBlockModels(BlockModelGenerators blockModelGenerators) {
        this.createRedstoneLamp(ModBlocks.AMETHYST_LAMP.value(), blockModelGenerators);
        blockModelGenerators.createTrivialCube(ModBlocks.PATINA_BLOCK.value());
        ResourceLocation resourceLocation = BUILTIN_GENERATED_TEMPLATE.create(ModBlocks.GLOW_STICK.value(),
                TextureMapping.layer0(ModBlocks.GLOW_STICK.value().asItem()),
                blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(ModBlocks.GLOW_STICK.value(),
                Variant.variant().with(VariantProperties.MODEL, resourceLocation)).with(createFacingDispatch()));
        blockModelGenerators.skipAutoItemBlock(ModBlocks.GLOW_STICK.value());
        blockModelGenerators.createSimpleFlatItemModel(ModItems.COPPER_PATINA.value());
        blockModelGenerators.createSimpleFlatItemModel(ModItems.ROPE.value());
        this.createPressurePlate(ModBlocks.OBSIDIAN_PRESSURE_PLATE.value(), Blocks.OBSIDIAN, blockModelGenerators);
        blockModelGenerators.createActiveRail(ModBlocks.COPPER_RAIL.value());
        this.createHopper(ModBlocks.COPPER_HOPPER.value(), blockModelGenerators);
    }

    public final void createPressurePlate(Block pressurePlateBlock, Block plateMaterialBlock, BlockModelGenerators blockModelGenerators) {
        TextureMapping textureMapping = TextureMapping.defaultTexture(plateMaterialBlock);
        ResourceLocation resourceLocation = ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlateBlock,
                textureMapping,
                blockModelGenerators.modelOutput);
        ResourceLocation resourceLocation2 = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlateBlock,
                textureMapping,
                blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlateBlock,
                resourceLocation,
                resourceLocation2));
    }

    public final void createCopperRail(Block railBlock, BlockModelGenerators blockModelGenerators) {
        ResourceLocation railFlatLocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "",
                ModelTemplates.RAIL_FLAT,
                TextureMapping::rail);
        ResourceLocation railCurvedLocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "_corner",
                ModelTemplates.RAIL_CURVED,
                TextureMapping::rail);
        ResourceLocation railRaisedNELocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "",
                ModelTemplates.RAIL_RAISED_NE,
                TextureMapping::rail);
        ResourceLocation railRaisedSWLocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "",
                ModelTemplates.RAIL_RAISED_SW,
                TextureMapping::rail);
        ResourceLocation poweredRailFlatLocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "_on",
                ModelTemplates.RAIL_FLAT,
                TextureMapping::rail);
        ResourceLocation poweredRailCurvedLocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "_corner_on",
                ModelTemplates.RAIL_CURVED,
                TextureMapping::rail);
        ResourceLocation poweredRailRaisedNELocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "_on",
                ModelTemplates.RAIL_RAISED_NE,
                TextureMapping::rail);
        ResourceLocation poweredRailRaisedSWLocation = blockModelGenerators.createSuffixedVariant(railBlock,
                "_on",
                ModelTemplates.RAIL_RAISED_SW,
                TextureMapping::rail);
        blockModelGenerators.createSimpleFlatItemModel(railBlock);
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(railBlock)
                .with(PropertyDispatch.properties(BlockStateProperties.POWERED, BlockStateProperties.RAIL_SHAPE)
                        .generate((Boolean isPowered, RailShape railShape) -> {
                            return switch (railShape) {
                                case NORTH_SOUTH -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailFlatLocation : railFlatLocation);
                                case RailShape.EAST_WEST -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailFlatLocation : railFlatLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                                case RailShape.ASCENDING_EAST -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailRaisedNELocation : railRaisedNELocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                                case RailShape.ASCENDING_WEST -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailRaisedSWLocation : railRaisedSWLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                                case RailShape.ASCENDING_NORTH -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailRaisedNELocation : railRaisedNELocation);
                                case RailShape.ASCENDING_SOUTH -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailRaisedSWLocation : railRaisedSWLocation);
                                case RailShape.SOUTH_EAST -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailCurvedLocation : railCurvedLocation);
                                case RailShape.SOUTH_WEST -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailCurvedLocation : railCurvedLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                                case RailShape.NORTH_WEST -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailCurvedLocation : railCurvedLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
                                case RailShape.NORTH_EAST -> Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                isPowered ? poweredRailCurvedLocation : railCurvedLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
                            };
                        })));
    }

    public static TextureMapping hopper(Block block) {
        return new TextureMapping().put(TextureSlot.PARTICLE, ModelLocationHelper.getBlockTexture(block, "_outside"))
                .put(TextureSlot.SIDE, ModelLocationHelper.getBlockTexture(block, "_outside"))
                .put(TextureSlot.TOP, ModelLocationHelper.getBlockTexture(block, "_top"))
                .put(TextureSlot.INSIDE, ModelLocationHelper.getBlockTexture(block, "_inside"));
    }

    public final void createHopper(Block block, BlockModelGenerators blockModelGenerators) {
        TextureMapping textureMapping = hopper(block);
        ResourceLocation resourceLocation = HOPPER_TEMPLATE.createWithSuffix(block,
                "",
                textureMapping,
                blockModelGenerators.modelOutput);
        ResourceLocation resourceLocation2 = HOPPER_SIDE_TEMPLATE.createWithSuffix(block,
                "_side",
                textureMapping,
                blockModelGenerators.modelOutput);
        blockModelGenerators.createSimpleFlatItemModel(block.asItem());
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.FACING_HOPPER)
                        .select(Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, resourceLocation))
                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, resourceLocation2))
                        .select(Direction.EAST,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation2)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation2)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation2)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    public static PropertyDispatch createFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.FACING)
                .select(Direction.UP,
                        Variant.variant()
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.DOWN,
                        Variant.variant()
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.SOUTH, Variant.variant())
                .select(Direction.NORTH,
                        Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST,
                        Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.WEST,
                        Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
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
        // TODO implement these properly for 1.21.4
        this.skipItem(ModItems.CROSSBOW_WITH_SPYGLASS.value());
        this.skipItem(ModItems.DEPTH_METER.value());
        this.skipItem(ModItems.POCKET_JUKEBOX.value());

        itemModelGenerators.generateFlatItem(ModItems.GLOW_STICK.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SWEET_BERRY_PIE.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.CHICKEN_NUGGET.value(), ModelTemplates.FLAT_ITEM);
        this.generateDepthMeterItem(ModItems.DEPTH_METER.value(), itemModelGenerators);
        itemModelGenerators.generateFlatItem(ModItems.FRIED_EGG.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.HEARTBEET.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GOLDEN_RING.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.HONEYED_APPLE.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MUSIC_DISC_0308.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MUSIC_DISC_1007.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MUSIC_DISC_1507.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MYSTERIOUS_BUNDLE.value(), ModelTemplates.FLAT_ITEM);
        ModelTemplateHelper.generateFlatItem(ModelLocationHelper.getItemLocation(ModItems.POCKET_JUKEBOX.value(),
                "_filled"), itemModelGenerators.output);
        itemModelGenerators.generateFlatItem(ModItems.ROSE_GOLD_ALLOY.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ROSE_GOLD_AXE.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ROSE_GOLD_HOE.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ROSE_GOLD_PICKAXE.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ROSE_GOLD_SHOVEL.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ROSE_GOLD_SWORD.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value(),
                ModelTemplates.FLAT_ITEM);
        this.generateArmorTrims(itemModelGenerators);
        itemModelGenerators.generateFlatItem(ModItems.TRIDENT_SHARD.value(), ModelTemplates.FLAT_ITEM);
        // TODO watering can
        itemModelGenerators.generateFlatItem(ModItems.COPPER_WRENCH.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.NETHERITE_HORSE_ARMOR.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.BAT_BUCKET.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.COPPER_HOPPER_MINECART.value(), ModelTemplates.FLAT_ITEM);
    }

    public final void generateDepthMeterItem(Item item, ItemModelGenerators itemModelGenerators) {
        for (int i = 0; i < 16; i++) {
            itemModelGenerators.generateFlatItem(item,
                    String.format(Locale.ROOT, "_%02d", i),
                    ModelTemplates.FLAT_ITEM);
        }
    }

    public final void generateArmorTrims(ItemModelGenerators itemModelGenerators) {
        for (Map.Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
            if (entry.getKey().location().getNamespace().equals(AdditionalSubtractions.MOD_ID) &&
                    entry.getValue() instanceof ArmorItem armorItem) {
                itemModelGenerators.generateArmorTrims(armorItem);
            }
        }
    }
}
