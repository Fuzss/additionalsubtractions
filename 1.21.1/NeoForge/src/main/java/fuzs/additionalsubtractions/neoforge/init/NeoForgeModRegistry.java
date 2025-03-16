package fuzs.additionalsubtractions.neoforge.init;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class NeoForgeModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(AdditionalSubtractions.MOD_ID);
    public static final Holder.Reference<Block> COPPER_RAIL_BLOCK = REGISTRIES.registerBlock("copper_rail",
            (BlockBehaviour.Properties properties) -> new PoweredRailBlock(properties, true),
            () -> BlockBehaviour.Properties.of().noCollission().strength(0.7F).sound(SoundType.METAL));

    public static void bootstrap() {
        // NO-OP
    }
}
