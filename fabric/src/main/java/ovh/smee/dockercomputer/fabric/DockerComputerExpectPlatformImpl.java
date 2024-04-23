package ovh.smee.dockercomputer.fabric;

import ovh.smee.dockercomputer.DockerComputerExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class DockerComputerExpectPlatformImpl {
    /**
     * This is our actual method to {@link DockerComputerExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static Path getSaveDirectory() {
        return FabricLoader.getInstance().getGameDir().resolve("saves");
    }
}
