package team.voided.sometechmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import team.voided.sometechmod.block.entity.CrudeConstructorEntity;

import javax.annotation.ParametersAreNonnullByDefault;

public class CrudeConstructor extends Block implements EntityBlock {
	public CrudeConstructor(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!world.isClientSide) {
			BlockEntity entity = world.getBlockEntity(pos);
			if (entity instanceof CrudeConstructorEntity constructor) {
				player.openMenu(new CrudeConstructorEntity.Provider(constructor));
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
	@ParametersAreNonnullByDefault
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CrudeConstructorEntity.create(pos, state);
	}

	@Nullable
	@Override
	@ParametersAreNonnullByDefault
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return type == BlockRegistry.CRUDE_CONSTRUCTOR_ENTITY_TYPE ? (level, blockPos, blockState, blockEntity) -> CrudeConstructorEntity.tick(level, blockPos, blockState, (CrudeConstructorEntity) blockEntity): null;
	}

	@Override
	public boolean hasDynamicShape() {
		return true;
	}
}
