package org.teacon.commandconfirm;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = CommandConfirm.ID)
public class EventHandler {
    public static final Map<String, String> TEMPLATES = Map.of(
            "en_us", "WARNING: %d entities will be affected by this command! Execute again to proceed.",
            "zh_cn", "警告：%d 个实体将被该命令影响！再次执行以继续。"
    );

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
            var lang = player.getLanguage();
            if (!TEMPLATES.containsKey(lang)) lang = "en_us";
            source.sendFailure(Component.literal(String.format(TEMPLATES.get(lang), count)));
        }
    }
}
