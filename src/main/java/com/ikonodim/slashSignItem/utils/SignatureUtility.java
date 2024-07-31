package com.ikonodim.slashSignItem.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import com.ikonodim.slashSignItem.config.Config;
import com.ikonodim.slashSignItem.constants.Keys;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class SignatureUtility {
    private static final Config CONFIG = Config.getInstance();

    private static List<Component> removeLoreSignature(List<Component> lore, String signature) {
        Objects.requireNonNull(lore, "Lore cannot be null");
        for (int i = 0; i < lore.size(); i++) {
            if (PlainTextComponentSerializer.plainText().serialize(lore.get(i)).equals(signature)) {
                lore.remove(i); // Remove the signature
                if (i - 1 >= 0) {
                    lore.remove(i - 1); // Remove the item before the signature if it exists
                }
                break;
            }
        }
        return lore;
    }

    private static List<Component> replaceSignature(List<Component> lore, String oldSignature, Component newSignature) {
        Objects.requireNonNull(lore, "Lore cannot be null");
        for (int i = 0; i < lore.size(); i++) {
            if (PlainTextComponentSerializer.plainText().serialize(lore.get(i)).equals(oldSignature)) {
                lore.set(i, newSignature);
                break;
            }
        }
        return lore;
    }

    private static Map<String, String> createSignaturePlaceholderValues(String name, Config.Formats formats) {
        Map<String, String> placeholderValues = new HashMap<>();
        placeholderValues.put("player", name);
        placeholderValues.put("date", new SimpleDateFormat(formats.date).format(new Date()));
        placeholderValues.put("time", LocalTime.now().format(DateTimeFormatter.ofPattern(formats.time)));
        return placeholderValues;
    }

    public static ItemMeta overwriteSignature(ItemMeta meta, String oldSignature, Component newSignature) {
        Objects.requireNonNull(meta, "ItemMeta cannot be null");
        ItemMeta newMeta = meta.clone();

        List<Component> lore = meta.lore();
        if (lore != null) {
            newMeta.lore(replaceSignature(lore, oldSignature, newSignature));
        }

        newMeta.getPersistentDataContainer().set(Keys.SIGN_CONTENT, PersistentDataType.STRING,
                TextUtility.componentToRawString(newSignature));
        return newMeta;
    }

    public static ItemMeta removeSignature(ItemMeta meta) {
        Objects.requireNonNull(meta, "ItemMeta cannot be null");
        String signature = meta.getPersistentDataContainer().get(Keys.SIGN_CONTENT, PersistentDataType.STRING);
        List<Component> lore = meta.lore();

        if (lore != null && signature != null) {
            meta.lore(removeLoreSignature(lore, signature));
        }

        meta.getPersistentDataContainer().remove(Keys.SIGN_KEY);
        meta.getPersistentDataContainer().remove(Keys.SIGN_CONTENT);
        meta.displayName(null);
        meta.setEnchantmentGlintOverride(false);

        return meta;
    }

    public static Component makeSignature(String signatureTemplate, Player player) {
        Objects.requireNonNull(signatureTemplate, "Signature template cannot be null");
        Objects.requireNonNull(player, "Player cannot be null");
        Map<String, String> placeholderValues = createSignaturePlaceholderValues(player.getName(), CONFIG.formats);
        return TextUtility.translateColorCodes(TextUtility.replacePlaceholders(signatureTemplate, placeholderValues));
    }
}
