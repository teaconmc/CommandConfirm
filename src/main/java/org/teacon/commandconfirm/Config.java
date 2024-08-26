package org.teacon.commandconfirm;


import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Integer> warningLimit;

    static {
        var builder = new ModConfigSpec.Builder();

        warningLimit = builder.comment("Amount of entities involved in a command that triggers the confirmation warning")
                .define("warningLimit", 32);

        SPEC = builder.build();
    }
}
