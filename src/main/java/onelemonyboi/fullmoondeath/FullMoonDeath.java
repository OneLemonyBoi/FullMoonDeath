package onelemonyboi.fullmoondeath;

import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FullMoonDeath.MODID)
public class FullMoonDeath {
    public static final String MODID = "fullmoondeath";

    public static final Logger LOGGER = LogManager.getLogger();

    public static DeadWorldSavedData wsd;

    public FullMoonDeath() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(OnFullMoon::onDeath);
        MinecraftForge.EVENT_BUS.addListener(OnFullMoon::onRespawn);
        MinecraftForge.EVENT_BUS.addListener(OnFullMoon::onGamemodeChange);
        MinecraftForge.EVENT_BUS.addListener(OnFullMoon::onSleep);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }
}
