package techreborn.client.container.old;

import net.minecraft.entity.player.EntityPlayer;
import reborncore.client.gui.slots.BaseSlot;
import reborncore.client.gui.slots.SlotCharge;
import reborncore.client.gui.slots.SlotOutput;
import techreborn.api.gui.SlotUpgrade;
import techreborn.tiles.TileAlloySmelter;

public class ContainerAlloySmelter extends ContainerCrafting {

	public int tickTime;
	EntityPlayer player;
	TileAlloySmelter tile;

	public ContainerAlloySmelter(EntityPlayer player, TileAlloySmelter tile) {
		super(tile.crafter);
		this.player = player;
		this.tile = tile;
		addPlayerSlots();
		addInventorySlots();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}


	public void addInventorySlots() {

		// input
		this.addSlotToContainer(new ContainerAlloyFurnace.SlotInputCustom(tile.inventory, 0, 47, 17, 0));
		this.addSlotToContainer(new ContainerAlloyFurnace.SlotInputCustom(tile.inventory, 1, 65, 17, 1));
		// outputs
		this.addSlotToContainer(new SlotOutput(tile.inventory, 2, 116, 35));
		// battery
		this.addSlotToContainer(new SlotCharge(tile.inventory, 3, 56, 53));
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
