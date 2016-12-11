package techreborn.client.container.old;

import net.minecraft.entity.player.EntityPlayer;
import reborncore.client.gui.slots.BaseSlot;
import reborncore.client.gui.slots.SlotOutput;
import techreborn.api.gui.SlotUpgrade;
import techreborn.tiles.TileAssemblingMachine;

public class ContainerAssemblingMachine extends ContainerCrafting{

	public int tickTime;
	EntityPlayer player;
	TileAssemblingMachine tile;

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public ContainerAssemblingMachine(EntityPlayer player, TileAssemblingMachine tile) {
		super(tile.crafter);
		this.player = player;
		this.tile = tile;
		addPlayerSlots();
		addInventorySlots();
	}


	public void addInventorySlots() {
		// input
		this.addSlotToContainer(new BaseSlot(tile.inventory, 0, 47, 17));
		this.addSlotToContainer(new BaseSlot(tile.inventory, 1, 65, 17));
		// outputs
		this.addSlotToContainer(new SlotOutput(tile.inventory, 2, 116, 35));
		// power
		this.addSlotToContainer(new BaseSlot(tile.inventory, 3, 56, 53));
		// upgrades
		this.addSlotToContainer(new SlotUpgrade(tile.inventory, 4, 152, 8));
		this.addSlotToContainer(new SlotUpgrade(tile.inventory, 5, 152, 26));
		this.addSlotToContainer(new SlotUpgrade(tile.inventory, 6, 152, 44));
		this.addSlotToContainer(new SlotUpgrade(tile.inventory, 7, 152, 62));
	}


	public void addPlayerSlots() {
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

}
