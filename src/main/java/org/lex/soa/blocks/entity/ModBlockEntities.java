package org.lex.soa.blocks.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lex.soa.Soa;
import org.lex.soa.registery.ModBlocks;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Soa.MOD_ID);


    public static final Supplier<BlockEntityType<TerminalCrafterBlockEntity>> TERMINAL_CRAFTER_ENTITY =
            BLOCK_ENTITIES.register("terminal_crafter_entity", () -> BlockEntityType.Builder.of(
                    TerminalCrafterBlockEntity::new, ModBlocks.TERMINAL_CRAFTER.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
