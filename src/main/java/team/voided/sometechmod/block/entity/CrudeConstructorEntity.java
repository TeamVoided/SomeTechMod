package team.voided.sometechmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.block.entity.EnergizedBlockEntity;
import team.voided.quiltenergy.energy.EnergyContainer;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.EnergyUnits;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.sometechmod.SomeTechMod;
import team.voided.sometechmod.block.BlockRegistry;
import team.voided.sometechmod.menu.ConstructingMenu;
import team.voided.sometechmod.recipe.ConstructingRecipe;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class CrudeConstructorEntity extends EnergizedBlockEntity {
	private final Container inv = new SimpleContainer(3);

	public CrudeConstructorEntity(BlockPos pos, BlockState state, Direction... energyTransferAllowed) {
		this(BlockRegistry.CRUDE_CONSTRUCTOR_ENTITY_TYPE, pos, state, new EnergyContainer(EnergyUnits.QUILT_ENERGY, 500_000), energyTransferAllowed);
	}

	public CrudeConstructorEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, container, energyTransferAllowed);
	}

	public CrudeConstructorEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, double maxCapacity, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState, unit, maxCapacity, energyTransferAllowed);
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, CrudeConstructorEntity self) {
		RecipeManager manager = level.getRecipeManager();
		Optional<ConstructingRecipe> recipe = manager.getRecipeFor(ConstructingRecipe.Type.INSTANCE, self.getInv(), level);

		recipe.ifPresent(crudeConstructingRecipe -> {
			if (crudeConstructingRecipe.getTier() >= 0) crudeConstructingRecipe.assemble(self.getInv());
		});
	}

	public static CrudeConstructorEntity create(BlockPos pos, BlockState state) {
		return new CrudeConstructorEntity(pos, state);
	}

	public Container getInv() {
		return inv;
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.putString("slot0_item", Registry.ITEM.getKey(inv.getItem(0).getItem()).toString());
		nbt.putInt("slot0_size", inv.getItem(0).getCount());
		nbt.putString("slot1_item", Registry.ITEM.getKey(inv.getItem(1).getItem()).toString());
		nbt.putInt("slot1_size", inv.getItem(1).getCount());
		nbt.putString("slot2_item", Registry.ITEM.getKey(inv.getItem(2).getItem()).toString());
		nbt.putInt("slot2_size", inv.getItem(2).getCount());
	}

	@Override
	public void load(CompoundTag nbt) {
		ItemStack slot0 = new ItemStack(Registry.ITEM.get(SomeTechMod.fromString(nbt.getString("slot0_item"))), nbt.getInt("slot0_size"));
		ItemStack slot1 = new ItemStack(Registry.ITEM.get(SomeTechMod.fromString(nbt.getString("slot1_item"))), nbt.getInt("slot1_size"));
		ItemStack slot2 = new ItemStack(Registry.ITEM.get(SomeTechMod.fromString(nbt.getString("slot2_item"))), nbt.getInt("slot2_size"));

		inv.setItem(0, slot0);
		inv.setItem(1, slot1);
		inv.setItem(2, slot2);
	}

	public static class Provider implements MenuProvider {
		private final CrudeConstructorEntity entity;

		public Provider(CrudeConstructorEntity entity) {
			this.entity = entity;
		}

		@Override
		public Component getDisplayName() {
			return Component.translatable("container.crude_constructor.title");
		}

		@Nullable
		@Override
		@ParametersAreNonnullByDefault
		public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
			return new ConstructingMenu(i, entity.getInv(), inventory, ContainerLevelAccess.create(player.level, entity.getBlockPos()));
		}
	}
}
