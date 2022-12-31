package io.github.zerthick.nomobfarm;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationSettings {

    public final boolean preventSpawnerExp;
    public final boolean preventSpawnerDrops;
    public final boolean dropRule;

    public ConfigurationSettings(final FileConfiguration config) {
        this.preventSpawnerExp = config.getBoolean("preventSpawnerExp");
        this.preventSpawnerDrops = config.getBoolean("preventSpawnerDrops");
        this.dropRule = config.getBoolean("dropRule");
    }

}
