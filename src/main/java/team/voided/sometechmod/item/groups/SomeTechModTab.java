package team.voided.sometechmod.item.groups;

import net.minecraft.world.item.ItemStack;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.item.ItemRegistry;

public final class SomeTechModTab {

	public static final QuiltItemGroup STM_ITEM_TAB = QuiltItemGroup.builder(SomeTechMod.modLoc("items")).icon(() -> new ItemStack(ItemRegistry.COGWHEEL)).build();

	public static final QuiltItemGroup STM_BLOCKS_TAB = QuiltItemGroup.builder(SomeTechMod.modLoc("blocks")).icon(() -> new ItemStack(ItemRegistry.WIRE)).build();


}
