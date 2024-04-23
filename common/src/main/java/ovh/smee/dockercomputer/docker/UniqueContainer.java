package ovh.smee.dockercomputer.docker;

import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;
import ovh.smee.dockercomputer.DockerComputerExpectPlatform;

import java.util.Objects;

public class UniqueContainer {
    private final String name;
    private final String image;

    public String getState() {
        String status;
        try {
            status = ServerInstance.getClient().inspectContainerCmd(name).exec().getState().getStatus().toLowerCase();
        } catch (NotFoundException e) {
            status = "not-found";
        }
        return status;
    }

    public UniqueContainer(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void pull() {
        try {
            ServerInstance.getClient().pullImageCmd(image).exec(new PullImageResultCallback()).awaitCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void create() {
        if (!Objects.equals(this.getState(), "running")) {
            this.pull();
            ServerInstance.getClient().createContainerCmd(image).withName(name).exec();
        }
    }

    public void start() {
        try {
            ServerInstance.getClient().startContainerCmd(name).exec();
        } catch (NotModifiedException e) {
            return;
        }
    }
}
