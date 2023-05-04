package net.dkcraft.sheepmeat;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Ageable) {
			Ageable entity = (Ageable)event.getEntity();
			if (!entity.isAdult()) {
				if ((LivingEntity)entity.getKiller() instanceof Player) {
					Player player = (Player)(LivingEntity)entity.getKiller();
					if (player.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
							if (event.getEntity().getType().equals(EntityType.SHEEP)) {
								dropItem(entity, Material.PORK, 3, "Raw Lamb");
							}
					} else {
						if (event.getEntity().getType().equals(EntityType.SHEEP)) {
							if (player.getItemInHand().containsEnchantment(Enchantment.FIRE_ASPECT)) {
								dropItem(entity, Material.GRILLED_PORK, 1, "Cooked Lamb");
							} else {
								dropItem(entity, Material.PORK, 1, "Raw Lamb");
							}
						}
					}
				} else {
					if (event.getEntity().getType().equals(EntityType.SHEEP)) {
						if (event.getEntity().getLastDamageCause().getCause() == DamageCause.FIRE_TICK) {
							dropItem(entity, Material.GRILLED_PORK, 1, "Cooked Lamb");
						} else {
							dropItem(entity, Material.PORK, 1, "Raw Lamb");
						}
					}
				}
			} else {
				if ((LivingEntity)entity.getKiller() instanceof Player) {
					Player player = (Player)(LivingEntity)entity.getKiller();
					if (player.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
						if (event.getEntity().getType().equals(EntityType.SHEEP)) {
							dropItem(entity, Material.PORK, 3, "Raw Mutton");
						}
					} else {
						if (event.getEntity().getType().equals(EntityType.SHEEP)) {
							if (player.getItemInHand().containsEnchantment(Enchantment.FIRE_ASPECT)) {
								dropItem(entity, Material.GRILLED_PORK, 1, "Cooked Mutton");
							} else {
								dropItem(entity, Material.PORK, 1, "Raw Mutton");
							}
						}
					}
				} else {
					if (event.getEntity().getType().equals(EntityType.SHEEP)) {
						if (event.getEntity().getLastDamageCause().getCause() == DamageCause.FIRE_TICK) {
							dropItem(entity, Material.GRILLED_PORK, 1, "Cooked Mutton");
						} else {
							dropItem(entity, Material.PORK, 1, "Raw Mutton");
						}
					}
				}
			}
		}
	}
	
	private void dropItem(LivingEntity living, Material material, int amount, String name) {
		living.getWorld().dropItem(living.getLocation(), customItem(material, amount, name));
	}
	
	@EventHandler
	private void onSmelt(FurnaceSmeltEvent event) {
		ItemStack extractedItem = event.getSource();
		if (extractedItem.getType().equals(Material.PORK)) {
			if (extractedItem.getItemMeta().getDisplayName().equals("Raw Lamb")) {
				event.setResult(customItem(Material.GRILLED_PORK, 1, "Cooked Lamb"));
			}
		}
		if (extractedItem.getType().equals(Material.PORK)) {
			if (extractedItem.getItemMeta().getDisplayName().equals("Raw Mutton")) {
				event.setResult(customItem(Material.GRILLED_PORK, 1, "Cooked Mutton"));
			}
		}
	}
	
	private ItemStack customItem(Material material, int amount, String name) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
		
		public void onDisable() {}
}
