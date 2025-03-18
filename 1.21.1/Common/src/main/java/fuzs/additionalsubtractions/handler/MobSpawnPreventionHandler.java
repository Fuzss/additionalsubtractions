package fuzs.additionalsubtractions.handler;

import fuzs.additionalsubtractions.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.List;

public class MobSpawnPreventionHandler {

    public static void onGatherPotentialSpawns(ServerLevel serverLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, MobCategory mobCategory, BlockPos blockPos, List<MobSpawnSettings.SpawnerData> mobs) {
        if (mobCategory == MobCategory.MONSTER) {
            int inRange = (int) serverLevel.getPoiManager()
                    .getCountInRange((Holder<PoiType> holder) -> holder.is(ModBlocks.AMETHYST_LAMP_POI_TYPE.key()),
                            blockPos,
                            21,
                            PoiManager.Occupancy.ANY);
            if (inRange > 0) {
                mobs.clear();
            }
        }
    }
}
