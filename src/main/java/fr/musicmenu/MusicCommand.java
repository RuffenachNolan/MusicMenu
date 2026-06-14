package fr.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class MusicCommand implements CommandExecutor {

    private final List<String> allowedPlayers = Arrays.asList("Anguile09", "DustMan009");

    private final List<String> categories = Arrays.asList(
            "Greenwood", "Roseville", "Northmen", "Vieilleville",
            "Mer", "Shurixor", "Konoshina", "Volcan",
            "Mine", "Epreuves Mel'Kor", "Mel'kor"
    );

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!allowedPlayers.contains(player.getName())) {
            player.sendMessage("§cTu n'as pas accès à ce menu.");
            return true;
        }

        Inventory menu = Bukkit.createInventory(null, 27, "§8Musiques");

        int slot = 0;
        for (String category : categories) {
            menu.setItem(slot++, createItem(Material.NOTE_BLOCK, "§a" + category));
        }

        player.openInventory(menu);
        return true;
    }

    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }

        return item;
    }
}
