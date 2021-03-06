package net.unraveled.listeners;

import net.unraveled.ConversePlugin;
import net.unraveled.api.abstracts.AbstractGUI;
import net.unraveled.util.ConverseBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ShopListener extends ConverseBase implements Listener {

    public ShopListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        UUID invUUID = AbstractGUI.openInventories.get(playerUUID);
        if (invUUID != null) {
            e.setCancelled(true);
            AbstractGUI gui = AbstractGUI.getInvByUUID().get(invUUID);
            AbstractGUI.GUIAction action = gui.getActions().get(e.getSlot());
            if (action != null) {
                action.click(player);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        AbstractGUI.openInventories.remove(playerUUID);
    }

}