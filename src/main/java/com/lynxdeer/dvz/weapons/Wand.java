package com.lynxdeer.dvz.weapons;

import com.lynxdeer.dvz.DVZ;
import com.lynxdeer.dvz.important.Items;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Wand implements org.bukkit.event.Listener {

	@EventHandler
	public void onWandUse(PlayerInteractEvent event) {

		Player p = event.getPlayer();
		if (!(Items.getItemID(p.getInventory().getItemInMainHand()).equals("WAND"))) return;
		event.setCancelled(true);

		if (DVZ.islc(event)) wandMainAttack(p);

	}

	@EventHandler
	public void onWandHit(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player p)) return;

		if (!(Items.getItemID(p.getInventory().getItemInMainHand()).equals("WAND"))) return;
		event.setCancelled(true);
		wandMainAttack(p);

	}

	public void wandMainAttack(Player p) {

		if (!(Items.getItemID(p.getInventory().getItemInMainHand()).equals("WAND"))) return;

		if (p.getCooldown(Material.STICK) > 0) return;

		DVZ.playClearSound(p, "entity.ender_dragon.hurt", 1, 1.5f);
		DVZ.playClearSound(p, "entity.blaze.shoot", 1, 2);

		p.setCooldown(Material.STICK, 15);

			final Location[] loc = {p.getEyeLocation()};
			final int[] times = {0};
			BukkitTask runnable = new BukkitRunnable() {
				@Override
				public void run() {

					for (int i = 0; i < 5; i++) {

						if (loc[0].getY() < 1) this.cancel();
						if (!(DVZ.canPass(loc[0])) || DVZ.closeairmobs(p, loc[0], 4).size()>0 || DVZ.hitbox(p, loc[0]).size()>0) {
							times[0] = 5;
							break;
						}

						loc[0].add(loc[0].getDirection().multiply(new Vector(0.5, 0.5, 0.5)));

						p.getWorld().spawnParticle(Particle.CRIT_MAGIC, loc[0], 1, 0, 0, 0, 0);

					}

					times[0]++;
					if (times[0] > 5) {

						for (LivingEntity loopentity : loc[0].getWorld().getLivingEntities()) {
							if (loopentity == p) continue;
							if (loopentity.getLocation().distance(loc[0]) < 5) {
								loopentity.damage((5-loopentity.getLocation().distance(loc[0]) + ((loopentity.isOnGround()) ? 1 : 10)));
								if (!loopentity.isOnGround()) {
									p.playSound(p.getLocation(), "entity.arrow.hit_player", 1000, 1);
									loopentity.getWorld().spawnParticle( Particle.TOTEM, loopentity.getLocation().add(new Vector(0, 1, 0)), 10, 0, 0, 0, 0.5 );
								}
								loopentity.setVelocity( loopentity.getVelocity().add(loopentity.getLocation().subtract(loc[0]).toVector().normalize().multiply(  (5-loopentity.getLocation().distance(loc[0]))/5  )).multiply(new Vector(1, 0, 1)).add(new Vector(0, 1, 0)) );
							}
						}

						loc[0].getWorld().playSound(loc[0], "entity.blaze.hurt", 1, 0.5f);
						loc[0].getWorld().playSound(loc[0], "entity.generic.explode", 1, 2);
						loc[0].getWorld().playSound(loc[0], "entity.iron_golem.hurt", 1, 2);
						p.getWorld().spawnParticle(Particle.CLOUD, loc[0], 5, 0, 0, 0, 0.1);
						p.getWorld().spawnParticle(Particle.CLOUD, loc[0], 5, 0, 0, 0, 0.25);
						p.getWorld().spawnParticle(Particle.CLOUD, loc[0], 5, 0, 0, 0, 0.5);
						p.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc[0], 5, 0, 0, 0, 0.5);
						this.cancel();

					}

				}}.runTaskTimer(DVZ.getPlugin(), 0L, 1L);

	}

}
