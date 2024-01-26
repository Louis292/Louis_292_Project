package fr.louis.louis_292_project.Command;

import com.avaje.ebeaninternal.server.deploy.InheritInfoVisitor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ZombieSpawningCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        Player player = (Player) sender;


        Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

        zombie.setHealth(20);

        zombie.setCustomName("Zombie LVL1");

        zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));

        ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta ironSwordMeta = ironSword.getItemMeta();
        ironSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
        ironSword.setItemMeta(ironSwordMeta);

        zombie.getEquipment().setItemInHand(ironSword);

        // PotionEffect increaseDamage = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2, false, false);

        // zombie.addPotionEffect(increaseDamage);

        zombie.setVillager(true);

        zombie.setBaby(false);

        player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 5, 5);

        Bukkit.broadcast("Zombie de LVL1 vien d'apparaitre en"  + player.getLocation(), "");

        return false;
    }
}
