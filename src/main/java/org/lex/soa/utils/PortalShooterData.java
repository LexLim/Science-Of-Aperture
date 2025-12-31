package org.lex.soa.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.lex.soa.Soa;

import java.util.Objects;

public class PortalShooterData {
    // colours are cosmetic ONLY and dont serve a functional purpose
    private int primaryColour;
    private int secondaryColour;

    private PortalData primaryPortal;
    private PortalData secondaryPortal;

    PortalShooterData(int primcolour, int seconcolour){
        this.primaryColour = primcolour;
        this.secondaryColour = seconcolour;

        this.primaryPortal = PortalData.Blank();
        primaryPortal.setPrimary(true);
        this.secondaryPortal= PortalData.Blank();
    }

    public void setPrimaryColour(int colour){
        this.primaryColour = colour;
    }

    public void setSecondaryColour(int colour){
        this.secondaryColour = colour;
    }

    public int getPrimaryColour(){
        return this.primaryColour;
    }
    public int getSecondaryColour(){
        return this.secondaryColour;
    }

    public PortalData getPrimaryPortal(){
        return this.primaryPortal;
    }
    public PortalData getSecondaryPortal(){
        return this.secondaryPortal;
    }

    public void setPrimaryPortal(PortalData por){
        this.primaryPortal = por;
    }
    public void setSecondaryPortal(PortalData por){
        this.secondaryPortal = por;
    }

    public boolean isEmpty(){
        return (PortalData.isEmpty(this.primaryPortal) && PortalData.isEmpty(this.secondaryPortal));
    }

    public static boolean isEmpty(PortalShooterData dat){
        return PortalData.isEmpty(dat.primaryPortal) && PortalData.isEmpty(dat.secondaryPortal);
    }

    public static CompoundTag toCompoundTag(PortalShooterData dat){
        CompoundTag tag = new CompoundTag();

        if(dat.isEmpty()){
            PortalShooterData blank = PortalShooterData.Blank();

            tag.putInt("PrimaryColour", blank.getPrimaryColour());
            tag.putInt("SecondaryColour", blank.getSecondaryColour());
            tag.put("PrimaryPortal", blank.primaryPortal.toCompoundTag());
            tag.put("SecondaryPortal", blank.secondaryPortal.toCompoundTag());
        }
        else {

            tag.putInt("PrimaryColour", dat.getPrimaryColour());
            tag.putInt("SecondaryColour", dat.getSecondaryColour());
            tag.put("PrimaryPortal", dat.primaryPortal.toCompoundTag());
            tag.put("SecondaryPortal", dat.secondaryPortal.toCompoundTag());
        }

        return tag;
    }

    public static PortalShooterData fromCompound(CompoundTag tag){
        if(tag.isEmpty())
            return new PortalShooterData(
                    0,
                    0
            );

        PortalShooterData shooterdat = new PortalShooterData(
                tag.getInt("PrimaryColour"),
                tag.getInt("SecondaryColour")
        );

        shooterdat.setPrimaryPortal(
                PortalData.fromCompound(tag.getCompound("PrimaryPortal"))
        );
        shooterdat.setSecondaryPortal(
                PortalData.fromCompound(tag.getCompound("SecondaryPortal"))
        );

        return shooterdat;
    }

    public static PortalShooterData Blank(){
        //blank by default
        return new PortalShooterData(0,0);
    }

    public static ItemStack applyDatToItem(PortalShooterData dat, ItemStack stacc){
        CompoundTag tagger = toCompoundTag(dat).copy();
        stacc.set(DataComponents.CUSTOM_DATA, CustomData.of(tagger));
        return stacc;
    }

    public static PortalShooterData fromItem(ItemStack stacc){
        return PortalShooterData.fromCompound(
                PortalShooterData.getItemCompound(stacc)
        );
    }

    public ItemStack applyToItem(ItemStack stacc){
        Soa.LOGGER.info("ITEM NBT: attempting to store data");
        CompoundTag tagger = toCompoundTag(this).copy();
        Soa.LOGGER.info("ITEM NBT: data being updated: " + tagger.toString());
        stacc.set(DataComponents.CUSTOM_DATA, CustomData.of(tagger));
        Soa.LOGGER.info("ITEM NBT: new data is: " + stacc.get(DataComponents.CUSTOM_DATA).toString());

        return stacc;
    }

    public static CompoundTag getItemCompound(ItemStack pStack){
        if(pStack.get(DataComponents.CUSTOM_DATA) == null){
           PortalShooterData.Blank().applyToItem(pStack);
        }
        return pStack.get(DataComponents.CUSTOM_DATA).copyTag();
    }

}

