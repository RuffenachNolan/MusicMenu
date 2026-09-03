package fr.musicmenu;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MusicMenuPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        MusicCommand musicCommand = new MusicCommand();

        Objects.requireNonNull(
                getCommand("musicmenu"),
                "La commande musicmenu est absente de plugin.yml"
        ).setExecutor(musicCommand);

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getLogger().info("MusicMenu 1.1.0 active.");
    }

    @Override
    public void onDisable() {
        getLogger().info("MusicMenu desactive.");
    }
}
