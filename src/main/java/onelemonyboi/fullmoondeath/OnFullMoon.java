package onelemonyboi.fullmoondeath;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import static onelemonyboi.fullmoondeath.DeadWorldSavedData.getInstance;

public class OnFullMoon {
    public static void onDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof ServerPlayerEntity) || event.getEntityLiving().world.isRemote()) {
            return;
        }
        ServerPlayerEntity playerEntity = (ServerPlayerEntity) event.getEntityLiving();
        if (playerEntity.world.getDimensionKey().getLocation() != World.OVERWORLD.getLocation()) {
            return;
        }
        if (playerEntity.world.getMoonPhase() == 0 && playerEntity.world.isNightTime()) {
            FullMoonDeath.LOGGER.info("Player added");
            getInstance(playerEntity.getServer()).isDead.add(playerEntity.getUniqueID());
            getInstance(playerEntity.getServer()).markDirty();
        }
    }

    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getPlayer().world.isRemote()) {
            return;
        }

        if (getInstance(event.getPlayer().getServer()).isDead.contains(event.getPlayer().getUniqueID())) {
            event.getPlayer().setGameType(GameType.SPECTATOR);
        }
    }

    public static void onGamemodeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        if (event.getPlayer().world.isRemote()) {
            return;
        }

        if (getInstance(event.getPlayer().getServer()).isDead.contains(event.getPlayer().getUniqueID()) && event.getNewGameMode() != GameType.SPECTATOR) {
            event.setCanceled(true);
            event.getPlayer().sendStatusMessage(new TranslationTextComponent("fullmoondeath.text.cancelgamemodechange"), true);
        }
    }

    public static void onWorldStart(FMLServerStartedEvent event) {
        getInstance(event.getServer());
    }

    public static void onSleep(PlayerSleepInBedEvent event) {
        if (event.getPlayer().world.getMoonPhase() == 0 && event.getPlayer().world.isNightTime()) {
            event.setResult(PlayerEntity.SleepResult.OTHER_PROBLEM);
            event.getPlayer().sendStatusMessage(new TranslationTextComponent("fullmoondeath.text.cancelsleep"), true);
        }
    }
}
