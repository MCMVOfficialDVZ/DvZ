package com.lynxdeer.dvz.stuff;


import com.lynxdeer.dvz.DVZ;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Chat implements org.bukkit.event.Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		event.setJoinMessage("§e" + p.getName() + " joined.");
		Bukkit.getScheduler().runTaskLater(DVZ.getPlugin(), () -> {
			p.playSound(p.getLocation(), "entity.experience_orb.pickup", 1, 1);
		}, 1L);

	}

	@EventHandler
	public void onPluginCommand(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/pl") || event.getMessage().equalsIgnoreCase("/plugins")) {
			event.getPlayer().sendMessage("§elol why do server owners hide their plugins it's not like ppl can just copy it by looking at it unless you didn't make your server well");
		}
	}


	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage("§e" + event.getPlayer().getName() + " left.");
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		String m = e.getMessage();
		Player p = e.getPlayer();

		m = m.replaceAll(":shrug:", "¯\\\\" + (char) 0x5C + "_(ツ)_/¯");
		m = m.replaceAll(":tableflip:", "(╯°□°）╯︵ ┻━┻");
		m = m.replaceAll(":flip:", "(╯°□°）╯︵ ┻━┻");
		m = m.replaceAll(":skull:", "☠");
		m = m.replaceAll(":forgor:", "☠");
		m = m.replaceAll("%", "%%");

		if (m.equalsIgnoreCase("--edebug") && p.isOp()) {
			p.sendMessage("enabled debug");
			e.setCancelled(true);
			p.setMetadata("debug", new FixedMetadataValue(DVZ.getPlugin(), true));
			return;
		}
		if (m.equalsIgnoreCase("--ddebug") && p.isOp()) {
			p.sendMessage("disabled debug");
			e.setCancelled(true);
			p.setMetadata("debug", new FixedMetadataValue(DVZ.getPlugin(), false));
			return;
		}

		if (m.startsWith("<rainbow>")) {
			m = m.replaceFirst("<rainbow>", "");
			if (m.startsWith(" ")) m = m.replaceFirst(" ", "");
			String[] rm = m.split("");
			int rc = 1;
			for (int i = 0; i < rm.length; i++) {

				if (rc == 1) rm[i] = "§c" + rm[i];
				if (rc == 2) rm[i] = "§6" + rm[i];
				if (rc == 3) rm[i] = "§e" + rm[i];
				if (rc == 4) rm[i] = "§a" + rm[i];
				if (rc == 5) rm[i] = "§9" + rm[i];
				if (rc == 6) rm[i] = net.md_5.bungee.api.ChatColor.of("#8133f5") + rm[i];
				rc++;
				if (rc > 6) rc = 1;


			}
			m = String.join("", rm);

		}

		e.setFormat("§f" + p.getName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', m));

	}

}