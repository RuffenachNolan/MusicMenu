package fr.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class MenuListener implements Listener {

    private static final String MUSIC_PREFIX = "§b";
    private final Map<String, List<String>> musiques = new LinkedHashMap<>();

    public MenuListener() {
        musiques.put("Greenwood", List.of(
                "Greenwood_Ambiance",
                "Greenwood_Orage",
                "Greenwood_Loups",
                "Greenwood_Temple",
                "Greenwood_Boss",
                "Greenwood_Afterboss",
                "Greenwood_Foret",
                "Voyage_Mer"
        ));

        musiques.put("Roseville", List.of(
                "Roseville_Ambiance",
                "Roseville_Orage",
                "Roseville_Grotte",
                "Voyage_Mer"
        ));

        musiques.put("Northmen", List.of(
                "Northmen_Ambiance",
                "Northmen_Epreuve",
                "Northmen_Boss",
                "Voyage_Mer"
        ));

        musiques.put("Vieilleville", List.of(
                "Vieilleville_Ambiance",
                "Vieilleville_TP",
                "Voyage_Mer"
        ));

        musiques.put("Mer", List.of(
                "Mer_Ambiance",
                "Mer_Grotte",
                "Mer_Temple",
                "Mer_Boss",
                "Voyage_Mer"
        ));

        musiques.put("Shurixor", List.of(
                "Shurixor_Ambiance",
                "Shurixor_Temple",
                "Shurixor_Boss",
                "Voyage_Mer"
        ));

        musiques.put("Konoshina", List.of(
                "Konoshina_Ambiance",
                "Konoshina_Epreuve1",
                "Konoshina_Epreuve2",
                "Konoshina_Epreuve3",
                "Konoshina_Boss",
                "Voyage_Mer"
        ));

        musiques.put("Volcan", List.of(
                "Volcan_Before",
                "Volcan_Embuscade",
                "Volcan_Boss",
                "Voyage_Mer"
        ));

        musiques.put("Mine", List.of(
                "Mine_Ambiance",
                "Voyage_Mer"
        ));

        musiques.put("Epreuves Mel'Kor", List.of(
                "Melkor_Epreuve1",
                "Melkor_Epreuve2",
                "Melkor_Epreuve3",
                "Voyage_Mer"
        ));

        musiques.put("Mel'kor", List.of(
                "Melkor_Epreuve1",
                "Melkor_Epreuve2",
                "Melkor_Epreuve3",
                "Melkor_Grotte",
                "Melkor_Boss1",
                "Melkor_Temple",
                "Melkor_Boss2",
                "Melkor_Boss3",
                "Melkor_Before_Good_Ending",
                "Melkor_After_Good_Ending",
                "Melkor_Bad_Ending"
        ));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (MusicCommand.MAIN_MENU_TITLE.equals(title)) {
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player player)) return;

            ItemMeta meta = getMeta(event);
            if (meta == null || !meta.hasDisplayName()) return;

            String category = stripPrefix(meta.getDisplayName(), "§a");
            if (musiques.containsKey(category)) {
                openCategoryMenu(player, category);
            }
            return;
        }

        if (!musiques.containsKey(title)) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player)) return;

        ItemMeta meta = getMeta(event);
        if (meta == null || !meta.hasDisplayName()) return;

        String musicName = stripPrefix(meta.getDisplayName(), MUSIC_PREFIX);
        if (!musiques.get(title).contains(musicName)) return;

        String soundId = ("musique." + musicName).toLowerCase(Locale.ROOT);

        // Lance la musique pour TOUS les joueurs connectes.
        // RECORDS correspond au curseur "Blocs musicaux / Jukebox".
        for (Player target : Bukkit.getOnlinePlayers()) {
            // Evite toute superposition avec la piste precedente.
            target.stopSound(SoundCategory.RECORDS);

            target.playSound(
                    target.getLocation(),
                    soundId,
                    SoundCategory.RECORDS,
                    1.0f,
                    1.0f
            );
        }

        Bukkit.broadcastMessage("§aLecture : §f" + musicName);
    }

    private void openCategoryMenu(Player player, String category) {
        List<String> list = musiques.get(category);
        if (list == null || list.isEmpty()) return;

        int rows = Math.max(2, Math.min(6, (int) Math.ceil(list.size() / 9.0)));
        Inventory menu = Bukkit.createInventory(null, rows * 9, category);

        int slot = 0;
        for (String music : list) {
            menu.setItem(slot++, createItem(Material.MUSIC_DISC_CAT, MUSIC_PREFIX + music));
        }

        player.openInventory(menu);
    }

    private ItemMeta getMeta(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) return null;
        return item.getItemMeta();
    }

    private String stripPrefix(String text, String prefix) {
        return text.startsWith(prefix) ? text.substring(prefix.length()) : text;
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
