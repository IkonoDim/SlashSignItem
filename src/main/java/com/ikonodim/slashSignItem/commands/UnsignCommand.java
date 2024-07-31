package com.ikonodim.slashSignItem.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import static com.ikonodim.slashSignItem.utils.SignatureUtility.removeSignature;
import com.ikonodim.slashSignItem.config.Config;
import com.ikonodim.slashSignItem.constants.Keys;

public class UnsignCommand implements CommandExecutor {
    private static final Config CONFIG = Config.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the item has metadata and if it contains the SIGN_KEY
        if (item.getType() == Material.AIR || !item.hasItemMeta()) {
            player.sendMessage(CONFIG.messages.itemNoSignature);
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            player.sendMessage(CONFIG.messages.itemNoSignature);
            return true;
        }

        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        if (!dataContainer.has(Keys.SIGN_KEY, PersistentDataType.BOOLEAN)) {
            player.sendMessage(CONFIG.messages.itemNoSignature);
            return true;
        }

        // Remove the signature
        item.setItemMeta(removeSignature(meta));

        return true;
    }
}
