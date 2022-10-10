package setspawn.setspawn.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import setspawn.setspawn.SpawnManager;

import java.util.Arrays;

public class removespawn implements CommandExecutor {

    SpawnManager plugin;

    public removespawn(SpawnManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            return false;
        }

        Player p = (Player) sender;

        if (args.length < 1) {
            p.sendMessage(ChatColor.RED + "Select spawn to remove!");
            return true;
        }

        String spawnname = Arrays.toString(args).toLowerCase();

        if (!(plugin.alreadyInPlayerSpawns(p.getUniqueId(), spawnname))){
            p.sendMessage(ChatColor.RED+"You don't have a spawn saved by that name.");
            return true;
        }

        plugin.removeSpawnByName(p.getUniqueId(), spawnname);
        p.sendMessage(ChatColor.GREEN+"Removed spawn: "+spawnname);
        return true;
    }
}
