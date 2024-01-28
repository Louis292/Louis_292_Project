package fr.louis.louis_292_project.listeners;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import fr.louis.louis_292_project.DataBase.Economie.Economie;
import fr.louis.louis_292_project.DataBase.Langue.Langue;
import fr.louis.louis_292_project.Louis_292_Project;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class OnPlayerJoinAddScoreBoard implements Listener {

    private Langue langue;
    private Economie economie;
    private DataBaseManager dataBaseManager;
    private Louis_292_Project plugin;

    public OnPlayerJoinAddScoreBoard(Langue langue, Economie economie, DataBaseManager dataBaseManager, Louis_292_Project plugin) {
        this.langue = langue;
        this.economie = economie;
        this.dataBaseManager = dataBaseManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {
                // Vérifier si les objets sont null
                if (langue == null || economie == null || dataBaseManager == null || plugin == null) {
                    // Gérer le cas où l'un des objets est null
                    return;
                }

                // Créer un gestionnaire de tableaux de scores
                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

                // Créer un objectif (scoreboard objective)
                Objective objective = scoreboard.registerNewObjective("ExScoreBoard", "dummy");

                objective.setDisplayName(ChatColor.BLUE + "Louis_292_Project");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                String score0Text = "Langue :" + langue.StringLanguePlayer(player, "Français", "English");
                Score score0 = objective.getScore(score0Text);
                score0.setScore(0);

                String score1Text = "Langue :" + economie.getMoney(player);
                Score score1 = objective.getScore(score1Text);
                score1.setScore(1);

                player.setScoreboard(scoreboard);
            }
        }.runTaskTimer(plugin, 0L, 20L); // Exécutez la tâche toutes les 1 seconde (20 ticks)
    }
}
