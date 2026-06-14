package fr.musicmenu;
package fr.musicmenu;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MusicMenuPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // Commande principale
        getCommand("musicmenu").setExecutor(new MusicCommand());

        // Events (clics menu)
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);

        getLogger().info("MusicMenu activé ✔");
    }

    @Override
    public void onDisable() {
        getLogger().info("MusicMenu désactivé ✔");
    }
}
