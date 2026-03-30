package org.lex.soa.blocks.enums;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;

public enum CubeDispenserPart implements StringRepresentable {

    BOTTOM_BACK_LEFT  (0, -1, 0),
    BOTTOM_BACK_RIGHT (1, -1, 0),

    BOTTOM_FRONT_LEFT (0, -1, 1),
    BOTTOM_FRONT_RIGHT(1, -1, 1),

    TOP_BACK_LEFT     (0, 0, 0),
    TOP_BACK_RIGHT    (1, 0, 0),

    TOP_FRONT_LEFT    (0, 0, 1),
    TOP_FRONT_RIGHT   (1, 0, 1);

    private final int x;
    private final int y;
    private final int z;

    CubeDispenserPart(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public BlockPos getOffset() {

        return new BlockPos(x, y, z);

    }

    @Override
    public String getSerializedName() {

        return name().toLowerCase();

    }

}