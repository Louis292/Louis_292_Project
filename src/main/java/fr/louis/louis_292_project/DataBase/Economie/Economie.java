package fr.louis.louis_292_project.DataBase.Economie;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Economie implements Listener {
    private final DataBaseManager dataBaseManager;

    public Economie(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        updatePlayerEconomy(playerUUID);
    }

    private void updatePlayerEconomy(UUID playerUUID) {
        try (Connection connection = dataBaseManager.getEconomyConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_data_economie WHERE uuid = ?")) {

            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double currentMoney = resultSet.getDouble("money");

                // Send a message to the player with their current money
                Player player = Bukkit.getPlayer(playerUUID);
                if (player != null) {
                    player.sendMessage("Votre argent : " + currentMoney);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getMoney(Player player) {
        UUID playerUUID = player.getUniqueId();
        try (Connection connection = dataBaseManager.getEconomyConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_data_economie WHERE uuid = ?")) {

            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double currentMoney = resultSet.getDouble("money");
                if (player != null) {
                    return (int) currentMoney;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add a default return statement
        return 0; // You can change this to another default value if needed
    }
}