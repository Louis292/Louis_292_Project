package fr.louis.louis_292_project.DataBase.Economie.Command;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MoneyCommand implements CommandExecutor {
    private DataBaseManager economie;

    public MoneyCommand(DataBaseManager economie) {
        this.economie = economie;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("money")) {
            if (args.length == 0) {
                // Si pas d'argument, affiche l'argent du joueur qui a envoyé la commande
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    double money = getPlayerMoney(player.getUniqueId());
                    player.sendMessage("Votre money est de : " + ChatColor.GREEN + money);
                } else {
                    // La commande ne peut être utilisée que par un joueur en jeu
                    sender.sendMessage(ChatColor.RED + "Cette commande ne peut être utilisée que par un joueur en jeu.");
                }
            } else if (args.length == 1) {
                // Si un argument est spécifié, recherche l'argent du joueur spécifié
                String playerName = args[0];
                UUID playerUUID = getPlayerUUID(playerName);
                if (playerUUID != null) {
                    double money = getPlayerMoney(playerUUID);
                    sender.sendMessage("L'argent de " + playerName + " est de : " + ChatColor.GREEN + money);
                } else {
                    sender.sendMessage(ChatColor.RED + "Joueur non trouvé dans la base de données.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Utilisation incorrecte de la commande. /money [joueur]");
            }
            return true;
        }
        return false;
    }

    private double getPlayerMoney(UUID uuid) {
        try (Connection connection = economie.getEconomyConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT money FROM player_data_money WHERE uuid = ?")) {
            statement.setString(1, uuid.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("money");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Si quelque chose ne va pas, retourner une valeur par défaut
    }

    private UUID getPlayerUUID(String playerName) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            return player.getUniqueId();
        } else {
            // Si le joueur n'est pas en ligne, recherchez son UUID dans la base de données
            try (Connection connection = economie.getEconomyConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT uuid FROM player_data_money WHERE LOWER(player_name) = LOWER(?)")) {
                statement.setString(1, playerName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return UUID.fromString(resultSet.getString("uuid"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}