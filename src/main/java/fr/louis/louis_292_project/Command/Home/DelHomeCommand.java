package fr.louis.louis_292_project.Command.Home;

import fr.louis.louis_292_project.Louis_292_Project;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DelHomeCommand implements CommandExecutor {

    private Louis_292_Project louis292Project;

    public DelHomeCommand(Louis_292_Project louis292Project) {
        this.louis292Project = louis292Project;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("delhome")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                final File file = new File(louis292Project.getDataFolder(), "homes.yml");
                final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                final String key = "players." + player.getUniqueId().toString();

                if (configuration.isConfigurationSection(key)) {
                    configuration.set(key, null);
                } else {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas de home à supprimer.");
                    return true;
                }

                try {
                    configuration.save(file);
                    player.sendMessage(ChatColor.GREEN + "Home supprimé avec succès.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }
        }
        return false;
    }
}