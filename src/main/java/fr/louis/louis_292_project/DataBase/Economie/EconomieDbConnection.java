package fr.louis.louis_292_project.DataBase.Economie;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EconomieDbConnection {

    private EconomieDbCredentials economieDbCredentials;
    private Connection connection;
    private FileConfiguration config;

    public EconomieDbConnection(EconomieDbCredentials economieDbCredentials, FileConfiguration config) {
        this.economieDbCredentials = economieDbCredentials;
        this.config = config;
        this.connect();
    }

    private void connect() {
        try {
            Class.forName(config.getString("Economie_DataBase_Tools.driver"));
            this.connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("Economie_DataBase_Tools.ip") +  ":" + config.getInt("Economie_DataBase_Tools.port") + "/" + config.getString("Economie_DataBase_Tools.DataBase_Name"), this.economieDbCredentials.getUser(), this.economieDbCredentials.getPass());

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
