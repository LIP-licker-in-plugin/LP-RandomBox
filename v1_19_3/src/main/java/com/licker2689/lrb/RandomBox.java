package com.licker2689.lrb;

import com.licker2689.lpc.utils.ColorUtils;
import com.licker2689.lpc.utils.ConfigUtils;
import com.licker2689.lrb.commands.LRBCommand;
import com.licker2689.lrb.events.LRBEvent;
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
        plugin.getServer().getPluginManager().registerEvents(new LRBEvent(), plugin);
        getCommand("랜덤박스").setExecutor(new LRBCommand());
    }

    @Override
    public void onDisable() {
    }
}
