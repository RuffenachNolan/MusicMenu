package fr.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Set;

public final class MusicCommand implements CommandExecutor {

    public static final String MAIN_MENU_TITLE = "§8Musiques";

    private static final Set<String> ALLOWED_PLAYERS = Set.of(
            "Anguile09",
            "DustMan009"
    );

    private static final List<String> CATEGORIES = List.of(
            "Greenwood", "Roseville", "Northmen", "Vieilleville",
            "Mer", "Shurixor", "Konoshina", "Volcan",
            "Mine", "Epreuves Mel'Kor", "Mel'kor"
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande doit etre utilisee en jeu.");
            return true;
        }

        if (!ALLOWED_PLAYERS.contains(player.getName())) {
            player.sendMessage("§cTu n'as pas acces a ce menu.");
            return true;
        }

        Inventory menu = Bukkit.createInventory(null, 27, MAIN_MENU_TITLE);
        int slot = 0;
        for (String category : CATEGORIES) {
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
