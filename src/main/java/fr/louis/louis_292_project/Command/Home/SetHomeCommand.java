package fr.louis.louis_292_project.Command.Home;

import fr.louis.louis_292_project.Louis_292_Project;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SetHomeCommand implements CommandExecutor {

    private Louis_292_Project louis292Project;

    public SetHomeCommand(Louis_292_Project louis292Project) {
        this.louis292Project = louis292Project;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("sethome")) {
            if (args.length == 0) {

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    final Location location = player.getLocation();

                    final UUID uuid = player.getUniqueId();

                    final File file = new File(louis292Project.getDataFolder(), "homes.yml");
                    final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                    final String key = "players." + uuid.toString();

                    configuration.set(key + ".world", location.getWorld().getName());
                    configuration.set(key + ".x", location.getX());
                    configuration.set(key + ".y", location.getY());
                    configuration.set(key + ".z", location.getZ());
                    configuration.set(key + ".yaw", location.getYaw());
                    configuration.set(key + ".pitch", location.getPitch());

                    try {
                        configuration.save(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    player.sendMessage(ChatColor.GREEN + "Home crée avec succés");
                }

                return  true;
            }
        }

        return false;
    }
}
