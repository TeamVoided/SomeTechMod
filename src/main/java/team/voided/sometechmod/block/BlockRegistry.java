package team.voided.sometechmod.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.block.entity.HeatWheelEntity;
import team.voided.sometechmod.energyunit.STMUnits;
import team.voided.sometechmod.item.HeatWheelItem;

;

public final class BlockRegistry {
	public static final Block HEAT_WHEEL_BLOCK = new HeatWheelBlock(BlockBehaviour.Properties.of(Material.METAL));
	public static final HeatWheelItem HEAT_WHEEL_ITEM = new HeatWheelItem(HEAT_WHEEL_BLOCK, new Item.Properties(), STMUnits.ATMOSPHERIC_EXTRACT);

	public static final BlockEntityType<HeatWheelEntity> HEAT_WHEEL_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(HeatWheelEntity::create, HEAT_WHEEL_BLOCK).build(null);

	public static void register() {
		Registry.register(Registry.BLOCK, SomeTechMod.modLoc("heat_wheel"), HEAT_WHEEL_BLOCK);
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("heat_wheel"), HEAT_WHEEL_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, SomeTechMod.modLoc("heat_wheel"), HEAT_WHEEL_ENTITY_TYPE);
	}
}
