package it.rhalis.deluxemenusitems;

import it.rhalis.deluxemenusitems.command.DMICommand;
import it.rhalis.deluxemenusitems.item.ItemManager;
import it.rhalis.deluxemenusitems.listener.DMIListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DeluxeMenusItems extends JavaPlugin {

    private static DeluxeMenusItems plugin;
    private ItemManager manager;

    public void onEnable() {
        plugin = this;

        try {
            Class.forName("com.extendedclip.deluxemenus.menu.Menu");
            saveDefaultConfig();

            this.manager = new ItemManager(this);
            manager.update();

            new DMIListener(this);
            new DMICommand(this, "deluxemenusitems");
        } catch (ClassNotFoundException ex) {
            getLogger().info("This plugin requires DeluxeMenus to work. Disabling...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public static DeluxeMenusItems getInstance() {
        return plugin;
    }

    public ItemManager getItemManager() {
        return this.manager;
    }
}
