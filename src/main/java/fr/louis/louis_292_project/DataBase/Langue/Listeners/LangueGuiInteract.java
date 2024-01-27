package fr.louis.louis_292_project.DataBase.Langue.Listeners;

import fr.louis.louis_292_project.DataBase.DataBaseManager;
import fr.louis.louis_292_project.DataBase.Langue.Langue;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class LangueGuiInteract implements Listener {
    private final DataBaseManager dataBaseManager;
    private final Langue langueGetter;

    public LangueGuiInteract(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
        this.langueGetter = new Langue(dataBaseManager);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && clickedInventory.getName().equals(langueGetter.StringLanguePlayer(player, "Changer la langue", "Change the language"))) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.BANNER) {
                String bannerName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

                if (bannerName.equals("Anglais UK")) {
                    setPlayerLangue(player, "EN_uk");
                    player.sendMessage(ChatColor.GREEN + "Langue change with Anglais UK.");
                    player.closeInventory();
                } else if (bannerName.equals("Francais")) {
                    setPlayerLangue(player, "FR_fr");
                    player.sendMessage(ChatColor.GREEN + "Langue changée en Francais.");
                    player.closeInventory();
                }
            }
        }
    }

    private void setPlayerLangue(Player player, String langue) {
        UUID playerUUID = player.getUniqueId();

        // Update the player's language in the database
        langueGetter.setPlayerLangue(playerUUID, langue);
    }
}
