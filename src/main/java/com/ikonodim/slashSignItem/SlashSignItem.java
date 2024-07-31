package com.ikonodim.slashSignItem;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import com.ikonodim.slashSignItem.bStats.Metrics;
import com.ikonodim.slashSignItem.commands.*;
import com.ikonodim.slashSignItem.config.Config;


public final class SlashSignItem extends JavaPlugin {
    private static final int METRICS_ID = 0;

    @Override
    public void onEnable() {
        // Load configuration
        Config config = Config.getInstance();
        config.load();

        // Initialize metrics tracking
        new Metrics(this, METRICS_ID);

        // Register commands
        Objects.requireNonNull(getCommand("sign"), "Command 'sign' not found").setExecutor(new SignCommand());
        Objects.requireNonNull(getCommand("unsign"), "Command 'unsign' not found").setExecutor(new UnsignCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("SlashSignItem plugin has been disabled.");
    }

    /**
     * Disables the plugin.
     */
    public void disable() {
        getServer().getPluginManager().disablePlugin(this);
    }

    /**
     * Gets the instance of the plugin.
     *
     * @return the instance of SlashSignItem
     */
    public static SlashSignItem getInstance() {
        return getPlugin(SlashSignItem.class);
    }
}
