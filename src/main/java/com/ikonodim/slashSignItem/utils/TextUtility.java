package com.ikonodim.slashSignItem.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.Map;

/**
 * Utility class for working with Components and placeholder replacements.
 */
public class TextUtility {

    /**
     * Translates a string with color codes using the '&' character into a Component.
     *
     * @param text The string containing color codes.
     * @return A Component with the translated color codes.
     */
    public static Component translateColorCodes(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null.");
        }
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }

    /**
     * Replaces placeholders in a template string with corresponding values from a map.
     * Placeholders should be in the format "%key%".
     *
     * @param template The template string containing placeholders.
     * @param values   The map containing keys and their corresponding values.
     * @return The template string with placeholders replaced by values.
     */
    public static String replacePlaceholders(String template, Map<String, String> values) {
        if (template == null || values == null) {
            throw new IllegalArgumentException("Template and values cannot be null.");
        }

        StringBuilder result = new StringBuilder(template);

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String placeholder = "%" + entry.getKey() + "%";
            int start;
            while ((start = result.indexOf(placeholder)) != -1) {
                result.replace(start, start + placeholder.length(), entry.getValue());
            }
        }

        return result.toString();
    }

    /**
     * Serializes a Component into a raw string, stripping away all formatting.
     *
     * @param component The Component to serialize.
     * @return The raw string representation of the Component.
     */
    public static String componentToRawString(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null.");
        }
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
