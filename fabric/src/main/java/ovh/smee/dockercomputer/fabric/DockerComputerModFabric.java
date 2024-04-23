package ovh.smee.dockercomputer.fabric;

import ovh.smee.dockercomputer.DockerComputerMod;
import net.fabricmc.api.ModInitializer;

public class DockerComputerModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DockerComputerMod.init();
    }
}
