package fr.louis.louis_292_project.listeners;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SheepRegrowWoolEvent;

import java.util.Random;

public class SheepListener implements Listener {

    @EventHandler (priority = EventPriority.NORMAL )
    public void onSheepRegainWool(SheepRegrowWoolEvent event) {

        final Sheep sheep = event.getEntity();
        final DyeColor generatedColor = randomizeColor();

        sheep.setColor(generatedColor);

        if (sheep.getCustomName() != null && sheep.getCustomName().equals("Louis_292")) {
            sheep.setCustomName("Jeb_");
            sheep.setMaxHealth(16);

        } else if (sheep.getColor().equals(DyeColor.WHITE)) {
            sheep.setCustomName("Blanc");
        } else if (sheep.getColor().equals(DyeColor.ORANGE)) {
            sheep.setCustomName("Orange");
        } else if (sheep.getColor().equals(DyeColor.ORANGE)) {
            sheep.setCustomName("Orange");
        } else if (sheep.getColor().equals(DyeColor.MAGENTA)) {
            sheep.setCustomName("Magenta");
        } else if (sheep.getColor().equals(DyeColor.LIGHT_BLUE)) {
            sheep.setCustomName("Bleu Clair");
        } else if (sheep.getColor().equals(DyeColor.YELLOW)) {
            sheep.setCustomName("Jaune");
        } else if (sheep.getColor().equals(DyeColor.LIME)) {
            sheep.setCustomName("Vert Clair");
        } else if (sheep.getColor().equals(DyeColor.PINK)) {
            sheep.setCustomName("Rose");
        } else if (sheep.getColor().equals(DyeColor.GRAY)) {
            sheep.setCustomName("Gris");
        } else if (sheep.getColor().equals(DyeColor.SILVER)) {
            sheep.setCustomName("Argent");
        } else if (sheep.getColor().equals(DyeColor.CYAN)) {
            sheep.setCustomName("Cyan");
        } else if (sheep.getColor().equals(DyeColor.PURPLE)) {
            sheep.setCustomName("Violet");
        } else if (sheep.getColor().equals(DyeColor.BLUE)) {
            sheep.setCustomName("Bleu");
        } else if (sheep.getColor().equals(DyeColor.BROWN)) {
            sheep.setCustomName("Marron");
        } else if (sheep.getColor().equals(DyeColor.GREEN)) {
            sheep.setCustomName("Vert");
        } else if (sheep.getColor().equals(DyeColor.RED)) {
            sheep.setCustomName("Rouge");
        } else if (sheep.getColor().equals(DyeColor.BLACK)) {
            sheep.setCustomName("Noir");
        }

        sheep.setHealth(8);

    }

    private DyeColor randomizeColor() {
        final Random random = new Random();
        final int color = random.nextInt(16);
        return DyeColor.values() [color];
    }

}
