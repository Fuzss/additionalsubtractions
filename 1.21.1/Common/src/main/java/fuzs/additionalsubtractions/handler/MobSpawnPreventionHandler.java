package fuzs.additionalsubtractions.handler;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MobSpawnPreventionHandler {
    static final Collection<MobSpawnType> NATURAL_SPAWN_REASONS = Set.of(MobSpawnType.NATURAL,
            MobSpawnType.JOCKEY,
            MobSpawnType.EVENT,
            MobSpawnType.REINFORCEMENT,
            MobSpawnType.PATROL);

    public static EventResult onEntitySpawn(Entity entity, ServerLevel serverLevel, @Nullable MobSpawnType entitySpawnReason) {
        if (entitySpawnReason != null && NATURAL_SPAWN_REASONS.contains(entitySpawnReason)) {
            int inRange = (int) serverLevel.getPoiManager()
                    .getCountInRange((Holder<PoiType> holder) -> holder.is(ModBlocks.AMETHYST_LAMP_POI_TYPE.key()),
                            entity.blockPosition(),
                            15,
                            PoiManager.Occupancy.ANY);
            if (inRange > 0) {
                // collect for running at the end of this server tick, other passengers might still be added to the level after this,
                // and calling Entity::discard to early for them will log an annoying warning
                List<Entity> entities = entity.getRootVehicle()
                        .getSelfAndPassengers()
                        .distinct()
                        .filter((Entity passenger) -> passenger != entity)
                        .toList();
                serverLevel.getServer().tell(new TickTask(serverLevel.getServer().getTickCount(), () -> {
                    entities.forEach(Entity::discard);
                }));

                return EventResult.INTERRUPT;
            }
        }

        return EventResult.PASS;
    }

    public static void onGatherPotentialSpawns(ServerLevel serverLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, MobCategory mobCategory, BlockPos blockPos, List<MobSpawnSettings.SpawnerData> mobs) {
        if (mobCategory == MobCategory.MONSTER) {
            int inRange = (int) serverLevel.getPoiManager()
                    .getCountInRange((Holder<PoiType> holder) -> holder.is(ModBlocks.AMETHYST_LAMP_POI_TYPE.key()),
                            blockPos,
                            15,
                            PoiManager.Occupancy.ANY);
            if (inRange > 0) {
                mobs.clear();
            }
        }
    }
}
