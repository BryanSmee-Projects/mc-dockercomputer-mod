package ovh.smee.dockercomputer;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import ovh.smee.dockercomputer.block.DockerComputerBlock;
import ovh.smee.dockercomputer.docker.ServerInstance;
import ovh.smee.dockercomputer.menu.Containers;
import ovh.smee.dockercomputer.menu.DockerComputerMenu;

import java.awt.*;
import java.util.function.Supplier;

public class DockerComputerMod {
    public static final String MOD_ID = "dockercomputer";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    // Registering a new creative tab
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> DOCKERCOMPUTER_TAB = TABS.register("docker_computer", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + MOD_ID + ".docker_computer"),
                    () -> new ItemStack(DockerComputerMod.EXAMPLE_ITEM.get())));
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () ->
            new Item(new Item.Properties().arch$tab(DockerComputerMod.DOCKERCOMPUTER_TAB)));

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static final RegistrySupplier<Block> DOCKER_COMPUTER = BLOCKS.register("docker_computer", DockerComputerBlock::new);

    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> DOCKER_COMPUTER_ITEM = BLOCK_ITEMS.register("docker_computer", () ->
            new BlockItem(DockerComputerMod.DOCKER_COMPUTER.get(), new Item.Properties().arch$tab(DockerComputerMod.DOCKERCOMPUTER_TAB)));


    public static ServerInstance SERVER_INSTANCE = null;

    public static void init() {
        TABS.register();
        ITEMS.register();
        BLOCKS.register();
        BLOCK_ITEMS.register();
        LifecycleEvent.SERVER_STARTING.register(server -> {
            SERVER_INSTANCE = new ServerInstance(server);
        });
        LifecycleEvent.SERVER_STARTED.register(server -> {
            System.out.println("Docker client: " + SERVER_INSTANCE.getClient().infoCmd().exec().toString());
        });
        ClientLifecycleEvent.CLIENT_SETUP.register(client -> {
            Containers.initialize();
        });
        System.out.println(DockerComputerExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
