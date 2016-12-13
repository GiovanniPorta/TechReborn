package techreborn.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import techreborn.client.container.ContainerMachineBase;
import techreborn.tiles.TileStorageUnitBase;

/**
 * Created by Prospector
 */
public class GuiStorageUnitBase extends GuiMachineBase {

	public GuiStorageUnitBase(EntityPlayer player, TileStorageUnitBase tile, ContainerMachineBase container, String name) {
		super(player, tile, container, name);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		builder.drawMultiEnergyBar(this, 81, 16, container.energy, (int) tile.getMaxPower(), mouseX - guiLeft, mouseY - guiTop);
	}
}
