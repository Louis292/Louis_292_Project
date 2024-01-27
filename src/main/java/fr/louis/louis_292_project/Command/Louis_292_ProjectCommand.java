package fr.louis.louis_292_project.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Louis_292_ProjectCommand implements CommandExecutor {
    private final FileConfiguration config;

    public Louis_292_ProjectCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("Louis_292_Project.use.*")) {
            player.sendMessage(ChatColor.GREEN + "===============================================================");
            player.sendMessage(ChatColor.GREEN + "| Plugin Louis_292_Project V" + config.getString("version"));
            player.sendMessage(ChatColor.GREEN + "| ");
            player.sendMessage(ChatColor.GREEN + "| Mode debug " + config.getBoolean("debug"));
            player.sendMessage(ChatColor.GREEN + "| ");
            player.sendMessage(ChatColor.GREEN + "| DataBase " + config.getBoolean("DataBase"));

            if (config.getBoolean("DataBase")) {
                player.sendMessage(ChatColor.GREEN + "| ");
                player.sendMessage(ChatColor.GREEN + "| Driver : " + config.getString("DataBase_Tools.driver"));
                player.sendMessage(ChatColor.GREEN + "| User : " + config.getString("DataBase_Tools.user"));
                player.sendMessage(ChatColor.GREEN + "| pass : " + config.getString("DataBase_Tools.pass"));
                player.sendMessage(ChatColor.GREEN + "| Nom de la base de donné : " + config.getString("DataBase_Tools.DataBase_Name"));
                player.sendMessage(ChatColor.GREEN + "| Port : " + config.getInt("DataBase_Tools.port"));
            }
            player.sendMessage(ChatColor.GREEN + "| ");
            player.sendMessage(ChatColor.GREEN + "| Fonctionnalité disponible :");
            player.sendMessage(ChatColor.GREEN + "| - Random Wool Color");
            player.sendMessage(ChatColor.GREEN + "| - Money DataBase");
            player.sendMessage(ChatColor.GREEN + "| - Example Commands");
            player.sendMessage(ChatColor.GREEN + "| - Example Listeners");
            player.sendMessage(ChatColor.GREEN + "| - Example Configuration");
            player.sendMessage(ChatColor.GREEN + "| - Home Plugin");
            player.sendMessage(ChatColor.GREEN + "| - Tâche Répété");
            player.sendMessage(ChatColor.GREEN + "| - Random Téléportation commandes");
            player.sendMessage(ChatColor.GREEN + "| - Example CustomItem Commandes");
            player.sendMessage(ChatColor.GREEN + "===============================================================");
        }

        return false;
    }
}