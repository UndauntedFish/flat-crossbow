package com.ben.crossbowtda;

import java.util.Collection;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Bolt
{
	public static void launch(Arrow arrow, Player player, Main main)
	{
		/*
		// WORKING: Gets block player is looking at in a 500 block range
		Block targetBlock = player.getTargetBlockExact(1500);
		arrow.teleport(targetBlock.getLocation());
		*/
		
		
		//Block targetBlock = player.getTargetBlockExact(1500);
		/*
		 * Gets all entities in a chunk. 
		 * If a LivingEntity is within 2 blocks of the targetBlock, fire the arrow at the LivingEntity.
		 * Else, fire the arrow at the targetBlock.
		 */
		/*for (Entity e : targetBlock.getChunk().getEntities())
		{
			if (e instanceof LivingEntity && targetBlock.getLocation().distance(e.getLocation()) < 2)
			{
				arrow.setDamage(15);
				arrow.teleport(e.getLocation());
				return;
			}
		}
		arrow.teleport(targetBlock.getLocation());
		*/
		
		List<Block> blocksInLOS = player.getLineOfSight(null, 1500);
		
		// Gets the TDA crossbow name specified in config.yml
		double hitRad = main.getConfig().getDouble("hitradius");
		
		/* For each block in player's line of sight, check if there is an entity in a block
		 * If there is an entity in the block, teleport a bolt to the entity and damage it  */
		for (Block b : blocksInLOS)
		{
			Collection<Entity> entitiesByb = b.getWorld().getNearbyEntities(b.getLocation(), hitRad, hitRad, hitRad);
			
			if (!entitiesByb.isEmpty())
			{
				for (Entity e : entitiesByb)
				{
					if (e instanceof LivingEntity)
					{
						arrow.teleport(e); //Still needs to delete arrow without muting crossbow. Use runnable
						((LivingEntity) e).damage(10.5);
						entitiesByb.clear(); // clears entitiesByb to prevent 1 bolt from hitting multiple mobs
					}
					break;
				}
				arrow.remove();
			}
			else
			{
				// Teleports the arrow to the last block in player's line of sight
				arrow.teleport(blocksInLOS.get(blocksInLOS.size()-1).getLocation());
				arrow.setTicksLived(1);
			}
		}
		
		
		
		//WORKING YEES
		/*
		// Gets block player is looking at by analyzing the blocks in the player's line of sight
		List<Block> blocksInLOS = player.getLineOfSight(null, 500);
		
		// Gets the TDA crossbow name specified in config.yml
		double hitRad = main.getConfig().getDouble("hitradius");
		
		// Tests if entities are within the players LOS. If so, damage closest entity to arrow.
		for (Block b : blocksInLOS)
		{	
			// Gets all entities within a 2 block radius of block b
			Collection<Entity> entitiesInLOS = b.getWorld().getNearbyEntities(b.getLocation(), hitRad, hitRad, hitRad);
		
			if (!entitiesInLOS.isEmpty())
			{
				for (Entity e : entitiesInLOS)
				{
					if (e instanceof LivingEntity)
					{
						arrow.teleport(e);
						arrow.setTicksLived(1); //set the arrow to cancelled
						((LivingEntity) e).damage(10.5);
						break;
					}
				}
			}
			else if (!(b.getBlockData().getMaterial() == Material.AIR))
			{
				arrow.teleport(b.getLocation());
			}
			else
			{
				arrow.setTicksLived(1); // if the crossbow is shot into the sky, set arrow to cancelled
			}
		}
		*/
		
		
		/* TRY: For each block b in blocksInLOS, 
		 *       get the location of b 
		 *        and determine if any entities are within that location
		 */
		
	}
}
