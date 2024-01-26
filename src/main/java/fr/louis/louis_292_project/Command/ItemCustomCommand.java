package fr.louis.louis_292_project.Command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class ItemCustomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        Player player = (Player) sender;

        if (player.hasPermission("*")) {
            ItemStack Item = new ItemStack(Material.IRON_PICKAXE);
            ItemMeta ItemMeta = Item.getItemMeta();
            ItemMeta.setDisplayName(ChatColor.WHITE + "Custom Pickaxe Améliorer");

            ArrayList<String> Lore = new ArrayList<String>();
            Lore.add(ChatColor.WHITE + "Une Pioche Personalisé Améliorer");
            ItemMeta.setLore(Lore);

            ItemMeta.addEnchant(Enchantment.DIG_SPEED, 10, true);
            ItemMeta.addEnchant(Enchantment.DURABILITY, 5, true);

            Item.setAmount(1);

            Item.setItemMeta(ItemMeta);

            player.getInventory().addItem(Item);
        } else {
            ItemStack Item = new ItemStack(Material.IRON_PICKAXE);
            Item.setDurability((short) 5);
            ItemMeta ItemMeta = Item.getItemMeta();
            ItemMeta.setDisplayName("Custom Pickaxe");

            ArrayList<String> Lore = new ArrayList<String>();
            Lore.add(ChatColor.WHITE + "Une Pioche Personalisé");


            ItemMeta.setLore(Lore);

            Item.setAmount(1);

            Item.setItemMeta(ItemMeta);

            player.getInventory().addItem(Item);
        }

        return false;
    }
}
