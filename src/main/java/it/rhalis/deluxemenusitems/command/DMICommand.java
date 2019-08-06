package it.rhalis.deluxemenusitems.command;

import it.rhalis.deluxemenusitems.DeluxeMenusItems;
import it.rhalis.deluxemenusitems.item.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DMICommand implements CommandExecutor {

    private DeluxeMenusItems plugin;

    public DMICommand(DeluxeMenusItems p, String command) {
        this.plugin = p;
        p.getCommand(command).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("dmi.admin")) {
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        if (args.length == 1) {
                            long a = System.currentTimeMillis();
                            plugin.reloadConfig();
                            plugin.getItemManager().update();
                            long b = System.currentTimeMillis() - a;
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfiguration reloaded successfully in " + b + "ms."));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect usage: &7/" + label + " reload"));
                        break;
                    case "item":
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            if (args.length == 2) {
                                MenuItem item;
                                if ((item = this.plugin.getItemManager().getMenuItem(args[1])) != null) {
                                    p.getInventory().addItem(item.getItemStack());
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou received the menu item " + item.getIdentifier() + "."));
                                }
                            } else p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect usage: &7/" + label + " item <identifier>"));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis command can only be used by online players."));
                        break;
                    default:
                        help(sender);
                        break;
                }
            } else help(sender);
        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Messages.no-permission")));

        return true;
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" +
                "&8&lDeluxe&7Menus&f&lItems &3by RhalisX99\n" +
                "&7/dmi reload &8- &freload the configuration file\n" +
                "&7/dmi item <identifier> &8- &fget the selected menu item"));
    }
}