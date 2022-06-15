package team.voided.sometechmod.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import team.voided.sometechmod.SomeTechMod;

public class CrudeConstructingMenuScreen extends AbstractContainerScreen<CrudeConstructingMenu> implements MenuAccess<CrudeConstructingMenu> {
	private final CrudeConstructingMenu menu;
	public static final ResourceLocation TEXTURE = SomeTechMod.modLoc("textures/gui/container/crude_construct_menu");

	public CrudeConstructingMenuScreen(CrudeConstructingMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		this.menu = abstractContainerMenu;
	}

	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBg(matrices, delta, mouseX, mouseY);
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = this.leftPos;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrices, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	public CrudeConstructingMenu getMenu() {
		return menu;
	}
}
