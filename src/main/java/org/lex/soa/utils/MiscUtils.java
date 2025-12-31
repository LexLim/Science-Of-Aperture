package org.lex.soa.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.apache.commons.lang3.Validate;

import java.util.Optional;

public class MiscUtils {
    public static Optional<BlockPos> ToOptionalBlockpos(BlockPos pos){
        return pos == BlockPos.ZERO?Optional.empty():Optional.of(pos);
    }
    public static int getDirectionToRotInt(Direction facingDirection) {
        return facingDirection.get3DDataValue();
    }

}
