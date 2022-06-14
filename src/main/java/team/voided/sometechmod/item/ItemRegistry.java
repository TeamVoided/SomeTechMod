package team.voided.sometechmod.item;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.item.groups.STMTabs;

public class ItemRegistry {
	public static final Item COGWHEEL = new Item(new QuiltItemSettings().group(STMTabs.STM_ITEM_TAB));
	public static final Item WIRE = new Item(new QuiltItemSettings().group(STMTabs.STM_ITEM_TAB));
	public static final Item NETHER_FIBER = new Item(new QuiltItemSettings().group(STMTabs.STM_ITEM_TAB));

	public static void register() {
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("cogwheel"), COGWHEEL);
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("wire"), WIRE);
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("nether_fiber"), NETHER_FIBER);
	}
}
