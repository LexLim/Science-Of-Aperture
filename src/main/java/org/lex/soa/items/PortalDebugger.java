package org.lex.soa.items;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.math.Fraction;
import org.lex.soa.Soa;
import org.lex.soa.entity.ProtoPortal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class PortalDebugger extends Item {

    public PortalDebugger(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        return super.use(level, player, usedHand);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof ProtoPortal portal){
            portal.setDebugData(getDebuggerModeInt(stack));
        }
        return true;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        String mode = getDebuggerMode(stack);
        DebugToolTooltip imageTooltip = DebugToolTooltip.fromMode(mode);

        return imageTooltip == DebugToolTooltip.EMPTY ? Optional.empty() : Optional.of(imageTooltip);
    }

    public static void registerBlankDebuggerDataOn(ItemStack stacc) {
        CompoundTag tagger = new CompoundTag();
        tagger.putInt("MODE", 0);
        stacc.set(DataComponents.CUSTOM_DATA, CustomData.of(tagger));
    }

    public static CompoundTag getDebuggerDataOn(ItemStack pStack) {
        if (pStack.get(DataComponents.CUSTOM_DATA) == null) {
            registerBlankDebuggerDataOn(pStack);
        }
        return pStack.get(DataComponents.CUSTOM_DATA).copyTag();
    }

    public static void setDebuggerDataOn(ItemStack pStack, int num) {
        CompoundTag tagger = getDebuggerDataOn(pStack);
        tagger.putInt("MODE", num);
        pStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tagger));
    }

    public static void setDebuggerMode(ItemStack pStack, String mode) {
        int modeid = MODE.getBy(mode).Id;
        setDebuggerDataOn(pStack, modeid);
    }

    public static int getDebuggerModeInt(ItemStack pStack) {
        return getDebuggerDataOn(pStack).getInt("MODE");
    }

    public static String getDebuggerMode(ItemStack pStack) {
        return MODE.getBy(getDebuggerDataOn(pStack).getInt("MODE")).modename;
    }

    public record DebugToolTooltip(int num) implements TooltipComponent {
        public static final TooltipComponent EMPTY = new DebugToolTooltip(0);

        public static DebugToolTooltip fromMode(String mode) {
            return new DebugToolTooltip(MODE.getBy(mode).Id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class ClientDebugToolTooltip implements ClientTooltipComponent {
        int currentMode;
        public ClientDebugToolTooltip(DebugToolTooltip tip) {
            this.currentMode = tip.num;
        }

        public static ResourceLocation textureme(int num){
            return ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "textures/gui/debug_"+String.valueOf(num)+".png");
        }
        @Override
        public int getHeight() {
            return 40; // 64px image + 4px padding
        }

        @Override
        public int getWidth(Font font) {
            return 64;
        }

        @Override
        public void renderImage(Font font, int mouseX, int mouseY, GuiGraphics guiGraphics) {
            List<MODE> moe = MODE.getModes();
            for (int i = 0; i < moe.size(); i++) {
                int j1 = mouseX + i * 20 + 4;
                int k1 = mouseY + 5 + 1;
                rendermother(j1, k1, textureme(i), guiGraphics, (i == this.currentMode) );
            }
        }

        private void rendermother(int x, int y, ResourceLocation res, GuiGraphics guiGraphics, boolean real) {
            if (!real){
                guiGraphics.blitSprite(ResourceLocation.withDefaultNamespace("container/bundle/slot"), x, y, 0, 20, 20);
            }
            guiGraphics.blit(res, x, y, 0, 0, 20, 20, 20, 20);
        }
    }

    public enum MODE {
        none("NONE", 0),
        selfPos("SELFPOS", 1),
        selfRot("SELFROT", 2),
        linkStatus("LINK", 3),
        linkPos("LINKPOS", 4),
        linkRot("LINKROT", 5),
        playerPos("PLAYPOS", 6),
        playerRot("PLAYROT", 7);


        public int Id;
        public String modename;
        public List<Mode> All;

        MODE(String name, int Id) {
            this.modename = name;
            this.Id = Id;

            this.All = ImmutableList.copyOf(new Mode[]{new Mode(name, Id)});
        }

        public static MODE getBy(String name) {
            List<MODE> allofthem = getModes();
            for (MODE o : allofthem) {
                if (o.modename == name) {
                    return o;
                }
            }
            return none;
        }

        public static MODE getBy(int num) {
            List<MODE> allofthem = getModes();
            return allofthem.get(num);
        }

        public static List<MODE> getModes() {
            List list;
            list = ImmutableList.of(
                    none, selfPos, selfRot, linkStatus, linkPos, linkRot, playerPos, playerRot
            );
            return list;
        }

        public static int totalModes() {
            return getModes().toArray().length == 0 ? 0 : getModes().toArray().length - 1;
        }

        public record Mode(String name, int Id) {
        }
    }
}

