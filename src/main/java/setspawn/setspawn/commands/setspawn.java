package setspawn.setspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import setspawn.setspawn.SpawnManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class setspawn implements CommandExecutor {
    SpawnManager plugin;

    public setspawn(SpawnManager plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0){
        p.sendMessage(ChatColor.RED+"Invalid arguments");
            return true;
        }

        String spawnname = Arrays.toString(args).toLowerCase();

        Location spawnloc = p.getLocation();


        if (plugin.alreadyInPlayerSpawns(p.getUniqueId(), spawnname)){
            p.sendMessage(ChatColor.RED+"You already have a spawn by that name!");
            return true;
        }

        if (plugin.getPlayerSetSpawns(p.getUniqueId()) != null){
            if (plugin.getPlayerSetSpawns(p.getUniqueId()).keySet().size() >= plugin.allowedspawncount) {
                p.sendMessage(ChatColor.RED + "You can only have "+ plugin.allowedspawncount +" spawns");
                return true;
            }
        }

        p.sendMessage(ChatColor.GREEN+"Set spawn at"+p.getLocation().toVector().serialize());
        plugin.addPlayerSpawn(p.getUniqueId(), spawnname, spawnloc);
        return true;
    }
}
