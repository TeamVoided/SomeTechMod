package team.voided.sometechmod.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.block.entity.HeatwheelEntity;
import team.voided.sometechmod.energyunit.STMUnits;
import team.voided.sometechmod.item.HeatwheelItem;
import team.voided.sometechmod.item.groups.STMTabs;

;

public final class BlockRegistry {
	public static final Block HEATWHEEL_BLOCK = new HeatwheelBlock(BlockBehaviour.Properties.of(Material.METAL));
	public static final HeatwheelItem HEATWHEEL_ITEM = new HeatwheelItem(HEATWHEEL_BLOCK, new QuiltItemSettings().group(STMTabs.STM_BLOCKS_TAB), STMUnits.ATMOSPHERIC_EXTRACT);

	public static final CrudeConstructor CRUDE_CONSTRUCTOR = new CrudeConstructor(BlockBehaviour.Properties.of(Material.METAL));
	public static final BlockItem CRUDE_CONSTRUCTOR_ITEM = new BlockItem(CRUDE_CONSTRUCTOR, new Item.Properties().tab(STMTabs.STM_BLOCKS_TAB));

	public static final BlockEntityType<HeatwheelEntity> HEATWHEEL_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(HeatwheelEntity::create, HEATWHEEL_BLOCK).build(null);

	public static void register() {
		Registry.register(Registry.BLOCK, SomeTechMod.modLoc("heatwheel"), HEATWHEEL_BLOCK);
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("heatwheel"), HEATWHEEL_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, SomeTechMod.modLoc("heatwheel"), HEATWHEEL_ENTITY_TYPE);
		Registry.register(Registry.BLOCK, SomeTechMod.modLoc("crude_constructor"), CRUDE_CONSTRUCTOR);
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("crude_constructor"), CRUDE_CONSTRUCTOR_ITEM);
	}
}
