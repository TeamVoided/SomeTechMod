package team.voided.sometechmod.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.item.EnergizedBlockItem;
import team.voided.sometechmod.block.entity.HeatwheelEntity;

public class HeatwheelItem extends EnergizedBlockItem {
	public HeatwheelItem(Block block, Properties properties, EnergyUnit unit) {
		super(block, properties, unit);
	}

	@Override
	public InteractionResult place(BlockPlaceContext context) {
		InteractionResult r = super.place(context);

		if (r == InteractionResult.FAIL) return r;

		BlockEntity entity = context.getLevel().getBlockEntity(context.getClickedPos());

		if (entity instanceof HeatwheelEntity heatWheel) {
			heatWheel.getContainer().addEnergy(getStored(context.getItemInHand()));
		} else return InteractionResult.FAIL;

		return InteractionResult.SUCCESS;
	}
}
