package team.voided.sometechmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import team.voided.sometechmod.menu.CrudeConstructingMenuConstructor;

public class CrudeConstructor extends Block {
	private static final Component CONTAINER_TITLE = Component.translatable("container.constructing.crude");

	public CrudeConstructor(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			player.openMenu(state.getMenuProvider(world, pos));
			return InteractionResult.CONSUME;
		}
	}

	@Nullable
	@Override
	public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
		return new SimpleMenuProvider(new CrudeConstructingMenuConstructor(world, pos), CONTAINER_TITLE);
	}
}
