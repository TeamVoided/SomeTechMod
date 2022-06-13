package team.voided.sometechmod.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.block.entity.HeatwheelEntity;
import team.voided.sometechmod.energyunit.STMUnits;
import team.voided.sometechmod.item.HeatwheelItem;
import team.voided.sometechmod.item.groups.SomeTechModTab;

;

public final class BlockRegistry {
	public static final Block HEAT_WHEEL_BLOCK = new HeatwheelBlock(BlockBehaviour.Properties.of(Material.METAL));
	public static final HeatwheelItem HEAT_WHEEL_ITEM = new HeatwheelItem(HEAT_WHEEL_BLOCK, new QuiltItemSettings().group(SomeTechModTab.STM_BLOCKS_TAB), STMUnits.ATMOSPHERIC_EXTRACT);

	public static final BlockEntityType<HeatwheelEntity> HEAT_WHEEL_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(HeatwheelEntity::create, HEAT_WHEEL_BLOCK).build(null);

	public static void register() {
		Registry.register(Registry.BLOCK, SomeTechMod.modLoc("heatwheel"), HEAT_WHEEL_BLOCK);
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("heatwheel"), HEAT_WHEEL_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, SomeTechMod.modLoc("heatwheel"), HEAT_WHEEL_ENTITY_TYPE);
	}
}
