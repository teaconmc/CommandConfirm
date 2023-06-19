package org.teacon.commandconfirm;

import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class CommandHistory {
    record Entry(String rawCommand, long time) {
    }

    private static final Map<UUID, Entry> history = new ConcurrentHashMap<>();

    public static boolean checkPlayerCommand(ServerPlayer player, String rawCommand) {
        var now = System.currentTimeMillis();
        var previous = history.put(player.getGameProfile().getId(), new Entry(rawCommand, now));
        if (previous == null) return false;
        if (now - previous.time > 5000) return false;
        return previous.rawCommand.equals(rawCommand);
    }
}
