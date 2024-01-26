package fr.louis.louis_292_project.listeners.Zombie;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ZombieListener implements Listener {

    @EventHandler
    public void onZombieDie(EntityDeathEvent event) {
        Entity deadEntity = event.getEntity();

        if (deadEntity instanceof Zombie && deadEntity.getCustomName() != null && deadEntity.getCustomName().equals("Zombie LVL1")) {
            Bukkit.broadcastMessage("Zombie de Niveau 1 est Mort");
        }
    }
}