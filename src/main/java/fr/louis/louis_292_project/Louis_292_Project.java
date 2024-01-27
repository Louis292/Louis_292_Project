package fr.louis.louis_292_project;

import fr.louis.louis_292_project.Command.Home.DelHomeCommand;
import fr.louis.louis_292_project.Command.Home.HomeCommand;
import fr.louis.louis_292_project.Command.Home.SetHomeCommand;
import fr.louis.louis_292_project.Command.ItemCustomCommand;
import fr.louis.louis_292_project.Command.Louis_292_ProjectCommand;
import fr.louis.louis_292_project.Command.TeleporteCommand;
import fr.louis.louis_292_project.Command.ZombieSpawningCommand;
import fr.louis.louis_292_project.DataBase.DataBaseManager;
import fr.louis.louis_292_project.DataBase.Economie.Command.*;
import fr.louis.louis_292_project.DataBase.Economie.Listeners.OnPlayerJoinEconomie;
import fr.louis.louis_292_project.Schedulers.MessageManager;
import fr.louis.louis_292_project.listeners.PlayerInteractListener;
import fr.louis.louis_292_project.listeners.SheepListener;
import fr.louis.louis_292_project.listeners.Zombie.ZombieListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Louis_292_Project extends JavaPlugin {

    private static FileConfiguration config;
    private DataBaseManager dataBaseManager;


    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        this.config = getConfig();

        ConfigMessage();

        this.dataBaseManager = new DataBaseManager(config);

        if (config.getBoolean("DataBase")) {
            Bukkit.getPluginManager().registerEvents(new OnPlayerJoinEconomie(dataBaseManager), this);
            getCommand("money").setExecutor(new MoneyCommand(dataBaseManager));
            getCommand("baltop").setExecutor(new BaltopCommand(dataBaseManager));
            getCommand("addmoney").setExecutor(new AddMonayCommand(dataBaseManager));
            getCommand("removemoney").setExecutor(new RemoveMoneyCommand(dataBaseManager));
            getCommand("pay").setExecutor(new PayMoneyCommand(dataBaseManager));
        }

        Bukkit.getPluginManager().registerEvents(new SheepListener(), this);
        Bukkit.getPluginManager().registerEvents(new ZombieListener(), this);

        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

        if (config.getBoolean("debug")) {
            final  MessageManager messageManager = new MessageManager();
            final int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getServer().broadcastMessage(messageManager.getNextMessage()), 0L, 20L * 3);

            Bukkit.getScheduler().cancelTask(task);
        }

        getCommand("delhome").setExecutor(new DelHomeCommand(this));
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));

        getCommand("Rtp").setExecutor(new TeleporteCommand(this));

        getCommand("CustomItem").setExecutor(new ItemCustomCommand());

        getCommand("zombie").setExecutor(new ZombieSpawningCommand());

        getCommand("Louis_292_Project").setExecutor(new Louis_292_ProjectCommand(config));

    }

    @Override
    public void onDisable() {
        this.dataBaseManager.Close();
    }

    private void ConfigMessage() {
        System.out.println("==============================================================");
        System.out.println("| ");
        System.out.println("| Louis_292_Project enable V" + config.getString("version"));
        System.out.println("| ");
        System.out.println("| Mode debug " + config.getBoolean("debug"));
        System.out.println("| ");
        System.out.println("| DataBase " + config.getBoolean("DataBase"));

        if (config.getBoolean("DataBase")) {
            System.out.println("| ");
            System.out.println("| Driver : " + config.getString("DataBase_Tools.driver"));
            System.out.println("| User : " + config.getString("DataBase_Tools.user"));
            System.out.println("| pass : " + config.getString("DataBase_Tools.pass"));
            System.out.println("| Nom de la base de donné : " + config.getString("DataBase_Tools.DataBase_Name"));
            System.out.println("| Port : " + config.getInt("DataBase_Tools.port"));
        }
        System.out.println("| ");
        System.out.println("==============================================================");
    }
}
