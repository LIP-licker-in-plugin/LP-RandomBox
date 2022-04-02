package com.darksoldier1404.drb;

import com.darksoldier1404.dppc.utils.ColorUtils;
import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.drb.commands.DRBCommand;
import com.darksoldier1404.drb.events.DRBEvent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("all")
public class RandomBox extends JavaPlugin {
    private static RandomBox plugin;
    public static YamlConfiguration config;
    public static String prefix;

    public static RandomBox getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        config = ConfigUtils.loadDefaultPluginConfig(plugin);
        prefix = ColorUtils.applyColor(config.getString("Settings.prefix"));
        plugin.getServer().getPluginManager().registerEvents(new DRBEvent(), plugin);
        getCommand("랜덤박스").setExecutor(new DRBCommand());
    }

    @Override
    public void onDisable() {
    }
}
