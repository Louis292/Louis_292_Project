package fr.louis.louis_292_project.DataBase.Langue.Command;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import fr.louis.louis_292_project.DataBase.Langue.Langue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class LangueCommand implements CommandExecutor {
    private FileConfiguration config;
    private final DataBaseManager dataBaseManager;
    private final Langue langueGetter;

    public LangueCommand(DataBaseManager dataBaseManager, FileConfiguration config) {
        this.dataBaseManager = dataBaseManager;
        this.langueGetter = new Langue(dataBaseManager);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent utiliser cette commande.");
            return true;
        }

        Player player = (Player) sender;
        openLanguageGUI(player);
        return true;
    }

    private void openLanguageGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, langueGetter.StringLanguePlayer(player, "Changer la langue", "Change the language"));

        ItemStack frenchBanner = createBanner("Francais", "blue");
        ItemStack englishBanner = createBanner("Anglais UK", "red");

        gui.setItem(12, frenchBanner);
        gui.setItem(14, englishBanner);

        player.openInventory(gui);
    }

    private ItemStack createBanner(String name, String baseColor) {
        ItemStack banner = new ItemStack(Material.BANNER, 1);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
        bannerMeta.setDisplayName(ChatColor.RESET + name);
        bannerMeta.setBaseColor(DyeColor.valueOf(baseColor.toUpperCase()));
        banner.setItemMeta(bannerMeta);

        return banner;
    }
}