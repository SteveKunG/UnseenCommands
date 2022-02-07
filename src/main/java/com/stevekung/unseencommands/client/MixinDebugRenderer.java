package com.stevekung.unseencommands.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingRenderer;

@Mixin(DebugRenderer.class)
public class MixinDebugRenderer
{
    @Shadow
    @Final
    PathfindingRenderer pathfindingRenderer;

    @Inject(method = "render", at = @At("TAIL"))
    private void render(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, double camX, double camY, double camZ, CallbackInfo info)
    {
        //this.pathfindingRenderer.render(poseStack, bufferSource, camX, camY, camZ);
    }
}