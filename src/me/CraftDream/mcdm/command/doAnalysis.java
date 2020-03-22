package me.CraftDream.mcdm.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.*;
import cn.nukkit.blockentity.*;
import cn.nukkit.command.*;
import cn.nukkit.entity.*;
import cn.nukkit.entity.item.*;
import cn.nukkit.level.*;

import me.CraftDream.mcdm.util.SortMap;

public class doAnalysis {
	
	public static List<Location> run (CommandSender sender, String label, Boolean report) {
		
		List<Location> locationList = new ArrayList<Location>(); //for invoked by k-means
		
		if (label == "mob") {
			sender.sendMessage("=====================================");
	    	Map<Integer, Level> worlds = Server.getInstance().getLevels();
	    	Map<String, Integer> entityMap = new HashMap<String, Integer>();
	    	for (Level w : worlds.values()) {
	    		int count = 0;
	    		Entity[] allEntitiesInAworld = w.getEntities();
//	    		List<Location> locationList = new ArrayList<Location>();
	    		for (Entity entities : allEntitiesInAworld) {
	    			// k-v map for counting Entities by type (name)
    				if (entities instanceof Player == false && entities instanceof EntityItem == false){
    					count++;
    					String entityName = entities.getName();
	        			if (entityMap.containsKey(entityName)) {
	        				int valueCount = entityMap.get(entityName);
	        				valueCount++;
	        				entityMap.put(entityName,valueCount);
	        			} else {entityMap.put(entityName,1);}
		    			// logging the location for further clustering 
		    			locationList.add(entities.getLocation());
		    			entityMap = SortMap.sortDescend(entityMap); //sort
	    			}
	    		}
	   			//report statistical result to server
	    		if (report) {
		    		sender.sendMessage("世界: " + w.getName() + " 中存在 " + count + " 个生物实体");
					for (Map.Entry<String, Integer> entry : entityMap.entrySet()) {
						sender.sendMessage("生物: " + entry.getKey() + ", 总数: " + entry.getValue());
					}
					sender.sendMessage("=====================================");
					entityMap.clear(); // clear for the next world
	    		}
	    	}
		} else if (label == "drop") {
			sender.sendMessage("=====================================");
	    	Map<Integer, Level> worlds = Server.getInstance().getLevels();
	    	Map<String, Integer> entityMap = new HashMap<String, Integer>();
	    	for (Level w : worlds.values()) {
	    		int count = 0;
	    		Entity[] allEntitiesInAworld = w.getEntities();
//	    		List<Location> locationList = new ArrayList<Location>();
	    		for (Entity entities : allEntitiesInAworld) {
	    			// k-v map for counting Entities by type (name)
	    			if (entities instanceof EntityItem){
    					count++;
	    				String entityName = entities.getName();
	        			if (entityMap.containsKey(entityName)) {
	        				int valueCount = entityMap.get(entityName);
	        				valueCount++;
	        				entityMap.put(entityName,valueCount);
	        			} else {entityMap.put(entityName,1);}
		    			// logging the location for further clustering 
		    			locationList.add(entities.getLocation());	
		    			entityMap = SortMap.sortDescend(entityMap); //sort
	    			}  			
	    		}
	   			//report statistical result to server
	    		if (report) {
		    		sender.sendMessage("世界: " + w.getName() + " 中存在 " + count + " 个掉落物实体");
					for (Map.Entry<String, Integer> entry : entityMap.entrySet()) {
						sender.sendMessage("掉落物: " + entry.getKey() + ", 总数: " + entry.getValue());
					}
					sender.sendMessage("=====================================");
					entityMap.clear(); // clear for the next world
	    		} 
	    	}
		} else if (label == "tiles") {
			sender.sendMessage("=====================================");
			Map<Integer, Level> worlds = Server.getInstance().getLevels();
			Map<String, Integer> tileMap = new HashMap<String, Integer>();
			for (Level w : worlds.values()) {
				int count = 0;
//				List<Location> locationList = new ArrayList<Location>();
				for (BlockEntity tile : w.getBlockEntities().values()) {
					count++;
					String tileName = String.valueOf(tile.getBlock().getId());
					if (tileMap.containsKey(tileName)) {
						int valueCount = tileMap.get(tileName);
						valueCount++;
						tileMap.put(tileName,valueCount);
					} else {tileMap.put(tileName,1);}
					// logging the location for further clustering
					locationList.add(tile.getLocation());
					tileMap = SortMap.sortDescend(tileMap); //sort
				}
	   			//report statistical result to server
	    		if (report) {
					sender.sendMessage("世界: " + w.getName() + " 中存在 " + count + " 个tiles方块");
					for (Map.Entry<String, Integer> entry : tileMap.entrySet()) {
						sender.sendMessage("tiles: " + entry.getKey() + ", 总数: " + entry.getValue());
					}
					sender.sendMessage("=====================================");
					tileMap.clear(); // clear for the next world
	    		} 
			}
		}
		return locationList;
	}
}
