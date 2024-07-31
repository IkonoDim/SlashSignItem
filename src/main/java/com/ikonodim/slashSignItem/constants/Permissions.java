package com.ikonodim.slashSignItem.constants;

import org.bukkit.permissions.Permission;

/**
 * Defines permissions used by the SlashSignItem plugin.
 */
public final class Permissions {

    /**
     * Permission to ignore the blacklist.
     */
    public static final Permission IGNORE_BLACKLIST;

    /**
     * Permission to ignore the maximum stack size limit.
     */
    public static final Permission IGNORE_MAX_STACK_SIZE;

    /**
     * Permission to overwrite existing signatures.
     */
    public static final Permission OVERWRITE_SIGNATURE;

    static {
        IGNORE_BLACKLIST = new Permission("SlashSignItem.ignore_blacklist");
        IGNORE_MAX_STACK_SIZE = new Permission("SlashSignItem.ignore_max-stack-size");
        OVERWRITE_SIGNATURE = new Permission("SlashSignItem.overwrite_signature");
    }

    // Private constructor to prevent instantiation
    private Permissions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
