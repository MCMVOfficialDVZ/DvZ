package com.lynxdeer.dvz.weapons;

import com.lynxdeer.dvz.DVZ;
import com.lynxdeer.dvz.important.Items;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Orbs implements org.bukkit.event.Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (!DVZ.isrc(event)) return;
		Player p = event.getPlayer();
		if (!(Items.getItemID(event.getPlayer().getInventory().getItemInMainHand()).contains("ORB"))) return;

		if (Items.getItemID(event.getPlayer().getInventory().getItemInMainHand()).equals("STRENGTH_ORB")) {

			final float[] grav = {0.3f}; // Java is so stupid.
			final Location[] loc = {p.getEyeLocation()};

			p.setCooldown(p.getInventory().getItemInMainHand().getType(), 160);
			DVZ.playClearSound(p, "minecraft:entity.witch.throw", 1, 0.75f);
			BukkitTask runnable = new BukkitRunnable() {
				@Override
				public void run() {

					for (int i = 0; i < 3; i++) {

						if (loc[0].getY() < 1) this.cancel();
						if (!(DVZ.canPass(loc[0])) || DVZ.hitbox(p, loc[0]).size() > 0) {
							loc[0].getWorld().playSound(loc[0], "block.lava.pop", 1, 0.5f);
							loc[0].getWorld().playSound(loc[0], "entity.splash_potion.break", 1, 0.5f);
							loc[0].getWorld().playSound(loc[0], "entity.splash_potion.break", 1, 2f);
							p.getWorld().spawnParticle(Particle.SPELL_WITCH, loc[0], 20, 0, 0, 0, 1);
							this.cancel();
							break;
						}

						loc[0].add(loc[0].getDirection().multiply(new Vector(0.5, 0.5, 0.5)));
						loc[0].add(0, grav[0]/3, 0);

						grav[0] -=0.033f;

						p.getWorld().spawnParticle(Particle.CRIT_MAGIC, loc[0], 1, 0, 0, 0, 0);

					}


				}
			}.runTaskTimer(DVZ.getPlugin(), 0L, 1L);
		}

	}

}
