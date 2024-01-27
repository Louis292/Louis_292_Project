package fr.louis.louis_292_project.DataBase.Economie.Command;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RemoveMoneyCommand implements CommandExecutor {
    private final DataBaseManager dataBaseManager;

    public RemoveMoneyCommand(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent exécuter cette commande.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Utilisation incorrecte. Utilisation : /removemoney <joueur> <montant>");
            return true;
        }

        String playerName = args[0];
        double amount;

        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Le montant doit être un nombre.");
            return true;
        }

        Player target = sender.getServer().getPlayer(playerName);

        if (target == null || !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Le joueur spécifié n'est pas en ligne.");
            return true;
        }

        // Enlever le montant de la base de données
        try (Connection connection = dataBaseManager.getEconomyConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE player_data_money SET money = money - ?, last_update = ? WHERE player_name = ?")) {
            statement.setDouble(1, amount);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setString(3, playerName);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                sender.sendMessage(ChatColor.GREEN + "Vous avez retiré " + amount + " d'argent à " + playerName + ".");
            } else {
                sender.sendMessage(ChatColor.RED + "Le joueur spécifié n'a pas suffisamment d'argent.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Une erreur s'est produite lors de la suppression d'argent dans la base de données.");
        }

        return true;
    }
}