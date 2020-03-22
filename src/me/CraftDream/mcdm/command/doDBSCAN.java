package me.CraftDream.mcdm.command;

import java.util.*;

import cn.nukkit.*;
import cn.nukkit.command.*;
import cn.nukkit.level.*;
import me.CraftDream.mcdm.command.algorithm.DBSCAN;

public class doDBSCAN {

	public static void run (CommandSender sender, String worldInput, List<Location> locationList, String task) {
		
		ArrayList<double[]> arrayXYZ = new ArrayList<>();
		
		for (Location sample : locationList) {
			if (sample.getLevel().getName().equals(worldInput)) {
				int ATRIBUTE_NUMBER = 3; //x y z
				
				String [] xyz = (sample.getX() + "," + sample.getY() + "," + sample.getZ()).split(",");
				double[] array = new double[ATRIBUTE_NUMBER];
	            for (int i = 0; i < ATRIBUTE_NUMBER; i++) {
	                array[i] = Double.parseDouble(xyz[i]);
	            }
	            arrayXYZ.add(array);
			}
		}
		if (locationList.size() == 0) {
			sender.sendMessage("§b[MCdataMining]§e未搜索到结果，请确认世界是否已开启");
		} else if (arrayXYZ.size() == 0) {
			sender.sendMessage("§b[MCdataMining]§e未找到世界，请确认世界名是否与下列匹配");
			Map<Integer, Level> worlds = Server.getInstance().getLevels();
			for (Level w : worlds.values()) {
				sender.sendMessage("==== " + w.getName());
			}
		} else {
			// start DBSCAN
			// arrayXYZ is input of DBSCAN
			DBSCAN.main(sender, arrayXYZ, locationList, task);
		}
	}
	
}
