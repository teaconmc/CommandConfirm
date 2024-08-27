package org.teacon.commandconfirm;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CommandConfirm.ID)
public final class CommandConfirm {
    public static final String ID = "commandconfirm";

    public static final Logger logger = LoggerFactory.getLogger(CommandConfirm.class);

    public CommandConfirm(ModContainer modContainer) {
        logger.debug("CommandConfirm reached construction");

        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }
}
