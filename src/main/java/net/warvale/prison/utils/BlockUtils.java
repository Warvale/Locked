package net.warvale.prison.utils;

import net.warvale.prison.Prison;
import org.bukkit.Location;
import org.bukkit.Material;

public class BlockUtils {

    public static void resetOres(){
        coal(1349, 3, 351);
        coal(1353, 3, 359);
        coal(1353, 5, 355);
        coal(1355, 6, 354);
        coal(1356, 3, 345);
        coal(1357, 3, 348);
        coal(1358, 5, 358);
        coal(1360, 3, 353);
        coal(1362, 3, 358);
        coal(1364, 3, 340);
        coal(1366, 4, 341);
        coal(1368, 3, 351);
        coal(1370, 4, 358);
        coal(1370, 2, 343);
        coal(1370, 6, 342);
        coal(1371, 1, 344);
        coal(1371, 6, 357);
        coal(1373, 4, 357);
        coal(1375, 7, 343);
        coal(1375, 3, 343);
        coal(1375, 1, 345);
        coal(1375, 5, 359);
        coal(1376, 2, 358);
        coal(1379, 1, 348);
        coal(1380, 6, 347);

        iron(1351, 5, 348);
        iron(1352, 5, 353);
        iron(1352, 4, 347);
        iron(1356, 5, 357);
        iron(1354, 3, 343);
        iron(1360, 3, 341);
        iron(1359, 4, 354);
        iron(1357, 5, 359);
        iron(1360, 4, 359);
        iron(1362, 3, 355);
        iron(1365, 5, 340);
        iron(1364, 2, 344);
        iron(1367, 4, 341);
        iron(1367, 5, 339);
        iron(1372, 4, 341);
        iron(1370, 3, 343);
        iron(1374, 2, 356);
        iron(1372, 5, 358);
        iron(1378, 5, 346);
        iron(1374, 3, 344);
        iron(1381, 4, 341);
        iron(1381, 5, 344);

        diamond(1377, 2, 343);

    }

    private static void coal(int x, int y, int z){
        new Location(Prison.getWorld(), x, y, z).getBlock().setType(Material.COAL_ORE);
    }

    private static void iron(int x, int y, int z){
        new Location(Prison.getWorld(), x, y, z).getBlock().setType(Material.IRON_ORE);
    }

    private static void diamond(int x, int y, int z){
        new Location(Prison.getWorld(), x, y, z).getBlock().setType(Material.DIAMOND_ORE);
    }
}
