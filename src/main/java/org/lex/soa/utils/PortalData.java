package org.lex.soa.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Portal;

import javax.sound.sampled.Port;

public class PortalData {
    // @primary for tinting world portals ONLY
    private boolean primary;

    private BlockPos Position;
    private BlockPos Destination;
    private boolean PortalExistsSomeLevel;
    private int rotation;


    PortalData(boolean primary, BlockPos Position, BlockPos Destination){
        this.primary = primary;
        this.Position = Position;
        this.Destination = Destination;
        this.PortalExistsSomeLevel = false;
        this.rotation = 0;
    }

    public void setRotation(int rot){
        this.rotation = rot;
    }

    public int getRotation(){
        return this.rotation;
    }

    public void UpdateDestination(BlockPos newpos){
        this.Destination = newpos == null?BlockPos.ZERO:newpos;
    }

    public void UpdatePosition(BlockPos newpos){
        this.Position = newpos == null?BlockPos.ZERO:newpos;
    }

    public BlockPos getPosition(){
        return this.Position;
    }

    public BlockPos getDestination(){
        return this.Destination;
    }

    public boolean isPrimary(){
        return this.primary;
    }

    public void setPrimary(boolean prim){
        this.primary = prim;
    }

    public boolean existsInWorld(){
        return this.PortalExistsSomeLevel;
    }

    public void setExists(boolean prim){
        this.PortalExistsSomeLevel = prim;
    }

    public boolean isEmpty(){
        return (getPosition() == BlockPos.ZERO && getDestination() == BlockPos.ZERO);
    }
    public static boolean isEmpty(PortalData dat){
        return (dat.getPosition() == BlockPos.ZERO && dat.getDestination() == BlockPos.ZERO);
    }

    public CompoundTag toCompoundTag(){
        return PortalData.toCompoundTag(this);
    }

    public static CompoundTag toCompoundTag(PortalData dat){
        CompoundTag tag = new CompoundTag();
        if(dat.isEmpty()){
            PortalData blank = PortalData.Blank();

            tag.putBoolean("primary",blank.isPrimary());
            tag.putBoolean("exists", blank.existsInWorld());

            tag.putInt("rotation", blank.getRotation());
            tag.putIntArray("position",parseBlockPos(blank.getPosition()));
            tag.putIntArray("destination",parseBlockPos(blank.getDestination()));
        }
        else {
            tag.putBoolean("primary",dat.isPrimary());
            tag.putBoolean("exists",dat.existsInWorld());

            tag.putInt("rotation", dat.getRotation());
            tag.putIntArray("position",parseBlockPos(dat.getPosition()));
            tag.putIntArray("destination",parseBlockPos(dat.getDestination()));
        }

        return tag;
    }

    public static PortalData fromCompound(CompoundTag tag){
        if(tag.isEmpty())
            return new PortalData(false, BlockPos.ZERO, BlockPos.ZERO);

        boolean primary = tag.getBoolean("primary");
        boolean exists = tag.getBoolean("exists");
        int rotation = tag.getInt("rotation");

        BlockPos pos = toBlockPos(tag.getIntArray("position"));
        BlockPos dest = toBlockPos(tag.getIntArray("destination"));

        PortalData ptaldata = new PortalData(primary, pos, dest);
        ptaldata.setExists(exists);
        ptaldata.setRotation(rotation);

        return ptaldata;
    }

    public static BlockPos toBlockPos(int[] arr){
        return new BlockPos(arr[0], arr[1], arr[2]);
    }
    public static int[] parseBlockPos(BlockPos pos){
        return new int[]{pos.getX(), pos.getY(), pos.getZ()};
    }

    public static PortalData Blank(){
        return new PortalData(false, BlockPos.ZERO, BlockPos.ZERO);
    }


}

