package org.lex.soa.mixins.entity;

import net.minecraft.world.entity.player.Player;
import org.lex.soa.utils.PlayerPortalOrchestrator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerPortalOrchestrator {

}
