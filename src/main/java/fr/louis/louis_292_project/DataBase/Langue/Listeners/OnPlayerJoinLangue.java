package fr.louis.louis_292_project.DataBase.Langue.Listeners;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import fr.louis.louis_292_project.DataBase.Langue.Langue;
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

public class OnPlayerJoinLangue implements Listener {
    private final DataBaseManager dataBaseManager;
    private final Langue langue;
    public String Default_langue = "FR_fr";

    public OnPlayerJoinLangue(DataBaseManager dataBaseManager, Langue langue) {
        this.dataBaseManager = dataBaseManager;
        this.langue = langue;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = event.getPlayer().getName();
        UUID playerUUID = event.getPlayer().getUniqueId();

        try (Connection connection = dataBaseManager.getLanguageConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_data_langue WHERE uuid = ?")) {

            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            String playerLangue = Default_langue; // Default language if not found

            if (resultSet.next()) {
                // Player found in the table, retrieve language from the database
                playerLangue = resultSet.getString("langue");
            } else {
                // Player not found in the table, insert a new entry
                try (PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_data_langue (uuid, player_name, langue, last_update, created_at) VALUES (?, ?, ?, ?, ?)")) {

                    insertStatement.setString(1, playerUUID.toString());
                    insertStatement.setString(2, playerName);
                    insertStatement.setString(3, Default_langue);
                    insertStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    insertStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

                    insertStatement.executeUpdate();
                }
            }

            // Ensure that 'langue' is not null before using it
            if (langue != null) {
                player.sendMessage(langue.StringLanguePlayer(player, ChatColor.GREEN + "Bienvenue sur le serveur !", ChatColor.GREEN + "Welcome to the server!"));
            } else {
                // Handle the case where 'langue' is null
                player.sendMessage(ChatColor.RED + "Erreur: Langue non initialisée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}