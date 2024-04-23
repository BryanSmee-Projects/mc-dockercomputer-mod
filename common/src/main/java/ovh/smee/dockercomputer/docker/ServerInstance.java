package ovh.smee.dockercomputer.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerInstance {
    private static DockerClient client;
    private MinecraftServer server;
    private String id;

    private UniqueContainer mainContainer;

    private final ArrayList<UniqueContainer> runningContainers = new ArrayList<UniqueContainer>();

    public static DockerClient getClient() {
        if (client != null) return client;

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        ZerodepDockerHttpClient httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        client = DockerClientImpl.getInstance(config, httpClient);
        return client;
    }

    public void refreshContainers() {
        // Refresh running containers
        runningContainers.clear();
        getClient().listContainersCmd().exec().forEach(container -> {
            if(container.getNames().length > 0) {
                String name = container.getNames()[0];
                String image = container.getImage();
                if(name.startsWith(this.id + "-")) {
                    runningContainers.add(new UniqueContainer(name, image));
                }
            }
        });
    }

    public ServerInstance(MinecraftServer server) {
        this.server = server;
        File directory = server.getServerDirectory();
        // Create dockercomputer.id file
        File idFile = new File(directory, "dockercomputer.id");
        if (!idFile.exists()) {
            this.id = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            try {
                idFile.createNewFile();
                FileWriter write = new FileWriter(idFile);
                write.write(id);
                write.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Scanner scanner = null;
            try {
                scanner = new Scanner(idFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.id = scanner.nextLine();
            scanner.close();
        }

        this.mainContainer = new UniqueContainer(this.id + "-main", "nginx:latest");
        server.sendSystemMessage(Component.literal("Main container " + this.mainContainer.getName() + " is " + this.mainContainer.getState()));
        this.mainContainer.create();
        this.mainContainer.start();
        this.refreshContainers();
    }
}
