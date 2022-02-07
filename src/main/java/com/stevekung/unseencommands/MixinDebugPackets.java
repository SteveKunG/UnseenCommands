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