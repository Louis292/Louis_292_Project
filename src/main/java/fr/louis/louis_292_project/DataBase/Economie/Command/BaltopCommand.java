package fr.louis.louis_292_project.DataBase.Economie.Command;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaltopCommand implements CommandExecutor {
    private final int TOP_PLAYERS_LIMIT = 5; // Limite de joueurs affichés dans le top

    private DataBaseManager economie;

    public BaltopCommand(DataBaseManager economie) {
        this.economie = economie;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("baltop")) {
            // Récupérer les 5 premiers joueurs avec le plus d'argent
            String topPlayers = getTopPlayers();

            // Afficher le classement des joueurs
            sender.sendMessage(ChatColor.GOLD + "Classement des joueurs avec le plus d'argent :");
            sender.sendMessage(topPlayers);
            return true;
        }
        return false;
    }

    private String getTopPlayers() {
        StringBuilder topPlayers = new StringBuilder();

        try (Connection connection = economie.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT player_name, money FROM player_data_money ORDER BY money DESC LIMIT ?")) {
            statement.setInt(1, TOP_PLAYERS_LIMIT);
            try (ResultSet resultSet = statement.executeQuery()) {
                int rank = 1;
                while (resultSet.next()) {
                    String playerName = resultSet.getString("player_name");
                    double money = resultSet.getDouble("money");

                    topPlayers.append(ChatColor.YELLOW).append(rank).append(". ")
                            .append(ChatColor.WHITE).append(playerName).append(": ")
                            .append(ChatColor.GREEN).append(money).append("\n");

                    rank++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayers.toString();
    }
}