package com.stevekung.unseencommands;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.server.commands.*;

@Mixin(Commands.class)
public class MixinCommands
{
    @Shadow
    @Final
    CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addSecretCommands(CommandSelection commandSelection, CallbackInfo info)
    {
        ChaseCommand.register(this.dispatcher);
        RaidCommand.register(this.dispatcher);
        ResetChunksCommand.register(this.dispatcher);
        SpawnArmorTrimsCommand.register(this.dispatcher);

        DebugPathCommand.register(this.dispatcher);
        DebugMobSpawningCommand.register(this.dispatcher);
    }
}