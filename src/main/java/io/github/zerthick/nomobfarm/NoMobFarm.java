package io.github.zerthick.nomobfarm;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public final class NoMobFarm extends JavaPlugin implements Listener {

    private Logger logger;
    private Set<UUID> spawnerSet;
    private ConfigurationSettings configurationSettings;

    @Override
    public void onEnable() {
        logger = getLogger();

        spawnerSet = new HashSet<>();
        getServer().getPluginManager().registerEvents(this, this);  //register events

        saveDefaultConfig();
        configurationSettings = new ConfigurationSettings(this.getConfig());

        logger.info(String.format("%s version %s by %s enabled!", getDescription().getName(), getDescription().getVersion(), getDescription().getAuthors()));
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == SpawnReason.SPAWNER) {
            spawnerSet.add(event.getEntity().getUniqueId());  //if the entity came from a spawner add it to the list
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (!(entity.getType() == EntityType.PLAYER)) {  //if the entity that died is not a player
            if (spawnerSet.remove(entity.getUniqueId())) {  //if the entity that died came from a spawner
                if (configurationSettings.preventSpawnerDrops) {
                    event.getDrops().clear();  //clear the drops
                }
                if (configurationSettings.preventSpawnerExp) {
                    event.setDroppedExp(0);  //clear the exp
                }
            }

            // If the entity was not killed by a player, skip armor stands
            if (configurationSettings.dropRule && entity.getType() != EntityType.ARMOR_STAND && entity.getKiller() == null) {
                event.getDrops().clear();  //clear the drops
                event.setDroppedExp(0);  //clear the exp
            }
        }
    }
}
