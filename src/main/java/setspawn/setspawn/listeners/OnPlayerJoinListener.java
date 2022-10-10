package setspawn.setspawn.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import setspawn.setspawn.SpawnManager;

public class OnPlayerJoinListener implements Listener {

    SpawnManager plugin;
    public OnPlayerJoinListener(SpawnManager plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (plugin.getSetSpawns().containsKey(e.getPlayer().getUniqueId())){
            return;
        }

        Bukkit.getLogger().info("Player with no set spawns has joined.");
        plugin.addNewPlayerToSpawnList(p.getUniqueId());
    }
}
