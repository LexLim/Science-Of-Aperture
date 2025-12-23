package org.lex.soa.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.lex.soa.Soa;
import org.lex.soa.items.ProtoGun;
import org.lex.soa.registery.SoaEntities;
import org.lex.soa.registery.SoaSounds;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class ProtoPortal extends HangingEntity {

        private static final EntityDataAccessor<Optional<BlockPos>> DATA_DESTINATION = SynchedEntityData.defineId(
                ProtoPortal.class,
                EntityDataSerializers.OPTIONAL_BLOCK_POS
        );
        private static final EntityDataAccessor<Integer> DATA_ROTATION = SynchedEntityData.defineId(
                ProtoPortal.class,
                EntityDataSerializers.INT
        );

        public static final int NUM_ROTATIONS = 8;
        private static final float DEPTH = 0.0625F;
        private static final float WIDTH = 0.75F;
        private static final float HEIGHT = 0.75F;
        private float dropChance = 1.0F;
        private boolean fixed;

    public ProtoPortal(EntityType<ProtoPortal> entityType, Level level) {
        super(entityType, level);
    }

    public ProtoPortal(Level level, BlockPos pos, Direction facingDirection) {
        this(SoaEntities.PROTO_PORTAL.get(), level, pos, facingDirection);
    }

    public ProtoPortal(EntityType<? extends HangingEntity> entityType, Level level, BlockPos pos, Direction direction) {
            super(entityType, level, pos);
            this.setDirection(direction);
        }

    @Override
        protected void defineSynchedData(SynchedEntityData.Builder builder) {
            builder.define(DATA_DESTINATION, Optional.empty());
            builder.define(DATA_ROTATION, 0);
        }

        /**
         * Updates facing and bounding box based on it
         */
        @Override
        protected void setDirection(Direction facingDirection) {
            Validate.notNull(facingDirection);
            this.direction = facingDirection;
            if (facingDirection.getAxis().isHorizontal()) {
                this.setXRot(0.0F);
                this.setYRot((float)(this.direction.get2DDataValue() * 90));
            } else {
                this.setXRot((float)(-90 * facingDirection.getAxisDirection().getStep()));
                this.setYRot(0.0F);
            }

            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
            this.recalculateBoundingBox();
        }

        @Override
        protected AABB calculateBoundingBox(BlockPos pos, Direction direction) {
            float f = 0.46875F;
            Vec3 vec3 = Vec3.atCenterOf(pos).relative(direction, -0.46875);
            Direction.Axis direction$axis = direction.getAxis();
            double d0 = direction$axis == Direction.Axis.X ? 0.0625 : 0.75;
            double d1 = direction$axis == Direction.Axis.Y ? 0.0625 : 2;
            double d2 = direction$axis == Direction.Axis.Z ? 0.0625 : 0.75;
            return AABB.ofSize(vec3, d0, d1, d2);
        }

        @Override
        public boolean survives() {
            BlockState blockstate = this.level().getBlockState(this.pos.relative(this.direction.getOpposite()));
                return blockstate.isSolid() || this.direction.getAxis().isHorizontal() && DiodeBlock.isDiode(blockstate)
                        ? this.level().getEntities(this, this.getBoundingBox(),((b) -> b instanceof ProtoPortal) ).isEmpty()
                        : false;
        }

        @Override
        public void move(MoverType type, Vec3 pos) {
            if (!this.fixed) {
                super.move(type, pos);
            }
        }

        @Override
        public void push(double x, double y, double z) {
            if (!this.fixed) {
                super.push(x, y, z);
            }
        }

        @Override
        public void kill() {
            super.kill();
        }
        /**
         * Called when the entity is attacked.
         */
        @Override
        public boolean hurt(DamageSource source, float amount) {
            return !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !source.isCreativePlayer() ? false : super.hurt(source, amount);
        }

        /**
         * Checks if the entity is in range to render.
         */
        @Override
        public boolean shouldRenderAtSqrDistance(double distance) {
            double d0 = 16.0;
            d0 *= 64.0 * getViewScale();
            return distance < d0 * d0;
        }

        /**
         * Called when this entity is broken. Entity parameter may be null.
         */
        @Override
        public void dropItem(@Nullable Entity brokenEntity) {
            this.playSound(SoaSounds.PORTAL_SUMMON.get(), 0.2F, 1.0F);
        }

        @Override
        public void playPlacementSound() {
            this.playSound(SoaSounds.PORTAL_WITHER.get(), 0.2F, 1.0F);
        }

        public void setDestination(Optional<BlockPos> destPos) {
            this.getEntityData().set(DATA_DESTINATION, destPos);
        }

        @Override
        public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
            if (key.equals(DATA_DESTINATION)) {
                Soa.LOGGER.debug("destination changed");
                //this.onItemChanged(this.getItem());
            }
        }

        public int getRotation() {
            return this.getEntityData().get(DATA_ROTATION);
        }

        public void setRotation(int rotation) {
            this.setRotation(rotation, true);
        }

        private void setRotation(int rotation, boolean updateNeighbours) {
            this.getEntityData().set(DATA_ROTATION, rotation % 8);
            if (updateNeighbours && this.pos != null) {
                this.level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
            }
        }
        public Optional<BlockPos> getDest() {
            return this.getEntityData().get(DATA_DESTINATION);
        }
        @Override
        public void addAdditionalSaveData(CompoundTag compound) {
            super.addAdditionalSaveData(compound);
            if (this.getDest().isEmpty()) {
                compound.putBoolean("SoaValid", false);
                compound.putIntArray("SoaDestination", new int[]{0, 0, 0});
            }
            else {
                BlockPos bp  = this.getDest().get();
                compound.putBoolean("SoaValid", true);
                compound.putIntArray("SoaDestination", new int[]{bp.getX(), bp.getY(), bp.getZ()});

            }

            compound.putByte("Facing", (byte)this.direction.get3DDataValue());
        }

        /**
         * (abstract) Protected helper method to read subclass entity data from NBT.
         */
        @Override
        public void readAdditionalSaveData(CompoundTag compound) {
            super.readAdditionalSaveData(compound);
            ItemStack itemstack;
            if (compound.getBoolean("SoaVaild")) {
                int[] destarr = compound.getIntArray("SoaDestination");
                this.setDestination(
                        Optional.of(
                               new BlockPos(
                                       destarr[0],
                                       destarr[1],
                                       destarr[2]
                               )
                        )
                );
            } else {
                this.setDestination(Optional.empty());
            }

            this.setDirection(Direction.from3DDataValue(compound.getByte("Facing")));
        }

        @Override
        public InteractionResult interact(Player player, InteractionHand hand) {

            return InteractionResult.CONSUME;
        }

        @Override
        public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
            return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
        }

        @Override
        public void recreateFromPacket(ClientboundAddEntityPacket packet) {
            super.recreateFromPacket(packet);
            this.setDirection(Direction.from3DDataValue(packet.getData()));
        }

    }


