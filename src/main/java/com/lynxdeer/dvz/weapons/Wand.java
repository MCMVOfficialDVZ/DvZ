package com.lynxdeer.dvz.weapons;

import com.lynxdeer.dvz.DVZ;
import com.lynxdeer.dvz.important.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Wand implements org.bukkit.event.Listener {

	@EventHandler
	public void onWandUse(PlayerInteractEvent event) {

		if (!(Items.getItemID(event.getPlayer().getInventory().getItemInMainHand()).equals("WAND"))) return;
		Player p = event.getPlayer();

		if (p.getCooldown(Material.STICK) > 0) return;

		if (DVZ.islc(event)) {

			DVZ.playClearSound(p, "entity.ender_dragon.hurt", 1, 1.5f);
			DVZ.playClearSound(p, "entity.blaze.shoot", 1, 2);

			p.setCooldown(Material.STICK, 15);
			Location loc = p.getEyeLocation();

			for (int i = 1; i < 30; i++) {
				loc.add(loc.getDirection().multiply(new Vector(0.5, 0.5, 0.5)));
				if (DVZ.canPass(loc)) break;
				if (DVZ.hitbox(p, loc).size() > 0) break;
				loc.getWorld().spawnParticle(Particle.END_ROD, loc, 1, 0, 0, 0, 0);
			}

		}

	}

}
