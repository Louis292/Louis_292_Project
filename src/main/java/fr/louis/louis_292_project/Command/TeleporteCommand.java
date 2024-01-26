package fr.louis.louis_292_project.Command;

import fr.louis.louis_292_project.Louis_292_Project;
import fr.louis.louis_292_project.Schedulers.RandomTeleporte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleporteCommand implements CommandExecutor {

    private Louis_292_Project louis292Project;

    public TeleporteCommand (Louis_292_Project louis292Project) {
        this.louis292Project = louis292Project;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("rtp")) {

            final Location location = RandomTeleporte.generationLocation(player.getWorld());

            player.sendMessage( ChatColor.YELLOW + "Téléportation en cours");

            Bukkit.getScheduler().scheduleSyncDelayedTask(louis292Project, ()-> {
                player.teleport(location);

                player.sendMessage(ChatColor.GREEN + "Téléportation réussite");
            }, 20L*2);

            return true;
        }

        return false;
    }
}
