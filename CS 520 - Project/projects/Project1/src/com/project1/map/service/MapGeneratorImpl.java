package com.project1.map.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.agent.utility.CommonTraverseMethods;
import com.project1.map.DTO.MapReaderDTO;
import com.project1.map.model.Map;
import com.project1.map.utility.Constants;

public class MapGeneratorImpl implements MapGenerator{

	public static Random randVar = new Random();
	
	public static Date date = new Date();
	
	/**
	 * @return a list of Maps generated with random probability of being blocked or unblocked and there exists 
	 * a path to the destination in the output map
	 */
	@Override
	public List<Map> generateMap() {
		
		int validMapCounter = 0;
		List<Map> outputMaps = new ArrayList<>();
		
		while(validMapCounter < Constants.NO_OF_VALID_MAPS) {
			Map outputMap = new Map();
			outputMap.setGridSize(Constants.GRIDSIZE);
			boolean validationSuccessful = false;
			
//			ExecutorService pool = Executors.newFixedThreadPool(1);
//			Runnable runnable = new MapGeneratorThread(this, outputMap, validationSuccessful);
//			pool.execute(runnable);
			
			generate(outputMap);
			
			if(CommonTraverseMethods.validate(outputMap)) {
//				validationSuccessful = true;
				outputMaps.add(outputMap);
				validMapCounter++;
			}
//			if(validationSuccessful) {
////				outputMaps.add(outputMap);
////				validMapCounter++;
//			}
		}
		
		for(Map map: outputMaps) {
			CommonTraverseMethods.setReachableNodesPathToGoal(map);
		}
		
		writeMap(outputMaps);
		
		System.out.println("Total Number of Valid Maps: " + outputMaps.size());
		
		return outputMaps;		
	}

	private void writeMap(List<Map> outputMaps) {
		
		MapReaderDTO mapReader = new MapReaderDTO();
		mapReader.setValidatedMap(outputMaps);
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(mapReader);
			try (PrintWriter out = new PrintWriter(new FileWriter(Constants.ENV_FILE_PATH))) {
				out.write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param newMap
	 * @return True if it does, else false
	 */
	@Override
	public Map generate(Map newMap) {
		
		int gridSize = newMap.getGridSize();
		int[][] grid = new int[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if((i == 0 && j == 0) || (i == gridSize - 1 && j == gridSize - 1)) {
					grid[i][j] = Constants.UNBLOCKED;
					break;
				}
				double blockChance = randVar.nextDouble();
				if(blockChance <= Constants.PROB_OF_BLOCKED) {
					grid[i][j] = Constants.BLOCKED;
				}
				else {
					grid[i][j] = Constants.UNBLOCKED;
				}
			}
		}
		newMap.setGrid(grid);
		return newMap;
		
	}	
	
}
