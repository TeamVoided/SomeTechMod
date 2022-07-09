package team.voided.sometechmod.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import team.voided.sometechmod.SomeTechMod;

public class ConstructingScreen extends AbstractContainerScreen<ConstructingMenu> {
	public static final ResourceLocation TEXTURE = SomeTechMod.modLoc("textures/gui/container/crude_constructor.png");

	public ConstructingScreen(ConstructingMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
	}

	@Override
	protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		blit(matrices, x, y, 0, 0, imageWidth, imageHeight);
	}

	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
		renderBg(matrices, delta, mouseX, mouseY);
		super.render(matrices, mouseX, mouseY, delta);
		renderTooltip(matrices, mouseX, mouseY);
	}
}
