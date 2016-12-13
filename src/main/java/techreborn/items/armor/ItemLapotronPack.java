package techreborn.items.armor;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.api.power.IEnergyItemInfo;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.powerSystem.PoweredItem;
import techreborn.client.TechRebornCreativeTab;
import techreborn.config.ConfigTechReborn;
import techreborn.init.ModItems;

public class ItemLapotronPack extends ItemArmor implements IEnergyItemInfo {

	public static final int maxCharge = ConfigTechReborn.LapotronPackCharge;
	public static final int tier = ConfigTechReborn.LapotronPackTier;
	public double transferLimit = 100000;

	public ItemLapotronPack() {
		super(ItemArmor.ArmorMaterial.DIAMOND, 7, EntityEquipmentSlot.CHEST);
		setCreativeTab(TechRebornCreativeTab.instance);
		setUnlocalizedName("techreborn.lapotronpack");
		setMaxStackSize(1);
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return PowerSystem.getDisplayPower().colour;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "techreborn:" + "textures/models/lapotronpack.png";
	}

	@Override
	public double getMaxPower(ItemStack stack) {
		return maxCharge;
	}

	@Override
	public boolean canAcceptEnergy(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public double getMaxTransfer(ItemStack stack) {
		return transferLimit;
	}

	@Override
	public int getStackTier(ItemStack stack) {
		return tier;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item,
	                        CreativeTabs par2CreativeTabs, NonNullList itemList) {
		ItemStack uncharged = new ItemStack(ModItems.lapotronpack);
		ItemStack charged = new ItemStack(ModItems.lapotronpack);
		PoweredItem.setEnergy(getMaxPower(charged), charged);

		itemList.add(uncharged);
		itemList.add(charged);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		double charge = (PoweredItem.getEnergy(stack) / getMaxPower(stack));
		return 1 - charge;

	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			if (player.inventory.getStackInSlot(i) != ItemStack.EMPTY) {
				ItemStack item = player.inventory.getStackInSlot(i);
				if (item.getItem() instanceof IEnergyItemInfo) {
					IEnergyItemInfo energyItemInfo = (IEnergyItemInfo) item.getItem();
					if (energyItemInfo.getMaxPower(item) != PoweredItem.getEnergy(item)) {
						if (PoweredItem.canUseEnergy(energyItemInfo.getMaxPower(item), itemStack)) {
							PoweredItem.useEnergy(energyItemInfo.getMaxTransfer(item), itemStack);
							PoweredItem.setEnergy(PoweredItem.getEnergy(item) + energyItemInfo.getMaxTransfer(item), item);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

}
