package it.rhalis.deluxemenusitems.item;

import org.bukkit.event.block.Action;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public interface MenuItem {

    String getIdentifier();
    ItemStack getItemStack();
    String getMenu();
    List<Action> getActions();
}