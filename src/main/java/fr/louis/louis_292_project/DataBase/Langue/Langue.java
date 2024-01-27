package fr.louis.louis_292_project.DataBase.Langue;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Langue {

    private final DataBaseManager dataBaseManager;

    public Langue(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public String getPlayerLangue(Player player) {
        return getPlayerLangue(player.getUniqueId());
    }

    public String getPlayerLangue(UUID playerUUID) {
        String playerLangue = "EN_uk"; // Default value if language is not found

        try (Connection connection = dataBaseManager.getLanguageConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT langue FROM player_data_langue WHERE uuid = ?")) {

            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                playerLangue = resultSet.getString("langue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerLangue;
    }

    public void setPlayerLangue(UUID playerUUID, String langue) {
        try (Connection connection = dataBaseManager.getLanguageConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE player_data_langue SET langue = ? WHERE uuid = ?")) {

            statement.setString(1, langue);
            statement.setString(2, playerUUID.toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String StringLanguePlayer(Player player, String FR_fr, String EN_uk) {

        if (getPlayerLangue(player).equalsIgnoreCase("FR_fr")) {
            return FR_fr;
        } else if (getPlayerLangue(player).equalsIgnoreCase("EN_uk")) {
            return EN_uk;
        } else {
            return EN_uk;
        }
    }
}