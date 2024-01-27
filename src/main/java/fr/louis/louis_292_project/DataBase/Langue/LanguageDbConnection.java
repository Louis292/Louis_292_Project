package fr.louis.louis_292_project.DataBase.Langue;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LanguageDbConnection {

    private LanguageDbCredentials languageDbCredentials;
    private Connection connection;
    private FileConfiguration config;

    public LanguageDbConnection(LanguageDbCredentials languageDbCredentials, FileConfiguration config) {
        this.languageDbCredentials = languageDbCredentials;
        this.config = config;
        this.connect();
    }

    private void connect() {
        try {
            Class.forName(config.getString("Language_DataBase_Tools.driver"));
            this.connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("Language_DataBase_Tools.ip") +  ":" + config.getInt("Language_DataBase_Tools.port") + "/" + config.getString("Language_DataBase_Tools.DataBase_Name"), this.languageDbCredentials.getUser(), this.languageDbCredentials.getPass());

            if (config.getBoolean("debug")) {
                System.out.println("Successfully connected to DB.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (this.connection != null) {
                if (!this.connection.isClosed()) {
                    this.connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) {
                return this.connection;
            }
        }
        connect();
        return this.connection;
    }
}
