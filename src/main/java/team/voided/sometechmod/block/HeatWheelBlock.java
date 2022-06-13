package team.voided.sometechmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.voided.sometechmod.block.entity.HeatWheelEntity;

import javax.annotation.ParametersAreNonnullByDefault;

public class HeatWheelBlock extends Block implements EntityBlock {
	public HeatWheelBlock(Properties properties) {
		super(properties);
	}


	@Nullable
	@Override
	@ParametersAreNonnullByDefault
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new HeatWheelEntity(pos, state);
	}

	@Nullable
	@Override
	@ParametersAreNonnullByDefault
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return type == BlockRegistry.HEAT_WHEEL_ENTITY_TYPE ? (level, blockPos, blockState, blockEntity) -> HeatWheelEntity.tick(level, blockPos, blockState, (HeatWheelEntity) blockEntity) : null;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		if (blockEntity instanceof HeatWheelEntity heatWheel) {
			ItemStack replace = new ItemStack(BlockRegistry.HEAT_WHEEL_ITEM, 1);

			player.awardStat(Stats.BLOCK_MINED.get(this));
			player.causeFoodExhaustion(0.005F);

			BlockRegistry.HEAT_WHEEL_ITEM.setMaxCapacity(replace, heatWheel.getContainer().maxCapacity());
			BlockRegistry.HEAT_WHEEL_ITEM.setStored(replace, heatWheel.getContainer().stored());

			popResource(level, pos, replace);
		}
	}
}
