package techreborn.client.gui.old;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import reborncore.common.packets.PacketHandler;
import reborncore.common.powerSystem.PowerSystem;
import techreborn.client.container.old.ContainerIDSU;
import techreborn.packets.PacketIdsu;
import techreborn.tiles.idsu.TileIDSU;

import java.awt.*;
import java.io.IOException;

public class GuiIDSU extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("techreborn", "textures/gui/aesu.png");

	TileIDSU idsu;

	ContainerIDSU containerIDSU;

	public GuiIDSU(EntityPlayer player, TileIDSU tileIDSU) {
		super(new ContainerIDSU(tileIDSU, player));
		this.xSize = 176;
		this.ySize = 197;
		idsu = tileIDSU;
		this.containerIDSU = (ContainerIDSU) this.inventorySlots;
	}

	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.buttonList.add(new GuiButton(0, k + 115, l + 5, 15, 20, "++"));
		this.buttonList.add(new GuiButton(1, k + 115, l + 5 + 20, 15, 20, "+"));
		this.buttonList.add(new GuiButton(2, k + 115, l + 5 + (20 * 2), 15, 20, "-"));
		this.buttonList.add(new GuiButton(3, k + 115, l + 5 + (20 * 3), 15, 20, "--"));
		this.buttonList.add(new GuiButton(4, k + 40, l + 10, 10, 10, "+"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		this.fontRendererObj.drawString(I18n.translateToLocal("tile.techreborn.idsu.name"), 75, 10,
			Color.WHITE.getRGB());
		this.fontRendererObj.drawString(PowerSystem.getLocaliszedPower(containerIDSU.euOut) + "/tick", 10, 22,
			Color.WHITE.getRGB());
		this.fontRendererObj.drawString(PowerSystem.getLocaliszedPower(containerIDSU.storedEu), 10, 32,
			Color.WHITE.getRGB());
		this.fontRendererObj.drawString(PowerSystem.getLocaliszedPower(containerIDSU.euChange) + "  change", 10, 42,
			Color.WHITE.getRGB());
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		PacketHandler.sendPacketToServer(new PacketIdsu(button.id, idsu));

	}

}