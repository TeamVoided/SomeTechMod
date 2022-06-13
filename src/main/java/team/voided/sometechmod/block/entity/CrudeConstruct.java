package team.voided.sometechmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.voided.quiltenergy.block.entity.EnergizedBlockEntity;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.sometechmod.api.data.IDataContaining;

public class CrudeConstruct extends EnergizedBlockEntity implements IDataContaining {
	public CrudeConstruct(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, container, energyTransferAllowed);
	}

	public CrudeConstruct(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, double maxCapacity, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, unit, maxCapacity, energyTransferAllowed);
	}

	@Override
	public double getDataCapacity() {
		return 0;
	}

	@Override
	public double getUsedData() {
		return 0;
	}
}
