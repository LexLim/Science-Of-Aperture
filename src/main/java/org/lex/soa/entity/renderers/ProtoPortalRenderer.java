package org.lex.soa.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lex.soa.entity.ProtoPortal;

@OnlyIn(Dist.CLIENT)
public class ProtoPortalRenderer<T extends ProtoPortal> extends EntityRenderer<T> {

        private final BlockRenderDispatcher blockRenderer;

        public ProtoPortalRenderer(EntityRendererProvider.Context context) {
            super(context);
            this.blockRenderer = context.getBlockRenderDispatcher();
        }

        protected int getBlockLightLevel(T entity, BlockPos pos) {
            return Math.max(5, super.getBlockLightLevel(entity, pos));
        }

        public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            poseStack.pushPose();
            Direction direction = entity.getDirection();
            Vec3 vec3 = this.getRenderOffset(entity, partialTicks);
            poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
            double d0 = 0.46875;
            poseStack.translate((double)direction.getStepX() * 0.46875, (double)direction.getStepY() * 0.46875, (double)direction.getStepZ() * 0.46875);
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));

            poseStack.popPose();
        }

        public Vec3 getRenderOffset(T entity, float partialTicks) {
            return new Vec3((double)((float)entity.getDirection().getStepX() * 0.3F), -0.25, (double)((float)entity.getDirection().getStepZ() * 0.3F));
        }

        /**
         * Returns the location of an entity's texture.
         */
        public ResourceLocation getTextureLocation(T entity) {
            return TextureAtlas.LOCATION_BLOCKS;
        }

        protected boolean shouldShowName(T entity) {
            if (Minecraft.getInstance().player.isShiftKeyDown()) {
                return true;
            } else {
                return false;
            }
        }

        protected void renderNameTag(T entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
            super.renderNameTag(entity, Component.literal("Portal"), poseStack, bufferSource, packedLight, partialTick);
        }
    }

