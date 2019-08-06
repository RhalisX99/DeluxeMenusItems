package it.rhalis.deluxemenusitems.item;

import it.rhalis.deluxemenusitems.DeluxeMenusItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
    private DeluxeMenusItems plugin;
    private List<MenuItem> items;

    public ItemManager(DeluxeMenusItems p) {
        this.plugin = p;
        this.items = new ArrayList<>();
    }

    public void update() {
        this.items.clear();
        for (String identifier : plugin.getConfig().getConfigurationSection("Items").getKeys(false)) {
            String confm = this.plugin.getConfig().getString("Items." + identifier + ".material");
            Material m;
            try {
                m = Material.valueOf(confm);
            } catch (IllegalArgumentException var15) {
                this.plugin.getLogger().info(confm + " is not a valid Material, skipping item menu " + identifier);
                continue;
            }

            short data = (short) this.plugin.getConfig().getInt("Items." + identifier + ".data", 0);
            ItemStack item = new ItemStack(m, 1, data);
            String name = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Items." + identifier + ".name", ""));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
            String menu = this.plugin.getConfig().getString("Items." + identifier + ".menu");
            List<Action> actions = new ArrayList<>();
            for (String action : this.plugin.getConfig().getStringList("Items." + identifier + ".actions")) {
                try {
                    actions.add(Action.valueOf(action));
                } catch (IllegalArgumentException var14) {
                    this.plugin.getLogger().info(action + " is not a valid Action (" + identifier + ")");
                }
            }
            addItem(identifier, item, menu, actions);
        }

    }

    private void addItem(String identifier, ItemStack item, String menu, List<Action> actions) {
        this.items.add(new MenuItem() {
            public String getIdentifier() {
                return identifier;
            }

            public ItemStack getItemStack() {
                return item.clone();
            }

            public String getMenu() {
                return menu;
            }

            public List<Action> getActions() {
                return new ArrayList<>(actions);
            }
        });
    }

    public MenuItem getMenuItem(String identifier) {
        for(MenuItem mi : items){
            if(mi.getIdentifier().equalsIgnoreCase(identifier)){
                return mi;
            }
        }
        return null;
    }

    public boolean isItemMenu(String identifier) {
        return getMenuItem(identifier) != null;
    }

    public MenuItem getMenuItem(ItemStack item) {
        for(MenuItem mi : items){
            if (mi.getItemStack().getType() == item.getType()) {
                String name1 = mi.getItemStack().hasItemMeta() ? mi.getItemStack().getItemMeta().getDisplayName() : null;
                String name2 = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : null;
                if (Objects.equals(name1, name2)) {
                    return mi;
                }
            }
        }
        return null;
    }

    public boolean isMenuItem(ItemStack item) {
        return getMenuItem(item) != null;
    }
}
