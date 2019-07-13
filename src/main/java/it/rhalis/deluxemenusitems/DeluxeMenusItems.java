package it.rhalis.deluxemenusitems;

import it.rhalis.deluxemenusitems.command.DMICommand;
import it.rhalis.deluxemenusitems.item.ItemManager;
import it.rhalis.deluxemenusitems.listener.DMIListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeluxeMenusItems extends JavaPlugin {
    private static DeluxeMenusItems plugin;
    private ItemManager manager;

    public void onEnable() {
        plugin = this;

        try {
            Class.forName("com.extendedclip.deluxemenus.menu.Menu");
            this.saveDefaultConfig();
            this.manager = new ItemManager(this);
            this.manager.update();
            new DMIListener(this);
            new DMICommand(this, "deluxemenuitems");
        } catch (ClassNotFoundException var2) {
            this.getLogger().info("This plugin requires DeluxeMenus to work. Disabling...");
            this.getServer().getPluginManager().disablePlugin(this);
        }

    }

    public static DeluxeMenusItems getInstance() {
        return plugin;
    }

    public ItemManager getItemManager() {
        return this.manager;
    }
}
