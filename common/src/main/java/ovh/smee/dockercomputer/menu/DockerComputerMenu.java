package ovh.smee.dockercomputer.menu;

import com.google.common.io.Closer;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DockerComputerMenu extends AbstractContainerMenu {

    public DockerComputerMenu(@Nullable MenuType<?> menuType, int id) {
        super(menuType, id);



    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    public static DockerComputerMenu create(final int id, final Inventory playerInventory, final @Nullable FriendlyByteBuf data) {
        return new DockerComputerMenu(Containers.DOCKERCOMPUTER_MENU.get(), id);
    }
}
