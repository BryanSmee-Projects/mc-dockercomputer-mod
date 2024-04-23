package ovh.smee.dockercomputer.forge;

import ovh.smee.dockercomputer.DockerComputerExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class DockerComputerExpectPlatformImpl {
    /**
     * This is our actual method to {@link DockerComputerExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Path getSaveDirectory() {
        return FMLPaths.GAMEDIR.get();
    }
}
