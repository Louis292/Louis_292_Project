package fr.louis.louis_292_project.DataBase.Economie.Command;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PayMoneyCommand implements CommandExecutor {
    private final DataBaseManager dataBaseManager;

    public PayMoneyCommand(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent exécuter cette commande.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Utilisation incorrecte. Utilisation : /pay <joueur> <montant>");
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

        if (sender.equals(target)) {
            sender.sendMessage(ChatColor.RED + "Vous ne pouvez pas vous payer vous-même.");
            return true;
        }

        try (Connection connection = dataBaseManager.getConnection()) {
            // Vérifier si le joueur a suffisamment d'argent
            try (PreparedStatement checkStatement = connection.prepareStatement("SELECT money FROM player_data_money WHERE player_name = ?")) {
                checkStatement.setString(1, sender.getName());
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    double senderMoney = resultSet.getDouble("money");

                    if (senderMoney >= amount) {
                        // Enlever l'argent du sender
                        try (PreparedStatement removeStatement = connection.prepareStatement("UPDATE player_data_money SET money = money - ?, last_update = ? WHERE player_name = ?")) {
                            removeStatement.setDouble(1, amount);
                            removeStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                            removeStatement.setString(3, sender.getName());
                            removeStatement.executeUpdate();
                        }

                        // Ajouter l'argent au joueur cible
                        try (PreparedStatement addStatement = connection.prepareStatement("UPDATE player_data_money SET money = money + ?, last_update = ? WHERE player_name = ?")) {
                            addStatement.setDouble(1, amount);
                            addStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                            addStatement.setString(3, playerName);
                            addStatement.executeUpdate();
                        }

                        sender.sendMessage("Vous avez payé " + amount + " à " + playerName + ".");
                        target.sendMessage("Vous avez reçu " + amount + " de " + sender.getName());
                    } else {
                        sender.sendMessage(ChatColor.RED + "Vous n'avez pas suffisamment d'argent.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Erreur lors de la récupération de l'argent du sender.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Une erreur s'est produite lors du paiement.");
        }

        return true;
    }
}