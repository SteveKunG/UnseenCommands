package com.stevekung.unseencommands;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

@Mixin(DebugPackets.class)
public class MixinDebugPackets
{
    @Shadow
    static void sendPacketToAllPlayers(ServerLevel serverLevel, FriendlyByteBuf friendlyByteBuf, ResourceLocation resourceLocation) {}

    //    @Overwrite
    //    public static void sendNeighborsUpdatePacket(Level level, BlockPos pos)
    //    {
    //        var friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
    //
    //        friendlyByteBuf.writeVarLong(level.getGameTime());
    //        friendlyByteBuf.writeBlockPos(pos);
    //
    //        sendPacketToAllPlayers((ServerLevel) level, friendlyByteBuf, ClientboundCustomPayloadPacket.DEBUG_NEIGHBORSUPDATE_PACKET);
    //    }

    //    @Overwrite
    //    public static void sendGameEventInfo(Level level, GameEvent gameEvent, BlockPos pos)
    //    {
    //        var friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
    //
    //        friendlyByteBuf.writeUtf(gameEvent.getName());
    //        friendlyByteBuf.writeBlockPos(pos);
    //
    //        sendPacketToAllPlayers((ServerLevel) level, friendlyByteBuf, ClientboundCustomPayloadPacket.DEBUG_GAME_EVENT);
    //    }

    //    // Not working
    //    @Overwrite
    //    public static void sendStructurePacket(WorldGenLevel level, StructureStart<?> structureStart)
    //    {
    //        var minecraft = Minecraft.getInstance();
    //
    //        if (minecraft.getConnection() == null || minecraft.level == null)
    //        {
    //            return;
    //        }
    //
    //        var friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
    //        var bb = structureStart.getBoundingBox();
    //        var size = structureStart.getPieces().size();
    //
    //        friendlyByteBuf.writeResourceLocation(DimensionType.OVERWORLD_LOCATION.location());
    //        friendlyByteBuf.writeInt(bb.minX());
    //        friendlyByteBuf.writeInt(bb.minY());
    //        friendlyByteBuf.writeInt(bb.minZ());
    //        friendlyByteBuf.writeInt(bb.maxX());
    //        friendlyByteBuf.writeInt(bb.maxY());
    //        friendlyByteBuf.writeInt(bb.maxZ());
    //
    //        friendlyByteBuf.writeInt(size);
    //
    //        for (var piece : structureStart.getPieces())
    //        {
    //            var bb2 = piece.getBoundingBox();
    //            friendlyByteBuf.writeInt(bb2.minX());
    //            friendlyByteBuf.writeInt(bb2.minY());
    //            friendlyByteBuf.writeInt(bb2.minZ());
    //            friendlyByteBuf.writeInt(bb2.maxX());
    //            friendlyByteBuf.writeInt(bb2.maxY());
    //            friendlyByteBuf.writeInt(bb2.maxZ());
    //            friendlyByteBuf.writeBoolean(true);
    //        }
    //
    //        var packet = new ClientboundCustomPayloadPacket(ClientboundCustomPayloadPacket.DEBUG_STRUCTURES_PACKET, friendlyByteBuf);
    //        minecraft.execute(() -> packet.handle(minecraft.getConnection()));
    //    }

    /**
     * @reason Make path debugging work
     * @author SteveKunG
     */
    @Overwrite
    public static void sendPathFindingPacket(Level level, Mob mob, @Nullable Path path, float maxDistanceToWaypoint)
    {
        if (path == null)
        {
            return;
        }

        var friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());

        friendlyByteBuf.writeInt(mob.getId());
        friendlyByteBuf.writeFloat(maxDistanceToWaypoint);
        path.writeToStream(friendlyByteBuf);

        sendPacketToAllPlayers((ServerLevel) level, friendlyByteBuf, ClientboundCustomPayloadPacket.DEBUG_PATHFINDING_PACKET);
    }
}