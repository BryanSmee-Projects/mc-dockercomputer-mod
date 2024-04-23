package ovh.smee.dockercomputer.forge;

import dev.architectury.platform.forge.EventBuses;
import ovh.smee.dockercomputer.DockerComputerMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DockerComputerMod.MOD_ID)
public class DockerComputerModForge {
    public DockerComputerModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(DockerComputerMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        DockerComputerMod.init();
    }
}
