package ch.skilldev.motdmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class MOTDManager extends JavaPlugin implements Listener {
    public static String prefix = "§7[§eMOTD$7] ";


    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, (Plugin) this);
        System.out.println("[MOTDManager] The plugin was loaded successfully!");
    }

    @EventHandler
    public void onMotd(ServerListPingEvent e) {
        String MOTD = getConfig().getString("Config.MOTD");
        String MOTD2 = getConfig().getString("Config.MOTD2");
        e.setMotd(ChatColor.translateAlternateColorCodes('&', String.valueOf(MOTD) + "\n" + MOTD2));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (p.hasPermission("MOTD.*") &&
                label.equalsIgnoreCase("motd")) {
            if (args.length == 0) {
                p.sendMessage("§3========== §7| §eMOTD Help §7| §3==========");
                p.sendMessage("- §6You can set color codes with the &!");
                p.sendMessage("$7/motd 1 <text> | §7 first row of the MOTD!");
                p.sendMessage("$7/motd 2 <text> | §7 the second row of the MOTD!!");
            }
            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("1")) {
                    String motd = "";
                    for (int i = 1; i < args.length; i++) {
                        motd = String.valueOf(motd) + " " + args[i];
                    }
                    getConfig().set("Config.MOTD", motd);
                    saveConfig();
                    p.sendMessage(String.valueOf(prefix) + "§2The 1. MOTD was successfully changed");
                }
                else if (args.length >= 2 &&
                        args[0].equalsIgnoreCase("2")) {
                    String motd = "";
                    for (int i = 1; i < args.length; i++) {
                        motd = String.valueOf(motd) + " " + args[i];
                    }
                    getConfig().set("Config.MOTD2", motd);
                    saveConfig();
                    p.sendMessage(String.valueOf(prefix) + "§2The 2. MOTD was successfully changed");
                }
            }
        }


        return false;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
