package org.lex.soa.blocks.enums;

import net.minecraft.util.StringRepresentable;

public enum SlidingDoorPart implements StringRepresentable {

    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    TOP_LEFT,
    TOP_RIGHT;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}