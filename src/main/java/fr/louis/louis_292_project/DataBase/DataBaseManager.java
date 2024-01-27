package fr.louis.louis_292_project.DataBase;

import fr.louis.louis_292_project.DataBase.Economie.EconomieDbConnection;
import fr.louis.louis_292_project.DataBase.Economie.EconomieDbCredentials;
import fr.louis.louis_292_project.DataBase.Langue.LanguageDbConnection;
import fr.louis.louis_292_project.DataBase.Langue.LanguageDbCredentials;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseManager {
    private EconomieDbConnection economyConnection;
    private LanguageDbConnection languageConnection;
    private FileConfiguration config;

    public DataBaseManager(FileConfiguration config) {
        this.config = config;
        this.connect(); // Connect to databases during manager initialization.
    }

    public void close() {
        if (economyConnection != null) {
            economyConnection.close(); // Close economy database connection when closing the manager.
        }

        if (languageConnection != null) {
            languageConnection.close(); // Close language database connection when closing the manager.
        }
    }

    // Get a connection to the economy database
    public Connection getEconomyConnection() throws SQLException {
        // If the connection doesn't exist or is closed, reconnect.
        if (economyConnection == null || economyConnection.getConnection().isClosed()) {
            connect();
        }
        return economyConnection.getConnection();
    }

    // Get a connection to the language database
    public Connection getLanguageConnection() throws SQLException {
        // If the connection doesn't exist or is closed, reconnect.
        if (languageConnection == null || languageConnection.getConnection().isClosed()) {
            connect();
        }
        return languageConnection.getConnection();
    }

    private void connect() {
        try {
            // Check if economy connection is already established to avoid reconnection.
            if (economyConnection == null || economyConnection.getConnection().isClosed()) {
                // Create an EconomieDbCredentials object and provide it to the EconomieDbConnection constructor.
                EconomieDbCredentials economyCredentials = new EconomieDbCredentials(config.getString("Economie_DataBase_Tools.ip"), config.getString("Economie_DataBase_Tools.user"), config.getString("Economie_DataBase_Tools.pass"), config.getString("Economie_DataBase_Tools.DataBase_Name"), config.getInt("Economie_DataBase_Tools.port"));
                this.economyConnection = new EconomieDbConnection(economyCredentials, config);
                System.out.println("Successfully connected to economy DB.");
            }

            // Check if language connection is already established to avoid reconnection.
            if (languageConnection == null || languageConnection.getConnection().isClosed()) {
                // Create a LanguageDbCredentials object and provide it to the LanguageDbConnection constructor.
                LanguageDbCredentials languageCredentials = new LanguageDbCredentials(config.getString("Langue_DataBase_Tools.ip"), config.getString("Langue_DataBase_Tools.user"), config.getString("Langue_DataBase_Tools.pass"), config.getString("Langue_DataBase_Tools.DataBase_Name"), config.getInt("Langue_DataBase_Tools.port"));
                this.languageConnection = new LanguageDbConnection(languageCredentials, config);
                System.out.println("Successfully connected to language DB.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}