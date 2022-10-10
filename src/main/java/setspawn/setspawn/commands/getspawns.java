package setspawn.setspawn.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import setspawn.setspawn.SpawnManager;

import java.util.Arrays;
import java.util.Set;

public class getspawns implements CommandExecutor {

    SpawnManager plugin;

    public getspawns(SpawnManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            return false;
        }

        Player p = (Player) sender;


        if (plugin.getPlayerSetSpawns(p.getUniqueId()).keySet().size() == 0){
            p.sendMessage(ChatColor.GREEN+"You don't have any spawns set yet!");
            return true;
        }


        Set spawnnames = plugin.getSetSpawns().get(p.getUniqueId()).keySet();

        StringBuilder spawns = new StringBuilder();

        for (Object spawnname: spawnnames) {
            spawns.append((String) spawnname).append(" ");
        }

        p.sendMessage(ChatColor.BLUE+"Your current spawns are: "+spawns);

        return true;
    }
}
