package org.teacon.commandconfirm;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> warningLimit;

    static {
        var builder = new ForgeConfigSpec.Builder();

        warningLimit = builder.comment("Amount of entities involved in a command that triggers the confirmation warning")
                .define("warningLimit", 32);

        SPEC = builder.build();
    }
}
