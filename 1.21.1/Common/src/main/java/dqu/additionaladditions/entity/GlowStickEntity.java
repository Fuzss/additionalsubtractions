package dqu.additionaladditions.entity;

import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.registry.ModBlocks;
import dqu.additionaladditions.registry.ModItems;
import dqu.additionaladditions.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class GlowStickEntity extends ThrowableItemProjectile {

    public GlowStickEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public GlowStickEntity(Level level, LivingEntity owner) {
        super(ModRegistry.GLOW_STICK_ENTITY_TYPE, owner, level);
    }

    public GlowStickEntity(Level level, double x, double y, double z) {
        super(ModRegistry.GLOW_STICK_ENTITY_TYPE, x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GLOW_STICK;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel serverLevel) {
            this.remove(RemovalReason.DISCARDED);
            BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
            if (serverLevel.getBlockState(pos).isAir()) {
                serverLevel.setBlockAndUpdate(pos,
                        ModBlocks.GLOW_STICK.defaultBlockState()
                                .setValue(GlowStickBlock.FLIPPED, level().getRandom().nextBoolean()));
                serverLevel.playSound(null,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        SoundEvents.GLASS_PLACE,
                        SoundSource.BLOCKS,
                        1.0f,
                        1.0f);
            } else {
                ItemStack itemStack = new ItemStack(ModItems.GLOW_STICK);
                ItemEntity itemEntity = new ItemEntity(serverLevel, this.getX(), this.getY(), this.getZ(), itemStack);
                serverLevel.addFreshEntity(itemEntity);
            }
        }
    }
}
