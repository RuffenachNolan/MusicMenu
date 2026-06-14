package fr.musicmenu;
package fr.tonplugin.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import java.util.*;

public class MusicCommand implements CommandExecutor {

    private final List<String> allowedPlayers = Arrays.asList("Anguile09", "DustMan00");

    private final List<String> categories = Arrays.asList(
            "Greenwood", "Roseville", "Northmen", "Vieilleville",
            "Mer", "Shurixor", "Konoshina", "Volcan",
            "Mine", "Epreuves Mel'Kor", "Mel'kor"
    );

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (!allowedPlayers.contains(player.getName())) {
            player.sendMessage("§cTu n'as pas accès à ce menu.");
            return true;
        }

        Inventory menu = Bukkit.createInventory(null, 27, "§8Musiques");

        int slot = 0;
        for (String cat : categories) {
            menu.setItem(slot++, createItem(Material.NOTE_BLOCK, "§a" + cat));
        }

        player.openInventory(menu);
        return true;
    }

    private ItemStack createItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
