package ovh.smee.dockercomputer.block;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ovh.smee.dockercomputer.menu.DockerComputerMenu;

import static dev.architectury.registry.menu.MenuRegistry.openExtendedMenu;
import static dev.architectury.registry.menu.MenuRegistry.openMenu;

public class DockerComputerBlock extends Block {


    public DockerComputerBlock() {
        super(Properties.copy(Blocks.OAK_PLANKS));
        BlockEvent.PLACE.register(this::onPlaceEvent);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(this::onRightClickBlock);
    }

    private EventResult onRightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        // get the block state at the position
        BlockState blockState = player.level().getBlockState(blockPos);
        if (blockState.getBlock() instanceof DockerComputerBlock && player instanceof ServerPlayer) {
            System.out.println("DockerComputerBlock right clicked at " + blockPos.toString());
            openExtendedMenu((ServerPlayer) player, new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(FriendlyByteBuf buf) {

                }

                @Override
                public Component getDisplayName() {
                    return Component.literal("Docker Computer");
                }

                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return DockerComputerMenu.create(i, inventory, null);
                }
            });
        }

        return EventResult.pass();
    }

    private EventResult onPlaceEvent(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (blockState.getBlock() instanceof DockerComputerBlock) {
            System.out.println("DockerComputerBlock placed at " + blockPos.toString());
        }

        return EventResult.pass();

    }


}
