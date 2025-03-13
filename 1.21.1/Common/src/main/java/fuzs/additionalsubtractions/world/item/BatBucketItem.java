package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BatBucketItem extends MobBucketItem {
    private final EntityType<?> type;

    public BatBucketItem(EntityType<?> type, Fluid content, SoundEvent emptySound, Properties properties) {
        super(type, content, emptySound, properties);
        this.type = type;
    }

    public static EventResultHolder<InteractionResult> onUseEntity(Player player, Level level, InteractionHand interactionHand, Entity entity) {
        if (entity.getType() == EntityType.BAT && entity.isAlive()) {
            ItemStack itemInHand = player.getItemInHand(interactionHand);
            if (itemInHand.is(Items.BUCKET)) {
                SoundEvent soundEvent = ((Bat) entity).getAmbientSound();
                if (soundEvent != null) {
                    entity.playSound(soundEvent, 1.0F, 1.0F);
                }
                ItemStack bucketItemStack = new ItemStack(ModItems.BAT_BUCKET);
                Bucketable.saveDefaultDataToBucketTag((Bat) entity, bucketItemStack);
                ItemStack newItemInHand = ItemUtils.createFilledResult(itemInHand, player, bucketItemStack, false);
                player.setItemInHand(interactionHand, newItemInHand);
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.FILLED_BUCKET.trigger(serverPlayer, bucketItemStack);
                }

                entity.discard();
                return EventResultHolder.interrupt(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }

        return EventResultHolder.pass();
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            this.spawn(serverLevel, containerStack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
            serverLevel.getEntitiesOfClass(Mob.class,
                    new AABB(pos).inflate(32.0),
                    (Mob mob) -> mob.isAlive() && mob instanceof Enemy).forEach((Mob mob) -> {
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
            });
        }
    }

    private void spawn(ServerLevel serverLevel, ItemStack bucketedMobStack, BlockPos pos) {
        if (this.type.spawn(serverLevel,
                bucketedMobStack,
                null,
                pos,
                MobSpawnType.BUCKET,
                true,
                false) instanceof Mob mob) {
            CustomData customData = bucketedMobStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            Bucketable.loadDefaultDataFromBucketTag(mob, customData.copyTag());
        }
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult result) {
        this.playEmptySound(player, level, pos);
        return true;
    }
}
