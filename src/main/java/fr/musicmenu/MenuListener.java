package fr.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListener implements Listener {

    private final Map<String, List<String>> musiques = new HashMap<>();

    public MenuListener() {
        musiques.put("Greenwood", Arrays.asList(
                "Greenwood_Orage",
                "Greenwood_Ambiance",
                "Greenwood_Boss",
                "Greenwood_Temple",
                "Greenwood_Epreuve"
        ));

        musiques.put("Roseville", Arrays.asList(
                "Roseville_Ambiance",
                "Roseville_Boss",
                "Roseville_Temple",
                "Roseville_Epreuve"
        ));

        musiques.put("Northmen", Arrays.asList(
                "Northmen_Ambiance",
                "Northmen_Boss",
                "Northmen_Temple",
                "Northmen_Epreuve"
        ));

        musiques.put("Vieilleville", Arrays.asList(
                "Vieilleville_Ambiance",
                "Vieilleville_Boss",
                "Vieilleville_Temple",
                "Vieilleville_Epreuve"
        ));

        musiques.put("Mer", Arrays.asList(
                "Mer_Ambiance",
                "Mer_Boss",
                "Mer_Temple",
                "Mer_Epreuve"
        ));

        musiques.put("Shurixor", Arrays.asList(
                "Shurixor_Ambiance",
                "Shurixor_Boss",
                "Shurixor_Temple",
                "Shurixor_Epreuve"
        ));

        musiques.put("Konoshina", Arrays.asList(
                "Konoshina_Ambiance",
                "Konoshina_Boss",
                "Konoshina_Temple",
                "Konoshina_Epreuve"
        ));

        musiques.put("Volcan", Arrays.asList(
                "Volcan_Ambiance",
                "Volcan_Boss",
                "Volcan_Temple",
                "Volcan_Epreuve"
        ));

        musiques.put("Mine", Arrays.asList(
                "Mine_Ambiance",
                "Mine_Boss",
                "Mine_Temple",
                "Mine_Epreuve"
        ));

        musiques.put("Epreuves Mel'Kor", Arrays.asList(
                "EpreuvesMelkor_Ambiance",
                "EpreuvesMelkor_Epreuve_1",
                "EpreuvesMelkor_Epreuve_2",
                "EpreuvesMelkor_Epreuve_3"
        ));

        musiques.put("Mel'kor", Arrays.asList(
                "Melkor_Ambiance",
                "Melkor_Boss",
                "Melkor_Temple",
                "Melkor_Epreuve"
        ));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        String title = event.getView().getTitle();

        if (title.equals("§8Musiques")) {
            event.setCancelled(true);

            if (!(event.getWhoClicked() instanceof Player)) return;
            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;

            ItemMeta meta = event.getCurrentItem().getItemMeta();
            if (meta == null || !meta.hasDisplayName()) return;

            Player player = (Player) event.getWhoClicked();
            String category = meta.getDisplayName().replace("§a", "");

            openCategoryMenu(player, category);
            return;
        }

        if (musiques.containsKey(title)) {
            event.setCancelled(true);

            if (!(event.getWhoClicked() instanceof Player)) return;
            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;

            ItemMeta meta = event.getCurrentItem().getItemMeta();
            if (meta == null || !meta.hasDisplayName()) return;

            Player player = (Player) event.getWhoClicked();
            String musicName = meta.getDisplayName().replace("§b", "");

            player.playSound(player.getLocation(), ("musique." + musicName).toLowerCase(), 1.0f, 1.0f);
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
