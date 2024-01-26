package fr.louis.louis_292_project.Command.Home;

import fr.louis.louis_292_project.Louis_292_Project;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.print.attribute.standard.Finishings;
import java.io.File;

public class HomeCommand implements CommandExecutor {

    private Louis_292_Project louis292Project;

    public HomeCommand(Louis_292_Project louis292Project) {
        this.louis292Project = louis292Project;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("home")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    final File file = new File(louis292Project.getDataFolder(), "homes.yml");
                    final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                    final String key = "players." + player.getUniqueId();

                    final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

                    if (configurationSection == null) {
                        player.sendMessage(ChatColor.RED + "Aucun home enregistré");
                    } else {
                        final World world = Bukkit.getWorld(configurationSection.getString("world"));
                        final double x = configurationSection.getDouble("x");
                        final double y = configurationSection.getDouble("y");
                        final double z = configurationSection.getDouble("z");
                        final float yaw = (float) configurationSection.getDouble("yaw");
                        final float pitch = (float) configurationSection.getDouble("pitch");

                        final Location HomeLocation = new Location(world, x, y, z, yaw, pitch);

                        player.teleport(HomeLocation);

                        player.sendMessage(ChatColor.GREEN + "Vous avez été téléporter à votre home");
                    }
                }
            }
        }
        return false;
    }
}
