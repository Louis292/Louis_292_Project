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

public class AddMonayCommand implements CommandExecutor {
    private final DataBaseManager dataBaseManager;

    public AddMonayCommand(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent exécuter cette commande.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Utilisation incorrecte. Utilisation : /addmoney <joueur> <montant>");
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

        // Ajouter le montant à la base de données avec mise à jour de last_update
        try (Connection connection = dataBaseManager.getEconomyConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE player_data_money SET money = money + ?, last_update = NOW() WHERE uuid = ?")) {
            statement.setDouble(1, amount);
            statement.setString(2, target.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Une erreur s'est produite lors de l'ajout d'argent à la base de données.");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + "Vous avez ajouté " + amount + " d'argent à " + playerName + "'s compte.");

        return true;
    }
}