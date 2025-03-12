package fuzs.additionalsubtractions.world.entity.projectile;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GlowStick extends ThrowableItemProjectile {

    public GlowStick(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public GlowStick(Level level, LivingEntity owner) {
        super(ModRegistry.GLOW_STICK_ENTITY_TYPE.value(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GLOW_STICK.value();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        BlockPlaceContext blockPlaceContext = new BlockPlaceContext(this.level(),
                null,
                InteractionHand.MAIN_HAND,
                ItemStack.EMPTY,
                hitResult);
        if (blockPlaceContext.canPlace()) {
            BlockState blockState = ModBlocks.GLOW_STICK.value().getStateForPlacement(blockPlaceContext);
            if (blockState != null) {
                BlockPos blockPos = hitResult.getBlockPos().relative(hitResult.getDirection());
                this.level().setBlockAndUpdate(blockPos, blockState);
                SoundType soundType = blockState.getSoundType();
                this.playSound(soundType.getPlaceSound(),
                        (soundType.getVolume() + 1.0F) / 2.0F,
                        soundType.getPitch() * 0.8F);
                return;
            }
        }

        this.dropItem();
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.BLOCKS;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        this.dropItem();
    }

    protected void dropItem() {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.level()
                    .addFreshEntity(new ItemEntity(this.level(),
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            this.getItem()));
        }
    }
}
