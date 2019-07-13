package it.rhalis.deluxemenusitems.listener;

import it.rhalis.deluxemenusitems.item.MenuItem;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import com.extendedclip.deluxemenus.menu.Menu;
import org.bukkit.event.player.PlayerInteractEvent;
import it.rhalis.deluxemenusitems.DeluxeMenusItems;
import org.bukkit.event.Listener;

public class DMIListener implements Listener {

    private DeluxeMenusItems plugin;

    public DMIListener(final DeluxeMenusItems p) {
        this.plugin = p;
        p.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        Player p = e.getPlayer();
        MenuItem mi;
        if ((mi = this.plugin.getItemManager().getMenuItem(p.getItemInHand())) != null) {
            e.setCancelled(true);
            final Menu m = Menu.getMenu(mi.getMenu());
            if (m != null) {
                m.openMenu(p, p, null);
            }
            else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Messages.menu-not-found")));
            }
        }
    }
}
