package org.lex.soa.registery;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lex.soa.Soa;
import org.lex.soa.entity.ProtoPortal;
import org.lex.soa.entity.renderers.ProtoPortalRenderer;

import java.util.function.Supplier;

public class SoaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Soa.MOD_ID);

    public static final Supplier<EntityType<ProtoPortal>> PROTO_PORTAL =
            ENTITY_TYPES.register("proto_portal", () -> EntityType.Builder.<ProtoPortal>of(ProtoPortal::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("proto_portal"));




    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static void registerEntityRenderers(){
        EntityRenderers.register(PROTO_PORTAL.get(), ProtoPortalRenderer::new);
    }

}