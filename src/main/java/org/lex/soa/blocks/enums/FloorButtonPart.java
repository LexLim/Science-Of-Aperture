package org.lex.soa.blocks.enums;

import net.minecraft.util.StringRepresentable;

public enum FloorButtonPart implements StringRepresentable {

    NORTHWEST,
    NORTHEAST,
    SOUTHWEST,
    SOUTHEAST;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}