package com.ikonodim.slashSignItem.constants;

import org.bukkit.NamespacedKey;

import com.ikonodim.slashSignItem.SlashSignItem;

/**
 * Defines custom NamespacedKeys used by the SlashSignItem plugin.
 */
public final class Keys {

    /**
     * Key for storing item signed status.
     */
    public static final NamespacedKey SIGN_KEY;

    /**
     * Key for storing signature content.
     */
    public static final NamespacedKey SIGN_CONTENT;

    static {
        SlashSignItem pluginInstance = SlashSignItem.getInstance();
        SIGN_KEY = new NamespacedKey(pluginInstance, "ItemSigned");
        SIGN_CONTENT = new NamespacedKey(pluginInstance, "Signature");
    }

    // Private constructor to prevent instantiation
    private Keys() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
