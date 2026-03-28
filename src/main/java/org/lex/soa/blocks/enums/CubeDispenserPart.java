package org.lex.soa.blocks.enums;

import net.minecraft.util.StringRepresentable;

public enum CubeDispenserPart
        implements StringRepresentable {

    BOTTOM_BACK_LEFT,
    BOTTOM_BACK_RIGHT,
    BOTTOM_FRONT_LEFT,
    BOTTOM_FRONT_RIGHT,

    TOP_BACK_LEFT,
    TOP_BACK_RIGHT,
    TOP_FRONT_LEFT,
    TOP_FRONT_RIGHT;

    private final String name;

    CubeDispenserPart() {
        this.name = name().toLowerCase();
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static CubeDispenserPart byIndex(int index) {
        return values()[index];
    }

}