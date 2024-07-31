package com.ikonodim.slashSignItem.config;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ikonodim.slashSignItem.SlashSignItem;
import com.ikonodim.slashSignItem.utils.TextUtility;

public class Config {

    private static final Config INSTANCE = new Config();
    private static final SlashSignItem PLUGIN_INSTANCE = SlashSignItem.getInstance();

    // Config Keys
    private static final String SIGNATURE_TEMPLATE_KEY = "signature-template";
    private static final String ITEM_NAME_TEMPLATE_KEY = "item-name-template";
    private static final String OPTIONS_ITEM_BLACKLIST_KEY = "Options.item-blacklist";
    private static final String OPTIONS_MAX_STACK_SIZE_KEY = "Options.max-stack-size";
    private static final String OPTIONS_ENCHANTMENT_GLINT_KEY = "Options.enchantment-glint";
    private static final String OPTIONS_OVERRIDE_SIGNATURE_KEY = "Options.override-signature";
    private static final String MESSAGES_PREFIX_KEY = "Messages.prefix";
    private static final String MESSAGES_ITEM_NO_SIGNATURE_KEY = "Messages.item-no-signature";
    private static final String MESSAGES_ITEM_HAS_SIGNATURE_KEY = "Messages.item-has-signature";
    private static final String MESSAGES_NOT_HOLDING_ITEM_KEY = "Messages.not-holding-item";
    private static final String MESSAGES_CANT_SIGN_ITEM_KEY = "Messages.cant-sign-item";
    private static final String MESSAGES_TOO_MANY_ITEMS_KEY = "Messages.too-many-items";
    private static final String FORMATS_DATE_KEY = "Formats.date";
    private static final String FORMATS_TIME_KEY = "Formats.time";

    private String signatureTemplate;
    private String itemNameTemplate;

    public static class Options {
        public List<String> itemBlacklist;
        public int maxStackSize;
        public boolean enchantmentGlint;
        public boolean overrideSignature;
    }
    public final Options options = new Options();

    public static class Messages {
        public String prefix;
        public Component itemNoSignature;
        public Component itemHasSignature;
        public Component notHoldingItem;
        public Component cantSignItem;
        public Component tooManyItems;
    }
    public final Messages messages = new Messages();

    public static class Formats {
        public String date;
        public String time;
    }
    public final Formats formats = new Formats();

    private Config() {}

    public void load() {
        File file = new File(PLUGIN_INSTANCE.getDataFolder(), "config.yml");

        if (!file.exists()) {
            PLUGIN_INSTANCE.saveResource("config.yml", false);
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            PLUGIN_INSTANCE.getLogger().severe("Failed to load config.yml: " + e.getMessage());
            PLUGIN_INSTANCE.disable();
            return;
        }

        signatureTemplate = config.getString(SIGNATURE_TEMPLATE_KEY, "");
        itemNameTemplate = config.getString(ITEM_NAME_TEMPLATE_KEY, "");

        options.itemBlacklist = config.getStringList(OPTIONS_ITEM_BLACKLIST_KEY);
        options.maxStackSize = config.getInt(OPTIONS_MAX_STACK_SIZE_KEY, 64); // Default to max stack size of 64
        options.enchantmentGlint = config.getBoolean(OPTIONS_ENCHANTMENT_GLINT_KEY, true);
        options.overrideSignature = config.getBoolean(OPTIONS_OVERRIDE_SIGNATURE_KEY, false);

        messages.prefix = config.getString(MESSAGES_PREFIX_KEY, "");

        Map<String, String> values = new HashMap<>();
        values.put("prefix", messages.prefix);

        messages.itemNoSignature = translateAndReplace(config.getString(MESSAGES_ITEM_NO_SIGNATURE_KEY, ""), values);
        messages.itemHasSignature = translateAndReplace(config.getString(MESSAGES_ITEM_HAS_SIGNATURE_KEY, ""), values);
        messages.notHoldingItem = translateAndReplace(config.getString(MESSAGES_NOT_HOLDING_ITEM_KEY, ""), values);
        messages.cantSignItem = translateAndReplace(config.getString(MESSAGES_CANT_SIGN_ITEM_KEY, ""), values);
        messages.tooManyItems = translateAndReplace(config.getString(MESSAGES_TOO_MANY_ITEMS_KEY, ""), values);

        formats.date = config.getString(FORMATS_DATE_KEY, "dd/MM/yyyy");
        formats.time = config.getString(FORMATS_TIME_KEY, "HH:mm:ss");
    }

    private Component translateAndReplace(String message, Map<String, String> values) {
        return TextUtility.translateColorCodes(TextUtility.replacePlaceholders(message, values));
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public String getSignatureTemplate() {
        return signatureTemplate;
    }

    public String getItemNameTemplate() {
        return itemNameTemplate;
    }
}
