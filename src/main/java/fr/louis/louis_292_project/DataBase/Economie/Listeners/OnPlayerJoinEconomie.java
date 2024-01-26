package fr.louis.louis_292_project.DataBase.Economie.Listeners;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class OnPlayerJoinEconomie implements Listener {

    private DataBaseManager economie;

    public OnPlayerJoinEconomie(DataBaseManager economie) {
        this.economie = economie;
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Vérifier si le joueur est déjà enregistré dans la base de données
        if (!playerExists(player.getUniqueId())) {
            // Si le joueur n'est pas enregistré, créer une nouvelle ligne dans la base de données
            createPlayer(player.getUniqueId(), player.getName());
        } else {
            // Si le joueur existe, mettre à jour le nom du joueur et le timestamp de création
            updatePlayerName(player.getUniqueId(), player.getName());
        }

        // Récupérer la valeur de money depuis la base de données et l'envoyer au joueur
        double money = getPlayerMoney(player.getUniqueId());
        player.sendMessage("Votre solde est de : " + ChatColor.GREEN + money);
    }

    private boolean playerExists(UUID uuid) {
        try (Connection connection = economie.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT uuid FROM player_data_money WHERE uuid = ?")) {
            statement.setString(1, uuid.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createPlayer(UUID uuid, String playerName) {
        try (Connection connection = economie.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO player_data_money (uuid, player_name, money, created_at) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, playerName);
            statement.setDouble(3, 10.0); // Initialiser la valeur à 10
            statement.setTimestamp(4, getCurrentTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePlayerName(UUID uuid, String playerName) {
        try (Connection connection = economie.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE player_data_money SET player_name = ?, created_at = ? WHERE uuid = ?")) {
            statement.setString(1, playerName);
            statement.setTimestamp(2, getCurrentTimestamp());
            statement.setString(3, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double getPlayerMoney(UUID uuid) {
        try (Connection connection = economie.getConnection();
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

    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
