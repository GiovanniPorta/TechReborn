package techreborn.tiles.generator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.ForgeModContainer;
import reborncore.api.power.EnumPowerTier;
import reborncore.api.tile.IInventoryProvider;
import reborncore.common.IWrenchable;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.TilePowerAcceptor;
import reborncore.common.util.Inventory;
import techreborn.init.ModBlocks;

public class TileGenerator extends TilePowerAcceptor implements IWrenchable, IInventoryProvider {
	public static int outputAmount = 10;
	public Inventory inventory = new Inventory(2, "TileGenerator", 64, this);
	public int fuelSlot = 0;
	public int burnTime;
	public int totalBurnTime = 0;
	// sould properly use the conversion
	// ratio here.
	public boolean isBurning;
	public boolean lastTickBurning;
	ItemStack burnItem;

	public TileGenerator() {
		super(1);
	}

	public static int getItemBurnTime(ItemStack stack) {
		return TileEntityFurnace.getItemBurnTime(stack) / 4;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (world.isRemote) {
			return;
		}
		if (getEnergy() < getMaxPower()) {
			if (burnTime > 0) {
				burnTime--;
				addEnergy(outputAmount);
				isBurning = true;
			}
		} else {
			isBurning = false;
		}

		if (burnTime == 0) {
			updateState();
			burnTime = totalBurnTime = getItemBurnTime(getStackInSlot(fuelSlot));
			if (burnTime > 0) {
				updateState();
				burnItem = getStackInSlot(fuelSlot);
				if (getStackInSlot(fuelSlot).getCount() == 1) {
					if (getStackInSlot(fuelSlot).getItem() == Items.LAVA_BUCKET || getStackInSlot(fuelSlot).getItem() == ForgeModContainer.getInstance().universalBucket) {
						setInventorySlotContents(fuelSlot, new ItemStack(Items.BUCKET));
					} else {
						setInventorySlotContents(fuelSlot, ItemStack.EMPTY);
					}

				} else {
					decrStackSize(fuelSlot, 1);
				}
			}
		}

		lastTickBurning = isBurning;
	}

	public void updateState() {
		IBlockState BlockStateContainer = world.getBlockState(pos);
		if (BlockStateContainer.getBlock() instanceof BlockMachineBase) {
			BlockMachineBase blockMachineBase = (BlockMachineBase) BlockStateContainer.getBlock();
			if (BlockStateContainer.getValue(BlockMachineBase.ACTIVE) != burnTime > 0)
				blockMachineBase.setActive(burnTime > 0, world, pos);
		}
	}

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, EnumFacing side) {
		return false;
	}

	@Override
	public EnumFacing getFacing() {
		return getFacingEnum();
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return entityPlayer.isSneaking();
	}

	@Override
	public float getWrenchDropRate() {
		return 1.0F;
	}

	@Override
	public double getMaxPower() {
		return 10000;
	}

	@Override
	public boolean canAcceptEnergy(EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canProvideEnergy(EnumFacing direction) {
		return true;
	}

	@Override
	public double getMaxOutput() {
		return 64;
	}

	@Override
	public double getMaxInput() {
		return 0;
	}

	@Override
	public EnumPowerTier getTier() {
		return EnumPowerTier.LOW;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer p0) {
		return new ItemStack(ModBlocks.generator);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}
}
