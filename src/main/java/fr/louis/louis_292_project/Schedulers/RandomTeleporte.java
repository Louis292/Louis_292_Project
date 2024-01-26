package fr.louis.louis_292_project.Schedulers;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTeleporte {

    private RandomTeleporte() {}

    public static Location generationLocation(World world) {
        double x = ThreadLocalRandom.current().nextDouble(0, 1000);
        double z = ThreadLocalRandom.current().nextDouble(0, 1000);
        double y = world.getHighestBlockYAt((int) x, (int) z);

        return new Location(world, x, y, z);
    }

}
