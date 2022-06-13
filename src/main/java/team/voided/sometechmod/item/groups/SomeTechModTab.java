package team.voided.sometechmod.item.groups;

import net.minecraft.world.item.ItemStack;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.item.ItemRegistry;

public final class SomeTechModTab {

	public static final QuiltItemGroup SMT_ITEM_TAB = QuiltItemGroup.builder(SomeTechMod.modLoc("smt_items")).icon(() -> new ItemStack(ItemRegistry.COGWHEEL)).build();


}
