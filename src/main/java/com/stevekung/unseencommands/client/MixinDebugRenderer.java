package com.stevekung.unseencommands.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.*;
import net.minecraft.client.renderer.debug.DebugRenderer.SimpleDebugRenderer;

@Mixin(DebugRenderer.class)
public class MixinDebugRenderer
{
    @Shadow @Final PathfindingRenderer pathfindingRenderer;
    @Shadow @Final SimpleDebugRenderer waterDebugRenderer;
    @Shadow @Final SimpleDebugRenderer heightMapRenderer;
    @Shadow @Final SimpleDebugRenderer collisionBoxRenderer;
    @Shadow @Final SimpleDebugRenderer neighborsUpdateRenderer;
    @Shadow @Final StructureRenderer structureRenderer;
    @Shadow @Final SimpleDebugRenderer lightDebugRenderer;
    @Shadow @Final SimpleDebugRenderer worldGenAttemptRenderer;
    @Shadow @Final SimpleDebugRenderer solidFaceRenderer;
    @Shadow @Final SimpleDebugRenderer chunkRenderer;
    @Shadow @Final BrainDebugRenderer brainDebugRenderer;
    @Shadow @Final VillageSectionsDebugRenderer villageSectionsDebugRenderer;
    @Shadow @Final BeeDebugRenderer beeDebugRenderer;
    @Shadow @Final RaidDebugRenderer raidDebugRenderer;
    @Shadow @Final GoalSelectorDebugRenderer goalSelectorRenderer;
    @Shadow @Final GameEventListenerRenderer gameEventListenerRenderer;

    @Inject(method = "render", at = @At("TAIL"))
    private void render(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, double camX, double camY, double camZ, CallbackInfo info)
    {
        this.pathfindingRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.waterDebugRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.heightMapRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.collisionBoxRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.neighborsUpdateRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.structureRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.lightDebugRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.solidFaceRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.chunkRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        //        this.gameEventListenerRenderer.render(poseStack, bufferSource, camX, camY, camZ);
    }
}