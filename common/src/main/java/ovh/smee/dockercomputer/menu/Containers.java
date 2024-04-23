package ovh.smee.dockercomputer.menu;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public final class Containers {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create("dockercomputer", Registries.MENU);
    // --------------------------------------------------------------------- //

    public static final RegistrySupplier<MenuType<DockerComputerMenu>> DOCKERCOMPUTER_MENU = MENU_TYPES.register("dockercomputer", () -> MenuRegistry.ofExtended(DockerComputerMenu::create));

    // --------------------------------------------------------------------- //

    public static void initialize() {
        MENU_TYPES.register();
        MenuRegistry.registerScreenFactory(DOCKERCOMPUTER_MENU.get(), DockerComputerScreen::new);
    }
}