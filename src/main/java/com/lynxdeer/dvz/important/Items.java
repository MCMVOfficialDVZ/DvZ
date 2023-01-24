package com.lynxdeer.dvz.important;

import com.lynxdeer.dvz.DVZ;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Items implements CommandExecutor {

	public ItemStack getItem(String s) {

		ItemStack i = new ItemStack(Material.AIR);

		switch(s.toUpperCase()) {
			case "WAND" -> {
				i = new ItemStack(Material.STICK);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName("§eWand");
				im.setLore(List.of("§7MMMMMAGIC"));
				im.setCustomModelData(1);
				i.setItemMeta(im);
			}
			case "SWORD" -> {
				i = new ItemStack(Material.IRON_SWORD);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName("§fSword");
				im.setLore(List.of("§7stabby stabby"));
				im.setCustomModelData(1);
				i.setItemMeta(im);
			}
		}

		ItemMeta m = i.getItemMeta();
		m.setUnbreakable(true);
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS);
		m.getPersistentDataContainer().set(new NamespacedKey(DVZ.getPlugin(), "id"), PersistentDataType.STRING, s.toUpperCase());
		i.setItemMeta(m);
		return i;
	}

	public static String getItemID(ItemStack item) {
		ItemMeta im = item.getItemMeta();
		if (im == null) return "";
		return (im.getPersistentDataContainer().has(new NamespacedKey(DVZ.getPlugin(), "id"))) ? im.getPersistentDataContainer().get(new NamespacedKey(DVZ.getPlugin(), "id"), PersistentDataType.STRING) : "";
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player p)) return false;
		if (args.length <= 0) return true;
		p.sendMessage("Gave you the item with the id " + args[0].toUpperCase());
		p.getInventory().addItem( getItem(args[0]) );
		return true;
	}
}
