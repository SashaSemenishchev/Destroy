package com.petrolpark.destroy.block.entity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour.TankSegment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class AgingBarrelBlockEntity extends SmartTileEntity {

    public SmartInventory inventory;
    protected SmartFluidTankBehaviour inputTank, outputTank;

    protected LazyOptional<IFluidHandler> fluidCapability;
    public LazyOptional<IItemHandlerModifiable> itemCapability;

    private int timer;

    public AgingBarrelBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        inventory = new SmartInventory(2, this, 1, false).forbidExtraction();
        itemCapability = LazyOptional.of(() -> inventory);

        timer = 0;
    };

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, 1000, true);
        outputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 1, 1000, true)
            .forbidInsertion();
        behaviours.add(inputTank);
        behaviours.add(outputTank);
        fluidCapability = LazyOptional.of(() -> {
			LazyOptional<? extends IFluidHandler> inputCap = inputTank.getCapability();
			LazyOptional<? extends IFluidHandler> outputCap = outputTank.getCapability();
			return new CombinedTankWrapper(outputCap.orElse(null), inputCap.orElse(null));
		});
    };

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        timer = compound.getInt("Timer");
        //Storage of what's in the Tanks is automatically covered in SmartTileEntity
    };

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putInt("Timer", timer);
        //Retrieval of what's in the Tanks is automatically covered in SmartTileEntity
    };

    //TODO sort this nonsense out
    // @Override
	// public void setRemoved() {
	// 	itemCapability.invalidate();
	// 	fluidCapability.invalidate();
	// 	super.setRemoved();
	// };

    @Nonnull
    @Override
    @SuppressWarnings("null")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return fluidCapability.cast();
        } else if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemCapability.cast();
        };
        return super.getCapability(cap, side);
    };

    @Override
    public void tick() {
        super.tick();
    };

    /**
     * Get the Fluid to render in the world when the Barrel is open.
     */
    public TankSegment getTankToRender() {
        if (!outputTank.isEmpty()) {
            return outputTank.getPrimaryTank();
        } else {
            return inputTank.getPrimaryTank();
        }
    };

    


    
}