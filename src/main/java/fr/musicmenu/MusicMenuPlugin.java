package fr.musicmenu;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public final class MusicMenuPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("musicmenu")).setExecutor(new MusicCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getLogger().info("MusicMenu 1.2.0 active !");
    }

    @Override
    public void onDisable() {
        getLogger().info("MusicMenu desactive.");
    }
}
