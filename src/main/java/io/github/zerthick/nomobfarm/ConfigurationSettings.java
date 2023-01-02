package io.github.zerthick.nomobfarm;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationSettings {

    public final boolean preventSpawnerExp;
    public final boolean preventSpawnerDrops;
    public final boolean dropRule;

    public ConfigurationSettings(final FileConfiguration config) {
        preventSpawnerExp = config.getBoolean("preventSpawnerExp");
        preventSpawnerDrops = config.getBoolean("preventSpawnerDrops");
        dropRule = config.getBoolean("dropRule");
    }

}
