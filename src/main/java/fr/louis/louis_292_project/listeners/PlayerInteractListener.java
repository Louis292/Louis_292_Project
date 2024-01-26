package fr.louis.louis_292_project.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Cauldron;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        final Action action = event.getAction();
        final Block block = event.getClickedBlock();
        final Player player = event.getPlayer();

        if (action == Action.RIGHT_CLICK_BLOCK) {

            if (block.getType() == Material.CAULDRON) {

                if (player.getInventory().getItemInHand().getType().equals(Material.EMERALD)) {

                    final Cauldron cauldron = (Cauldron) block.getState().getData();

                    if (cauldron.isFull()) {

                        Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                        final World world = player.getWorld();
                        world.spawnEntity(block.getLocation().add(0.5, 0.5, 0.5), EntityType.LIGHTNING);

                        cauldron.isEmpty();

                        world.spawnEntity(block.getLocation().add(0.5, 0.5, 0.5), EntityType.ZOMBIE);

                        zombie.setCustomName("Zombie Niveau 1");
                        zombie.setVillager(true);

                        zombie.setHealth(40);

                        PotionEffect increaseDamage = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2, false, false);

                        zombie.addPotionEffect(increaseDamage);

                        PotionEffect FirePortect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 2, false, false);

                        zombie.addPotionEffect(FirePortect);

                    } else {
                        player.sendMessage(ChatColor.RED + "Le chaudron dois être rempli");
                    }

                }

            }

        }

    }

}
