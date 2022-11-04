package survivalplugin.com.goudenhelden.plugin.Com.GoudenHelden.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import survivalplugin.com.goudenhelden.plugin.Com.GoudenHelden.Main;

import java.util.Collection;

public class CommandCoords implements CommandExecutor {

    private Main plugin;

    public CommandCoords(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                //EIGEN LOC STUREN
                    Location playerCoords = player.getLocation();
                    int x = playerCoords.getBlockX();
                    int y = playerCoords.getBlockY();
                    int z = playerCoords.getBlockZ();
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Locatie: " + ChatColor.GOLD + player.getName());
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "X: " + ChatColor.GOLD + x);
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Y: " + ChatColor.GOLD + y);
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Z: " + ChatColor.GOLD + z);
                    Bukkit.broadcastMessage("");
                return false;
            }
            if (args.length == 1) {
                if (args[0].toLowerCase().equals("list")){
                    //list functionallity
                    Collection<String> positions = plugin.getConfig().getKeys(false);
                    player.sendMessage("");
                    for (String position : positions) {
                        try {
                            String name = position;

                            TextComponent clickable = new TextComponent("§6§l" + name);
                            clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/c get " + name));
                            clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click me!")));

                            TextComponent none = new TextComponent("§8- §eLocatie: ");
                            TextComponent world = new TextComponent("");
                            if (plugin.getConfig().getBoolean(name + ".Nether"))
                                world = new TextComponent(" §c(Nether)");
                            if (plugin.getConfig().getBoolean(name + ".TheEnd"))
                                world = new TextComponent(" §0(The End)");

                            player.sendMessage(new BaseComponent[]{none, clickable, world});
                        } catch (IllegalArgumentException ignored){
                            // fout

                        }
                    }
                    player.sendMessage("");
                    return false;
                }

                if (args[0].toLowerCase().equals("help")){
                    invalidArgs(sender, false);
                    return false;
                }





                invalidArgs(sender, true);
            }
            if (args.length == 2){
                if (args[0].toLowerCase().equals("add")){
                    String coordsName = args[1].toLowerCase();
                    if (plugin.getConfig().get(coordsName) != null) {
                        player.sendMessage(ChatColor.RED + "Coordinaten met deze naam bestaan al. /c list");
                        return false;
                    }
                    Location loc = player.getLocation();
                    plugin.getConfig().set(coordsName + ".X", loc.getBlockX());
                    plugin.getConfig().set(coordsName + ".Y", loc.getBlockY());
                    plugin.getConfig().set(coordsName + ".Z", loc.getBlockZ());
                    if (player.getWorld().getEnvironment().equals(World.Environment.NETHER))
                        plugin.getConfig().set(coordsName + ".Nether", true);
                    else
                        plugin.getConfig().set(coordsName + ".Nether", false);
                    if (player.getWorld().getEnvironment().equals(World.Environment.THE_END))
                        plugin.getConfig().set(coordsName + ".TheEnd", true);
                    else
                        plugin.getConfig().set(coordsName + ".TheEnd", false);
                    plugin.saveConfig();
                    player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Location added: " + ChatColor.GOLD + coordsName);
                    return false;
                }

                if (args[0].toLowerCase().equals("del")){
                    //list del
                     String coordsName = args[1].toLowerCase();
                     if (plugin.getConfig().contains(coordsName)) {
                         plugin.getConfig().set(coordsName, null);
                         plugin.saveConfig();
                         player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Location removed: " + ChatColor.GOLD + coordsName);
                         return false;
                     }
                     player.sendMessage(ChatColor.RED + "Coordinaten met deze naam bestaan niet. /c list");
                     return false;


                }

                if (args[0].toLowerCase().equals("get")) {
                    String name = args[1].toLowerCase();
                    int x = Integer.parseInt(plugin.getConfig().getString(name + ".X"));
                    int y = Integer.parseInt(plugin.getConfig().getString(name + ".Y"));
                    int z = Integer.parseInt(plugin.getConfig().getString(name + ".Z"));

                    player.sendMessage("");
                    player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Locatie: " + ChatColor.GOLD + name);
                    if (plugin.getConfig().getBoolean(name + ".Nether")){
                        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "World: " + ChatColor.GOLD + "Nether");
                    }
                    if (plugin.getConfig().getBoolean(name + ".TheEnd")){
                        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "World: " + ChatColor.GOLD + "The End");
                    }
                    player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "X: " + ChatColor.GOLD + x);
                    player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Y: " + ChatColor.GOLD + y);
                    player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Z: " + ChatColor.GOLD + z);
                    player.sendMessage("");
                }


            }
            if (args.length > 2){
                invalidArgs(sender, true);
            }
            return false;
        }

        return false;
    }





    public void invalidArgs(CommandSender sender, boolean wrongArgs){
        sender.sendMessage("");
        if (wrongArgs){
            sender.sendMessage(ChatColor.RED + "Verkeerd ingevoerd. Hieronder zijn de mogelijke opties.");
        }
        sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/coords " + ChatColor.GOLD + "Dit zet jouw huidige coords in de chat.");
        sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/coords list " + ChatColor.GOLD + "Dit laat alle opgeslagen coords zien.");
        sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/coords add [naam] " + ChatColor.GOLD + "Hiermee voeg je jouw huidige coords toe aan de list.");
        sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/coords del [naam] " + ChatColor.GOLD + "Hiermee verwijder je coords uit de lijst.");
        sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/coords get [naam] " + ChatColor.GOLD + "Dit laat de opgevraagde coordinaten zien.");
        sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/coords help " + ChatColor.GOLD + "Dit laat dit bericht zien.");
        sender.sendMessage("");
    }
}
