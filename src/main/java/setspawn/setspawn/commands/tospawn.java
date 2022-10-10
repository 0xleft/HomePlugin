package setspawn.setspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import setspawn.setspawn.SpawnManager;
import java.util.Arrays;


public class tospawn implements CommandExecutor {
    SpawnManager plugin;

    public tospawn(SpawnManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        if (plugin.getPlayerSetSpawns(p.getUniqueId()) == null){
            p.sendMessage(ChatColor.RED+"You have not saved any spawns yet");
            return true;
        }

        if (args.length < 1) {
            p.sendMessage(ChatColor.RED + "Select spawn to teleport to!");
            return true;
        }


        String spawnname = Arrays.toString(args).toLowerCase();


        if (!(plugin.alreadyInPlayerSpawns(p.getUniqueId(), spawnname))){
            p.sendMessage(ChatColor.RED+"You don't have a spawn by this name.");
            return true;
        }

        Location playerreqloc = plugin.getLocationByName(p.getUniqueId(), spawnname);

        p.sendMessage(ChatColor.GREEN + "Teleporting!");
        p.teleport(playerreqloc);
        p.playSound(p, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1, 1);

        return true;
    }
}

//TODO no dupes