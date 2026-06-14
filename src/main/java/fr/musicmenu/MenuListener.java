package fr.musicmenu;
package fr.tonplugin.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import java.util.*;

public class MenuListener implements Listener {

    // 🔥 CONFIG FACILE A MODIFIER
    private final Map<String, List<String>> musiques = new HashMap<>();

    public MenuListener() {

        // Greenwood
        musiques.put("Greenwood", Arrays.asList(
				"Greenwood_Orage",
                "Greenwood_Ambiance",
                "Greenwood_Boss",
                "Greenwood_Temple",
                "Greenwood_Epreuve"
        ));

        // Roseville
        musiques.put("Roseville", Arrays.asList(
                "Roseville_Ambiance",
                "Roseville_Boss",
                "Roseville_Temple",
                "Roseville_Epreuve"
        ));

        // Northmen
        musiques.put("Northmen", Arrays.asList(
                "Northmen_Ambiance",
                "Northmen_Boss",
                "Northmen_Temple",
                "Northmen_Epreuve"
        ));

        // Vieilleville
        musiques.put("Vieilleville", Arrays.asList(
                "Vieilleville_Ambiance",
                "Vieilleville_Boss",
                "Vieilleville_Temple",
                "Vieilleville_Epreuve"
        ));

        // Mer
        musiques.put("Mer", Arrays.asList(
                "Mer_Ambiance",
                "Mer_Boss",
                "Mer_Temple",
                "Mer_Epreuve"
        ));

        // Shurixor
        musiques.put("Shurixor", Arrays.asList(
                "Shurixor_Ambiance",
                "Shurixor_Boss",
                "Shurixor_Temple",
                "Shurixor_Epreuve"
        ));

        // Konoshina
        musiques.put("Konoshina", Arrays.asList(
                "Konoshina_Ambiance",
                "Konoshina_Boss",
                "Konoshina_Temple",
                "Konoshina_Epreuve"
        ));

        // Volcan
        musiques.put("Volcan", Arrays.asList(
                "Volcan_Ambiance",
                "Volcan_Boss",
                "Volcan_Temple",
                "Volcan_Epreuve"
        ));

        // Mine
        musiques.put("Mine", Arrays.asList(
                "Mine_Ambiance",
                "Mine_Boss",
                "Mine_Temple",
                "Mine_Epreuve"
        ));

        // 🔥 CAS SPECIAL : Epreuves Mel'Kor
        musiques.put("Epreuves Mel'Kor", Arrays.asList(
                "EpreuvesMelkor_Ambiance",
                "EpreuvesMelkor_Epreuve_1",
                "EpreuvesMelkor_Epreuve_2",
                "EpreuvesMelkor_Epreuve_3"
        ));

        // Mel'kor
        musiques.put("Mel'kor", Arrays.asList(
                "Melkor_Ambiance",
                "Melkor_Boss",
                "Melkor_Temple",
                "Melkor_Epreuve"
        ));
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();

        // MENU PRINCIPAL
        if (title.equals("§8Musiques")) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;

            String category = e.getCurrentItem().getItemMeta().getDisplayName().replace("§a", "");

            openCategoryMenu(player, category);
        }

        // SOUS-MENU
        else if (musiques.containsKey(title)) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;

            String musicName = e.getCurrentItem().getItemMeta().getDisplayName().replace("§b", "");

            player.playSound(player.getLocation(), "musique." + musicName, 1f, 1f);
            player.sendMessage("§aLecture : " + musicName);
        }
    }

    private void openCategoryMenu(Player player, String category) {

        List<String> list = musiques.get(category);
        if (list == null) return;

        Inventory menu = Bukkit.createInventory(null, 27, category);

        int slot = 10;

        for (String music : list) {
            menu.setItem(slot++, createItem(Material.MUSIC_DISC_CAT, "§b" + music));
        }

        player.openInventory(menu);
    }

    private ItemStack createItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
