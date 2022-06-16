package team.voided.sometechmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import team.voided.sometechmod.block.entity.CrudeConstructorEntity;

public class CrudeConstructor extends BaseEntityBlock {
	private static final Component CONTAINER_TITLE = Component.translatable("container.constructing.crude");

	public CrudeConstructor(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!world.isClientSide) {
			BlockEntity entity = world.getBlockEntity(pos);
			if (entity instanceof CrudeConstructorEntity constructor) {
				player.openMenu(constructor);
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CrudeConstructorEntity.create(pos, state);
	}
}
