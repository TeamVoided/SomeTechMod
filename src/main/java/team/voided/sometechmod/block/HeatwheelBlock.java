package team.voided.sometechmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import team.voided.sometechmod.block.entity.HeatwheelEntity;

import javax.annotation.ParametersAreNonnullByDefault;

public class HeatwheelBlock extends HorizontalDirectionalBlock implements EntityBlock {
	public HeatwheelBlock(Properties properties) {
		super(properties);
		registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}


	@Nullable
	@Override
	@ParametersAreNonnullByDefault
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return HeatwheelEntity.create(pos, state);
	}

	@Override
	@ParametersAreNonnullByDefault
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Nullable
	@Override
	@ParametersAreNonnullByDefault
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return type == BlockRegistry.HEAT_WHEEL_ENTITY_TYPE ? (level, blockPos, blockState, blockEntity) -> HeatwheelEntity.tick(level, blockPos, blockState, (HeatwheelEntity) blockEntity) : null;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		if (blockEntity instanceof HeatwheelEntity heatWheel) {
			ItemStack replace = new ItemStack(BlockRegistry.HEAT_WHEEL_ITEM, 1);

			player.awardStat(Stats.BLOCK_MINED.get(this));
			player.causeFoodExhaustion(0.005F);

			BlockRegistry.HEAT_WHEEL_ITEM.setMaxCapacity(replace, heatWheel.getContainer().maxCapacity());
			BlockRegistry.HEAT_WHEEL_ITEM.setStored(replace, heatWheel.getContainer().stored());

			popResource(level, pos, replace);
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	@Override
	@ParametersAreNonnullByDefault
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);

		switch (direction) {
			case NORTH -> {
				return Shapes.join(Block.box(7.5, 0, 0, 8.5, 16, 7), Block.box(0, 0, 7, 16, 16, 16), BooleanOp.OR);
			} case SOUTH -> {
				return Shapes.join(Block.box(7.5, 0, 9, 8.5, 16, 16), Block.box(0, 0, 0, 16, 16, 9), BooleanOp.OR);
			} case EAST -> {
				return Shapes.join(Block.box(9, 0, 7.5, 16, 16, 8.5), Block.box(0, 0, 0, 9, 16, 16), BooleanOp.OR);
			} case WEST -> {
				return Shapes.join(Block.box(0, 0, 7.5, 7, 16, 8.5), Block.box(7, 0, 0, 16, 16, 16), BooleanOp.OR);
			}
		}

		return Shapes.join(Block.box(7.5, 0, 0, 8.5, 16, 7), Block.box(0, 0, 7, 16, 16, 16), BooleanOp.OR);
	}

	@Override
	public boolean hasDynamicShape() {
		return true;
	}
}
