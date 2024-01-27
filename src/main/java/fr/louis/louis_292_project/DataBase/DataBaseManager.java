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
        this.connect(); // Connecter aux bases de données lors de l'initialisation du gestionnaire.
    }

    public void close() {
        if (economyConnection != null) {
            economyConnection.close(); // Fermer la connexion à la base de données économique lors de la fermeture du gestionnaire.
        }

        if (languageConnection != null) {
            languageConnection.close(); // Fermer la connexion à la base de données de langue lors de la fermeture du gestionnaire.
        }
    }

    // Obtenir une connexion à la base de données économique
    public Connection getEconomyConnection() throws SQLException {
        // Si la connexion n'existe pas ou est fermée, reconnectez-vous.
        if (economyConnection == null || economyConnection.getConnection().isClosed()) {
            connect();
        }
        return economyConnection.getConnection();
    }

    // Obtenir une connexion à la base de données de langue
    public Connection getLanguageConnection() throws SQLException {
        // Si la connexion n'existe pas ou est fermée, reconnectez-vous.
        if (languageConnection == null || languageConnection.getConnection().isClosed()) {
            connect();
        }
        return languageConnection.getConnection();
    }

    private void connect() {
        // Vérifier si la connexion économique est déjà établie pour éviter une nouvelle connexion.
        if (config.getBoolean("Economie_DataBase")) {
            EconomieDbCredentials economyCredentials = new EconomieDbCredentials(
                    config.getString("Economie_DataBase_Tools.ip"),
                    config.getString("Economie_DataBase_Tools.user"),
                    config.getString("Economie_DataBase_Tools.pass"),
                    config.getString("Economie_DataBase_Tools.DataBase_Name"),
                    config.getInt("Economie_DataBase_Tools.port")
            );
            this.economyConnection = new EconomieDbConnection(economyCredentials, config);
            System.out.println("Successfully connected to economy DB.");
        }

        // Vérifier si la connexion linguistique est déjà établie pour éviter une nouvelle connexion.
        if (config.getBoolean("Langue_DataBase")) {
            LanguageDbCredentials languageCredentials = new LanguageDbCredentials(
                    config.getString("Language_DataBase_Tools.ip"),
                    config.getString("Language_DataBase_Tools.user"),
                    config.getString("Language_DataBase_Tools.pass"),
                    config.getString("Language_DataBase_Tools.DataBase_Name"),
                    config.getInt("Language_DataBase_Tools.port")
            );
            this.languageConnection = new LanguageDbConnection(languageCredentials, config);
            System.out.println("Successfully connected to language DB.");
        }
    }
}