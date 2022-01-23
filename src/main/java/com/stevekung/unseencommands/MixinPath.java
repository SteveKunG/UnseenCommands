package com.stevekung.unseencommands;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

@Mixin(Path.class)
public class MixinPath
{
    @Shadow
    @Final
    boolean reached;

    @Shadow
    @Final
    List<Node> nodes;

    @Shadow
    @Final
    BlockPos target;

    @Shadow
    Node[] openSet;

    @Shadow
    Node[] closedSet;

    @Shadow
    int nextNodeIndex;

    /**
     * @reason Make path debugging work
     * @author SteveKunG
     */
    @Overwrite
    public void writeToStream(FriendlyByteBuf friendlyByteBuf)
    {
        friendlyByteBuf.writeBoolean(this.reached);
        friendlyByteBuf.writeInt(this.nextNodeIndex);
        friendlyByteBuf.writeBlockPos(this.target);
        friendlyByteBuf.writeInt(this.nodes.size());

        for (var node : this.nodes)
        {
            node.writeToStream(friendlyByteBuf);
        }

        friendlyByteBuf.writeInt(this.openSet.length);

        for (var node2 : this.openSet)
        {
            node2.writeToStream(friendlyByteBuf);
        }

        friendlyByteBuf.writeInt(this.closedSet.length);

        for (var node2 : this.closedSet)
        {
            node2.writeToStream(friendlyByteBuf);
        }
    }

    /**
     * @reason Make path debugging work
     * @author SteveKunG
     */
    @Overwrite
    public static Path createFromStream(FriendlyByteBuf friendlyByteBuf)
    {
        var reached = friendlyByteBuf.readBoolean();
        var nextNodeIndex = friendlyByteBuf.readInt();
        var target = friendlyByteBuf.readBlockPos();
        var list = Lists.<Node>newArrayList();
        var nodesSize = friendlyByteBuf.readInt();

        for (var i = 0; i < nodesSize; ++i)
        {
            list.add(Node.createFromStream(friendlyByteBuf));
        }

        var openSet = new Node[friendlyByteBuf.readInt()];

        for (var i = 0; i < openSet.length; ++i)
        {
            openSet[i] = Node.createFromStream(friendlyByteBuf);
        }

        var closedSet = new Node[friendlyByteBuf.readInt()];

        for (var i = 0; i < closedSet.length; ++i)
        {
            closedSet[i] = Node.createFromStream(friendlyByteBuf);
        }

        var path = new Path(list, target, reached);
        ((MixinPath)(Object)path).openSet = openSet;
        ((MixinPath)(Object)path).closedSet = closedSet;
        ((MixinPath)(Object)path).nextNodeIndex = nextNodeIndex;
        return path;
    }
}