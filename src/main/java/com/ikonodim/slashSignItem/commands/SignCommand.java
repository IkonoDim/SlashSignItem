package com.ikonodim.slashSignItem.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.ikonodim.slashSignItem.utils.SignatureUtility.*;
import static com.ikonodim.slashSignItem.utils.TextUtility.*;
import com.ikonodim.slashSignItem.config.Config;
import com.ikonodim.slashSignItem.constants.Keys;
import com.ikonodim.slashSignItem.constants.Permissions;

public class SignCommand implements CommandExecutor {
    private static final Config CONFIG = Config.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage(CONFIG.messages.notHoldingItem);
            return true;
        }

        if (!canSignItem(player, item)) {
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            player.sendMessage(CONFIG.messages.cantSignItem);
            return true;
        }

        if (isItemAlreadySigned(meta)) {
            handleOverwriteSignature(player, item, meta);
            return true;
        }

        applySignature(player, item, meta);
        return true;
    }

    private boolean canSignItem(Player player, ItemStack item) {
        if (item.getAmount() > CONFIG.options.maxStackSize && CONFIG.options.maxStackSize != -1) {
            if (!player.hasPermission(Permissions.IGNORE_MAX_STACK_SIZE)) {
                player.sendMessage(CONFIG.messages.tooManyItems);
                return false;
            }
        }

        if (CONFIG.options.itemBlacklist.contains(item.getType().toString().toLowerCase())) {
            if (!player.hasPermission(Permissions.IGNORE_BLACKLIST)) {
                player.sendMessage(CONFIG.messages.cantSignItem);
                return false;
            }
        }

        return true;
    }

    private boolean isItemAlreadySigned(ItemMeta meta) {
        return Boolean.TRUE.equals(meta.getPersistentDataContainer().get(Keys.SIGN_KEY, PersistentDataType.BOOLEAN));
    }

    private void handleOverwriteSignature(Player player, ItemStack item, ItemMeta meta) {
        if (player.hasPermission(Permissions.OVERWRITE_SIGNATURE)) {
            ItemMeta newMeta = overwriteSignature(meta,
                    meta.getPersistentDataContainer().get(Keys.SIGN_CONTENT, PersistentDataType.STRING),
                    makeSignature(CONFIG.getSignatureTemplate(), player));
            item.setItemMeta(newMeta);
        } else {
            player.sendMessage(CONFIG.messages.itemHasSignature);
        }
    }

    private void applySignature(Player player, ItemStack item, ItemMeta meta) {
        List<Component> lore = meta.hasLore() ? meta.lore() : new ArrayList<>();
        if (lore == null) lore = new ArrayList<>();

        Component newName = meta.hasDisplayName() ? meta.displayName() : Component.translatable(item.translationKey());
        Component displayName = translateColorCodes(CONFIG.getItemNameTemplate().replace("%name%", componentToRawString(newName)));
        Component modifiedDisplayName = Component.text("").decoration(TextDecoration.ITALIC, false).append(displayName);

        meta.displayName(modifiedDisplayName);
        lore.add(Component.text(" "));
        Component signature = makeSignature(CONFIG.getSignatureTemplate(), player);
        lore.add(signature);

        if (CONFIG.options.enchantmentGlint) {
            meta.setEnchantmentGlintOverride(true);
        }

        meta.getPersistentDataContainer().set(Keys.SIGN_KEY, PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(Keys.SIGN_CONTENT, PersistentDataType.STRING, componentToRawString(signature));

        meta.lore(lore);
        item.setItemMeta(meta);
    }
}
