package fuzs.additionalsubtractions.client.resources.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class PocketMusicSoundInstance extends AbstractTickableSoundInstance {
    private final Player playerEntity;
    private final ItemStack itemStack;
    public static PocketMusicSoundInstance instance;

    public PocketMusicSoundInstance(SoundEvent soundEvent, Player player, ItemStack itemStack, boolean looping, float volume) {
        super(soundEvent, SoundSource.RECORDS, RandomSource.create());
        this.playerEntity = player;
        this.itemStack = itemStack;
        this.looping = looping;
        this.volume = volume;
        this.x = this.playerEntity.getX();
        this.y = this.playerEntity.getY();
        this.z = this.playerEntity.getZ();
    }

    @Override
    public void tick() {
        ItemStack cursor = this.playerEntity.containerMenu.getCarried();
        boolean hasDisc =
                ItemStack.matches(cursor, this.itemStack) || this.playerEntity.getInventory().contains(this.itemStack);

        if (this.playerEntity.isDeadOrDying() || !hasDisc) {
            this.cancel();
        } else {
            this.x = this.playerEntity.getX();
            this.y = this.playerEntity.getY();
            this.z = this.playerEntity.getZ();
        }
    }

    public void cancel() {
        this.stop();
    }

    public void play() {
        Minecraft.getInstance().getSoundManager().play(this);
    }
}
