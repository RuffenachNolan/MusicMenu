package fr.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class MenuListener implements Listener {

    private static final String MUSIC_PREFIX = "§b";

    /*
     * LinkedHashMap conserve l'ordre de declaration des categories.
     * L'ordre des musiques dans chaque List est l'ordre affiche dans le menu.
     */
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
                "Melkor_Boss3"
        ));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (MusicCommand.MAIN_MENU_TITLE.equals(title)) {
            event.setCancelled(true);

            if (!(event.getWhoClicked() instanceof Player player)) {
                return;
            }

            ItemMeta meta = getClickedItemMeta(event);
            if (meta == null || !meta.hasDisplayName()) {
                return;
            }

            String category = stripColorPrefix(meta.getDisplayName(), "§a");

            if (musiques.containsKey(category)) {
                openCategoryMenu(player, category);
            }
            return;
        }

        if (!musiques.containsKey(title)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemMeta meta = getClickedItemMeta(event);
        if (meta == null || !meta.hasDisplayName()) {
            return;
        }

        String musicName = stripColorPrefix(meta.getDisplayName(), MUSIC_PREFIX);

        if (!musiques.get(title).contains(musicName)) {
            return;
        }

        /*
         * Les identifiants Minecraft doivent etre en minuscules.
         * Exemple :
         * Volcan_Boss -> musique.volcan_boss
         */
        String soundId = ("musique." + musicName).toLowerCase(Locale.ROOT);

        /*
         * SoundCategory.MUSIC fait dependre ces pistes du curseur "Musique"
         * du client Minecraft, en plus du volume general.
         */
        player.playSound(
                player.getLocation(),
                soundId,
                SoundCategory.MUSIC,
                1.0f,
                1.0f
        );

        player.sendMessage("§aLecture : §f" + musicName);
    }

    private void openCategoryMenu(Player player, String category) {
        List<String> list = musiques.get(category);

        if (list == null || list.isEmpty()) {
            return;
        }

        int rows = Math.max(2, Math.min(6, (int) Math.ceil(list.size() / 9.0)));
        int size = rows * 9;

        Inventory menu = Bukkit.createInventory(null, size, category);

        int slot = 0;
        for (String music : list) {
            menu.setItem(
                    slot++,
                    createItem(Material.MUSIC_DISC_CAT, MUSIC_PREFIX + music)
            );
        }

        player.openInventory(menu);
    }

    private ItemMeta getClickedItemMeta(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType().isAir() || !clicked.hasItemMeta()) {
            return null;
        }

        return clicked.getItemMeta();
    }

    private String stripColorPrefix(String value, String prefix) {
        return value.startsWith(prefix) ? value.substring(prefix.length()) : value;
    }

    private ItemStack createItem(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(displayName);
            item.setItemMeta(meta);
        }

        return item;
    }
}
