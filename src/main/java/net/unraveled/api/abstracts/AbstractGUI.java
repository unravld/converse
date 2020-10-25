package net.unraveled.api.abstracts;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class AbstractGUI implements InventoryHolder, Listener {
    private final Inventory INV;
    private final Map<Integer, GUIAction> actions;
    private final UUID uuid;
    private final List<Integer> validNumbers = new ArrayList<>(Arrays.asList(9, 18, 27, 36, 45, 54));
    //
    public static final Map<UUID, AbstractGUI> invByUUID = new HashMap<>();
    public static final Map<UUID, UUID> openInventories = new HashMap<>();

    public AbstractGUI(int invSize, String invName) {
        uuid = UUID.randomUUID();
        if (!validNumbers.contains(invSize)) {
            throw new NumberFormatException("Number must be a multiple of nine!");
        }
        INV = Bukkit.createInventory(null, invSize, invName);
        actions = new HashMap<>();
        invByUUID.put(getUUId(), this);
    }

    public UUID getUUId() {
        return uuid;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return INV;
    }

    public interface GUIAction {
        void click(Player player);
    }

    public final void setItem(int slot, ItemStack stack, GUIAction action) {
        INV.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public final void setItem(int slot, ItemStack stack) {
        setItem(slot, stack, null);
    }

    public final void open(@NotNull Player p) {
        p.openInventory(INV);
        openInventories.put(p.getUniqueId(), getUUId());
    }

    public final void delete() {
        Bukkit.getOnlinePlayers().forEach((p) ->
        {
            UUID u = openInventories.get(p.getUniqueId());
            if (u.equals(getUUId())) {
                p.closeInventory();
            }
        });
        invByUUID.remove(getUUId());
    }

    public static Map<UUID, AbstractGUI> getInvByUUID() {
        return invByUUID;
    }

    public static Map<UUID, UUID> getOpenInvs() {
        return openInventories;
    }

    public Map<Integer, GUIAction> getActions() {
        return actions;
    }

    @NotNull
    public final ItemStack newItem(Material mat, @NotNull String name, String... lore) {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<>(Arrays.asList(lore));
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    @NotNull
    public final ItemStack newPlayerHead(@NotNull Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta sku = (SkullMeta) item.getItemMeta();
        assert sku != null;
        sku.setDisplayName(p.getName());
        ArrayList<String> loreComments = new ArrayList<>();
        loreComments.add("Click for user specific info");
        sku.setLore(loreComments);
        sku.setOwningPlayer(p);
        item.setItemMeta(sku);
        return item;
    }
}
