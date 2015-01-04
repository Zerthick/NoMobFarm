/*
 * Copyright (C) 2015 zerthick
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.gmail.zerthick.nomobfarm;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author zerthick
 */
public final class NoMobFarmMain extends JavaPlugin implements Listener {

    Set<UUID> spawnerSet;

    @Override
    public void onEnable() {
        spawnerSet = new HashSet<>();
        this.getServer().getPluginManager().registerEvents(this, this);  //register events
        this.getLogger().log(Level.INFO, "NoMobFarm Enabled!");
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == SpawnReason.SPAWNER) {
            spawnerSet.add(event.getEntity().getUniqueId());  //if the entity came from a spawner add it to the list
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (!(entity.getType() == EntityType.PLAYER)) {  //if the entity that died is not a player
            if (spawnerSet.remove(entity.getUniqueId()) || //if the entity that died came from a spawner
                    !(entity.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK || 
                        entity.getLastDamageCause().getCause() == DamageCause.PROJECTILE ||
                        entity.getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION ||
                        entity.getLastDamageCause().getCause() == DamageCause.THORNS)) {  //if the entity that died was not killed by an entity
                event.getDrops().clear();  //clear the drops
                event.setDroppedExp(0);  //clear the exp
            }
        }
    }
}
