package team.voided.sometechmod.item;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.item.groups.SomeTechModTab;

public class ItemRegistry {
	public static final Item COGWHEEL = new Item(new QuiltItemSettings().group(SomeTechModTab.SMT_ITEM_TAB));
	public static final Item WIRE = new Item(new QuiltItemSettings().group(SomeTechModTab.SMT_ITEM_TAB));

	public static void register() {
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("cogwheel"), COGWHEEL);
		Registry.register(Registry.ITEM, SomeTechMod.modLoc("wire"), WIRE);
	}
}
