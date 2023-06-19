package org.teacon.commandconfirm;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CommandConfirm.ID)
public class EventHandler {
    @SubscribeEvent
    public static void onCommand(CommandEvent event) {
        var rawCommand = event.getParseResults().getReader().getString();
        var context = event.getParseResults().getContext();
        var source = context.getSource();

        var player = source.getPlayer();
        // Ignore non-player executed commands
        if (player == null) return;

        // Bypass command check if executed for multiple times
        if (CommandHistory.checkPlayerCommand(player, rawCommand)) return;

        int count = -1;
        for (var entry : context.getArguments().values()) {
            if (entry.getResult() instanceof EntitySelector selector) {
                try {
                    var entities = selector.findEntities(source);
                    count = Math.max(count, entities.size());
                } catch (CommandSyntaxException ignore) {
                }
            }
        }

        if (count > Config.warningLimit.get()) {
            event.setCanceled(true);
            source.sendFailure(Component.literal("WARNING: " + count + "entities will be affected by this command. " +
                    "Execute again to proceed."));
        }
    }
}
