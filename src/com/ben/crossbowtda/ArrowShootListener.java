package com.ben.crossbowtda;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArrowShootListener implements Listener
{
	private Main main;
	
	public ArrowShootListener(Main main)
	{
		this.main = main;
	}
	
	private String getName(ItemStack e)
	{
        ItemMeta m = e.getItemMeta();
        return m.getDisplayName();
    }
	
	@EventHandler
	public void onPlayerShoot(ProjectileLaunchEvent e)
	{
		Player player;
		
		if (e.getEntity() instanceof Arrow && e.getEntity().getShooter() instanceof Player)
		{
			player = (Player) e.getEntity().getShooter();
			
			// Gets the crossbow that the player is holding. May be null, in which case if(mainItem != null) is needed
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			ItemStack offItem = player.getInventory().getItemInOffHand();
			
			// Gets the TDA crossbow name specified in config.yml
			String tdaName = main.getConfig().getString("crossbowname");
			
			// Checks if player is using a TDA crossbow, then launches a bolt.
			if (mainItem.getType() == Material.CROSSBOW)
			{
				if (getName(mainItem).equals(tdaName))
				{ 
					Bolt.launch((Arrow) e.getEntity(), player, main);
				}
			}
			else if (offItem.getType() == Material.CROSSBOW)
			{
				if (getName(offItem).equals(tdaName))
				{
					Bolt.launch((Arrow) e.getEntity(), player, main);
				}
			}
		}
	}
}
