package org.lex.soa.registery;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lex.soa.Soa;

import java.util.function.Supplier;

public class SoaSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Soa.MOD_ID);

    public static final Supplier<SoundEvent> GUN_DEPLETE_PRIMARY = registerSoundEvent("gun_deplete_primary");
    public static final Supplier<SoundEvent> GUN_DEPLETE_SECONDARY = registerSoundEvent("gun_deplete_secondary");
    public static final Supplier<SoundEvent> PORTAL_SUMMON = registerExistingSoundEvent("portal_summon", SoundEvents.RESPAWN_ANCHOR_SET_SPAWN.getLocation());
    public static final Supplier<SoundEvent> PORTAL_WITHER = registerExistingSoundEvent("portal_wither", SoundEvents.RESPAWN_ANCHOR_DEPLETE.value().getLocation());


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, name);

        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
    private static Supplier<SoundEvent> registerExistingSoundEvent(String name, ResourceLocation sound) {

        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(sound));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}