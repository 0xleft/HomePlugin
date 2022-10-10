//modification of https://www.spigotmc.org/wiki/save-load-data-files/

package setspawn.setspawn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;


public class Data implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    public final HashMap<UUID, HashMap<String, Location>> playerSpawns;

    // Can be used for saving
    public Data(HashMap<UUID, HashMap<String, Location>> playerSpawns) {
        this.playerSpawns = playerSpawns;
    }
    // Can be used for loading
    public Data(Data loadedData) {
        if (loadedData == null){
            this.playerSpawns = null;
            return;
        }
        this.playerSpawns = loadedData.playerSpawns;
    }

    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public static Data loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public static void savePlayerSpawns(HashMap<UUID, HashMap<String, Location>> playerSpawn) {
        new Data(playerSpawn).saveData("SpawnManager.data");
        Bukkit.getServer().getLogger().log(Level.INFO, "Data Saved");
    }


    public static HashMap<UUID, HashMap<String, Location>> getSavedPlayerSpawns() {
        // Load the data from disc using our loadData method.
        Data data = new Data(Data.loadData("SpawnManager.data"));
        Bukkit.getServer().getLogger().log(Level.INFO, "Data loaded");

        return data.playerSpawns;
    }
}