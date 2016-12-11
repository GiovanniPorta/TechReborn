package techreborn.client.container.old;

import net.minecraft.entity.player.EntityPlayer;
import reborncore.client.gui.slots.BaseSlot;
import reborncore.common.container.RebornContainer;
import techreborn.tiles.TileChunkLoader;

public class ContainerChunkloader extends RebornContainer {

	EntityPlayer player;

	public ContainerChunkloader(TileChunkLoader tilechunkloader, EntityPlayer player) {
		this.player = player;

		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new BaseSlot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new BaseSlot(player.inventory, i, 8 + i * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
