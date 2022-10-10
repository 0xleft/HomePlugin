package setspawn.setspawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import setspawn.setspawn.commands.getspawns;
import setspawn.setspawn.commands.removespawn;
import setspawn.setspawn.commands.setspawn;
import setspawn.setspawn.commands.tospawn;
import setspawn.setspawn.listeners.OnPlayerJoinListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import java.io.File;
import java.io.IOException;

public final class SpawnManager extends JavaPlugin {

    private HashMap<UUID, HashMap<String, Location>> playersetspawnsList;
    FileConfiguration config = getConfig();
    public int allowedspawncount;
    private final int saveTimer = 120;



    @Override
    public void onEnable() {
        createDataFile();

        config.addDefault("allowedspawncount", 3);
        config.options().copyDefaults(true);
        saveConfig();
        allowedspawncount  = config.getInt("allowedspawncount");
        // Plugin startup logic
        new Data(playersetspawnsList);
        this.playersetspawnsList = Data.getSavedPlayerSpawns();

        if (playersetspawnsList == null){
            this.playersetspawnsList = new HashMap<>();
            Bukkit.getLogger().warning("Failed to load SpawnManager plugin data");
        }

        getCommand("getspawns").setExecutor(new getspawns(this));
        getCommand("setspawn").setExecutor(new setspawn(this));
        getCommand("tospawn").setExecutor(new tospawn(this));
        getCommand("removespawn").setExecutor(new removespawn(this));
        getServer().getPluginManager().registerEvents(new OnPlayerJoinListener(this), this);
    }


    @Override
    public void onDisable() {
        Data.savePlayerSpawns(playersetspawnsList);
    }

    private void createDataFile() {
        try {
            File file = new File("SpawnManager.data");
            if (file.createNewFile()) {
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Location> getPlayerSetSpawns(UUID p) {
        if (playersetspawnsList.containsKey(p)) {
            return playersetspawnsList.get(p);
        }
        return new HashMap<>();
    }

    public void addPlayerSpawn(UUID p, String spawnname, Location l) {
        playersetspawnsList.get(p).put(spawnname, l);
    }

    public boolean alreadyInPlayerSpawns(UUID p, String spawnname){
        return playersetspawnsList.get(p).containsKey(spawnname);
    }

    public Location getLocationByName(UUID p, String spawnname){
        try{
            return playersetspawnsList.get(p).get(spawnname);
        }catch (Exception e){
            return null;
        }
    }

    public void addNewPlayerToSpawnList(UUID p){
        playersetspawnsList.put(p, new HashMap<>());
    }

    public HashMap<UUID, HashMap<String, Location>> getSetSpawns(){
        return this.playersetspawnsList;
    }

    public void removeSpawnByName(UUID p, String spawnname){
        playersetspawnsList.get(p).remove(spawnname);
    }
}
