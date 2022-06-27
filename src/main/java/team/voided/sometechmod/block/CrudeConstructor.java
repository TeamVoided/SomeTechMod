package team.voided.sometechmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import team.voided.sometechmod.block.entity.CrudeConstructorEntity;

public class CrudeConstructor extends BaseEntityBlock {
	public CrudeConstructor(Properties properties) {
		super(properties);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
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

	@Override
	public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		ItemStack s = stack;

		if (blockEntity instanceof CrudeConstructorEntity entity) {
			popResource(world, pos, entity.getInv().getItem(0));
			popResource(world, pos, entity.getInv().getItem(1));
			popResource(world, pos, entity.getInv().getItem(2));

			if (stack.getItem() == BlockRegistry.CRUDE_CONSTRUCTOR_ITEM) {
				BlockRegistry.CRUDE_CONSTRUCTOR_ITEM.setEnergy(s, entity.getContainer().stored());
			}
		}

		super.playerDestroy(world, player, pos, state, blockEntity, s);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CrudeConstructorEntity.create(pos, state);
	}
}
