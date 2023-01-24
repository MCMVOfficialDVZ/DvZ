package com.lynxdeer.dvz;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public final class DVZ extends JavaPlugin implements org.bukkit.event.Listener {

	private static DVZ plugin;



	@Override
	public void onEnable() {
		plugin = this;

		this.getServer().getPluginManager().registerEvents(this, this);

		this.getServer().getPluginManager().registerEvents(new com.lynxdeer.dvz.weapons.Wand(), this);


		Objects.requireNonNull(this.getCommand("cgive")).setExecutor(new com.lynxdeer.dvz.important.Items());
	}



	public static DVZ getPlugin() {return plugin;}



	@EventHandler
	public void onItemSwitch(PlayerItemHeldEvent event) {
		Player p = event.getPlayer();

		if (p.getInventory().getItem(event.getNewSlot()) == null) return;

		ItemStack tool = p.getInventory().getItem(event.getNewSlot());
		assert tool != null;
		if (p.getCooldown(tool.getType()) < 10) p.setCooldown(tool.getType(), 10);
	}



	public static boolean isrc(PlayerInteractEvent event) {
		// returns true if event is right click
		return (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK);
	}

	public static boolean islc(PlayerInteractEvent event) {
		return (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK);
	}



	public static boolean entityisproper(LivingEntity ent) {

		return !(ent instanceof Painting) && !(ent instanceof Boat) &&
				!(ent instanceof EnderCrystal) && !(ent instanceof Minecart) &&
				!(ent instanceof ItemFrame) && !(ent instanceof GlowItemFrame) &&
				!(ent instanceof Item) && !(ent instanceof SmallFireball) &&
				!(ent instanceof DragonFireball) && !(ent instanceof Arrow) &&
				!(ent instanceof ArmorStand) && !(ent instanceof Egg) && !(ent instanceof Snowball)
				;
	}



	public static ArrayList<LivingEntity> hitbox(Player p, Location loc) {
		ArrayList<LivingEntity> ret = new ArrayList<>();
		for (LivingEntity loopentity : loc.getWorld().getLivingEntities()) {if ((loopentity.getBoundingBox().contains(loc.getX(), loc.getY(), loc.getZ())) && entityisproper(loopentity) && loopentity!=p) ret.add(loopentity);}
		return ret;
	}

	public static ArrayList<LivingEntity> hitbox(Location loc) {
		ArrayList<LivingEntity> ret = new ArrayList<>();
		for (LivingEntity loopentity : loc.getWorld().getLivingEntities()) {if ((loopentity.getBoundingBox().contains(loc.getX(), loc.getY(), loc.getZ())) && entityisproper(loopentity)) ret.add(loopentity);}
		return ret;
	}



	public static void playClearSound(Player p, Sound s, float volume, float pitch) {
		p.playSound(p.getLocation(), s, 1000, pitch);
		for (Player lp : Bukkit.getOnlinePlayers()) {
			if (lp== p) continue;
			lp.playSound(p.getEyeLocation(), s, volume, pitch);
		}
	}

	public static void playClearSound(Player p, String s, float volume, float pitch) {
		p.playSound(p.getEyeLocation(), s, 1000, pitch);
		for (Player lp : Bukkit.getOnlinePlayers()) {
			if (lp== p) continue;
			lp.playSound(p.getEyeLocation(), s, volume, pitch);
		}
	}



	public String timeformat(int n) {

		n = (int) Math.floor(n);
		int mins = 0; int secs = n;

		if (n > 59) {
			int ln = n;
			while (ln > 0) {
				ln-=60;
				if (ln < 0) {ln+=60; break;}
				mins++;
			}
			secs = Math.abs(ln);
		}

		String stringsec = (secs < 10) ? "0" + secs: secs + "";
		return mins + ":" + stringsec;
	}



	public static void debug(String s) {

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals("Lynxdeer")) p.sendMessage("[Debug] " + ChatColor.translateAlternateColorCodes('&', s));
		}

		Bukkit.getLogger().log(Level.INFO, "[Debug]" + s);

	}



	public static boolean isPassableBlock(Block block) {
		Material t = block.getType();
		return t == Material.AIR || t == Material.GRASS || t == Material.TALL_GRASS || t == Material.DEAD_BUSH || t == Material.BARRIER || t == Material.CORNFLOWER
				|| t == Material.CRIMSON_FUNGUS || t == Material.WARPED_FUNGUS || t == Material.ALLIUM || t == Material.DANDELION || t == Material.ACACIA_SAPLING || t == Material.AZURE_BLUET ||
				t == Material.POPPY || t == Material.WARPED_ROOTS || t == Material.FERN || t == Material.LILY_OF_THE_VALLEY || t == Material.WITHER_ROSE ||
				t == Material.RED_TULIP || t == Material.ORANGE_TULIP || t == Material.PINK_TULIP || t == Material.WHITE_TULIP || t == Material.BROWN_MUSHROOM ||
				t == Material.RED_MUSHROOM || t == Material.BLUE_ORCHID;
	}

	public static boolean canPass(Location loc) {

		if (isPassableBlock(loc.getBlock())) return true;
		if (loc.getBlock().getType() == Material.AIR) return true;
		if (!(loc.getBlock().getBoundingBox().contains(loc.getX(), loc.getY(), loc.getZ()))) return true;

		return false;
	}

}
