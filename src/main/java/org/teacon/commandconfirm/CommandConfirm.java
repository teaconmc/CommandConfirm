package org.teacon.commandconfirm;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CommandConfirm.ID)
public final class CommandConfirm {
    public static final String ID = "commandconfirm";

    public static final Logger logger = LogManager.getLogger();

    public CommandConfirm() {
        logger.debug("CommandConfirm reached construction");

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
