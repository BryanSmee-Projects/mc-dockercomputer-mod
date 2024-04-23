package ovh.smee.dockercomputer.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DockerComputerScreen extends AbstractContainerScreen<DockerComputerMenu> {
    public DockerComputerScreen(DockerComputerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        guiGraphics.fill(0, 0, this.width, this.height, 0xFF000000);

    }
}
