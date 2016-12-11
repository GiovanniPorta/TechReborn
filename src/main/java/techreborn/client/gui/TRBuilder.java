package techreborn.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import reborncore.client.guibuilder.GuiBuilder;
import reborncore.common.powerSystem.PowerSystem;
import techreborn.client.StackToolTipEvent;
import techreborn.lib.ModInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prospector
 */
public class TRBuilder extends GuiBuilder {
	public static final ResourceLocation resourceLocation = new ResourceLocation(ModInfo.MOD_ID.toLowerCase() + ":" + "textures/gui/builder.png");

	public TRBuilder() {
		super(resourceLocation);
	}

	public void drawMultiEnergyBar(GuiScreen gui, int x, int y, int energyStored, int maxEnergyStored, int mouseX, int mouseY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);

		gui.drawTexturedModalRect(x, y, PowerSystem.getDisplayPower().xBar - 15, PowerSystem.getDisplayPower().yBar - 1, 14, 50);

		int draw = (int) ((double) energyStored / (double) maxEnergyStored * (48));
		gui.drawTexturedModalRect(x + 1, y + 49 - draw, PowerSystem.getDisplayPower().xBar, 48 + PowerSystem.getDisplayPower().yBar - draw, 12, draw);

		if (isInRect(x + 1, y + 1, 11, 48, mouseX, mouseY)) {
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			GlStateManager.colorMask(true, true, true, false);
			GuiUtils.drawGradientRect(0, x + 1, y + 1, x + 13, y + 49, 0x80FFFFFF, 0x80FFFFFF);
			GlStateManager.colorMask(true, true, true, true);
			GlStateManager.enableDepth();

			List<String> list = new ArrayList<>();
			list.add(TextFormatting.GOLD + PowerSystem.getLocaliszedPowerFormattedNoSuffix(energyStored) + "/" + PowerSystem.getLocaliszedPowerFormattedNoSuffix(maxEnergyStored) + " " + PowerSystem.getDisplayPower().abbreviation);
			if (gui.isShiftKeyDown()) {
				int percentage = StackToolTipEvent.percentage(
					maxEnergyStored,
					energyStored);
				TextFormatting color;
				if (percentage <= 10) {
					color = TextFormatting.RED;
				} else if (percentage >= 75) {
					color = TextFormatting.GREEN;
				} else {
					color = TextFormatting.YELLOW;
				}
				list.add(color + "" + percentage + "%" + TextFormatting.GRAY + " Charged");
			}
			net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(list, mouseX, mouseY, gui.width, gui.height, -1, gui.mc.fontRendererObj);
			GlStateManager.disableLighting();
		}
	}

	public void drawChargeSlot(GuiScreen gui, int posX, int posY, Arrow arrow) {
		drawSlot(gui, posX, posY);
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
		gui.drawTexturedModalRect(posX + arrow.guiX, posY + arrow.guiY, arrow.x, arrow.y, arrow.width, arrow.height);
	}

	public void drawUpgradeSlots(GuiScreen gui, int guiX, int guiY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
		drawSlot(gui, guiX + 151, guiY + 5);
		drawSlot(gui, guiX + 151, guiY + 23);
		drawSlot(gui, guiX + 151, guiY + 41);
		drawSlot(gui, guiX + 151, guiY + 59);
		gui.drawTexturedModalRect(guiX + 152, guiY + 6, 228, 18, 16, 16);
		gui.drawTexturedModalRect(guiX + 152, guiY + 24, 228, 18, 16, 16);
		gui.drawTexturedModalRect(guiX + 152, guiY + 42, 228, 18, 16, 16);
		gui.drawTexturedModalRect(guiX + 152, guiY + 60, 228, 18, 16, 16);
	}

	public void drawBurnBar(GuiScreen gui, double progress, int x, int y, int mouseX, int mouseY) {
		gui.mc.getTextureManager().bindTexture(resourceLocation);
		gui.drawTexturedModalRect(x, y, 187, 84, 13, 13);
		int j = 13 - (int) (progress);
		if (j > 0) {
			gui.drawTexturedModalRect(x, y + j, 187, 70 + j, 13, 13 - j);

		}
		if (isInRect(x, y, 12, 12, mouseX, mouseY)) {
			int percentage = StackToolTipEvent.percentage(
				13,
				(int) progress);
			TextFormatting color;
			if (percentage <= 10) {
				color = TextFormatting.RED;
			} else if (percentage >= 75) {
				color = TextFormatting.GREEN;
			} else {
				color = TextFormatting.YELLOW;
			}
			List<String> list = new ArrayList<String>();
			list.add(color + "" + percentage + "%");
			net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(list, mouseX, mouseY, gui.width, gui.height, -1, gui.mc.fontRendererObj);
		}
	}

	public int getScaledBurnTime(int scale, int burnTime, int totalBurnTime) {
		return (int) (((float) burnTime / (float) totalBurnTime) * scale);
	}

	public enum Arrow {
		UP_TOP_RIGHT(240, 0, 6, 5, 10, 2),
		UP_TOP_LEFT(240, 0, 6, 5, 2, 2),
		UP_BOTTOM_RIGHT(240, 0, 6, 5, 10, 11),
		UP_BOTTOM_LEFT(240, 0, 6, 5, 2, 11),

		DOWN_TOP_RIGHT(247, 0, 6, 5, 10, 2),
		DOWN_TOP_LEFT(247, 0, 6, 5, 2, 2),
		DOWN_BOTTOM_RIGHT(247, 0, 6, 5, 10, 11),
		DOWN_BOTTOM_LEFT(247, 0, 6, 5, 2, 11),

		RIGHT_TOP_RIGHT(240, 6, 5, 6, 11, 2),
		RIGHT_TOP_LEFT(240, 6, 5, 6, 2, 2),
		RIGHT_BOTTOM_RIGHT(240, 6, 5, 6, 11, 10),
		RIGHT_BOTTOM_LEFT(240, 6, 5, 6, 2, 10),

		LEFT_TOP_RIGHT(247, 6, 5, 6, 11, 2),
		LEFT_TOP_LEFT(247, 6, 5, 6, 2, 2),
		LEFT_BOTTOM_RIGHT(247, 6, 5, 6, 11, 10),
		LEFT_BOTTOM_LEFT(247, 6, 5, 6, 2, 10);

		public int x;
		public int y;
		public int width;
		public int height;
		public int guiX;
		public int guiY;

		Arrow(int x, int y, int width, int height, int guiX, int guiY) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.guiX = guiX;
			this.guiY = guiY;
		}
	}
}
