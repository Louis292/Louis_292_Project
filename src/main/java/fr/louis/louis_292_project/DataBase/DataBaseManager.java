package fr.louis.louis_292_project.DataBase;

import fr.louis.louis_292_project.DataBase.Economie.DbConnection;
import fr.louis.louis_292_project.DataBase.Economie.DbCredentials;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseManager {
    private DbConnection economieConnection;
    private FileConfiguration config;

    public DataBaseManager(FileConfiguration config) {
        this.config = config;
        this.connect(); // Connectez-vous à la base de données lors de l'initialisation du gestionnaire.
    }

    public void Close() {
        if (economieConnection != null) {
            economieConnection.close(); // Fermez la connexion à la base de données lors de la fermeture du gestionnaire.
        }
    }

    // Obtenez une connexion à la base de données
    public Connection getConnection() throws SQLException {
        // Si la connexion n'existe pas ou est fermée, reconnectez-vous.
        if (economieConnection == null || economieConnection.getConnection().isClosed()) {
            connect();
        }
        return economieConnection.getConnection();
    }

    private void connect() {
        try {
            // Vérifiez si la connexion est déjà établie pour éviter une nouvelle connexion.
            if (economieConnection == null || economieConnection.getConnection().isClosed()) {
                // Créez un objet DbCredentials et fournissez-le au constructeur de DbConnection.
                DbCredentials dbCredentials = new DbCredentials(config.getString("DataBase_Tools.ip"), config.getString("DataBase_Tools.user"), config.getString("DataBase_Tools.pass"), config.getString("DataBase_Tools.DataBase_Name"), config.getInt("DataBase_Tools.port"));
                this.economieConnection = new DbConnection(dbCredentials, config);
                System.out.println("Successfully connected to DB.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
