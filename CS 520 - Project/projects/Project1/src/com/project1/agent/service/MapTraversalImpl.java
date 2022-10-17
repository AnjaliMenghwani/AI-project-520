package com.project1.agent.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.agent.utility.AgentTraversalThread;
import com.project1.agent.utility.CommonTraverseMethods;
import com.project1.map.model.Coordinates;
import com.project1.map.model.Map;
import com.project1.map.model.Node;
import com.project1.map.utility.Agent;
import com.project1.map.utility.CommonNodeMethods;
import com.project1.map.utility.CommonlyUsedMethods;
import com.project1.map.utility.Constants;

import ResultantData.ResultantData;

public class MapTraversalImpl implements MapTraversal {
	
	public static Random randVar = new Random();
	
	@Override
	public HashMap<Map, Boolean> Agent1(List<Map> validatedMaps) {
		
		HashMap<Map, Boolean> successRate = new HashMap<>();
		
		moveAgent(validatedMaps, Agent.AGENT1);
		
		return successRate;
	}

	@Override
	public HashMap<Map, Boolean> Agent2(List<Map> validatedMaps) {
		
		HashMap<Map, Boolean> successRate = new HashMap<>();
		
		moveAgent(validatedMaps, Agent.AGENT2);

		return successRate;
	}

	@Override
	public HashMap<Map, Boolean> Agent3(List<Map> validatedMaps) {
		
		HashMap<Map, Boolean> successRate = new HashMap<>();
		
		moveAgent(validatedMaps, Agent.AGENT3);

		return successRate;
	}

	@Override
	public HashMap<Map, Boolean> Agent4(List<Map> validatedMaps) {

		moveAgent(validatedMaps, Agent.AGENT4);

		return null;
	}
	
	@Override
	public HashMap<Map, Boolean> moveAgent(List<Map> validatedMaps, Agent agent)
	{
		
		HashMap<Map, Boolean> successRate = new HashMap<>();
		ExecutorService pool = Executors.newFixedThreadPool(1);	
		HashMap<String, List<ResultantData>> allResultantData = new HashMap<>();
		
		int iter = 1;
		
		File file = null;
				
		for(Map eachMap: validatedMaps) {
			
			List<ResultantData> resultantDataListForMap = new ArrayList<>();

			for(iter = 0; iter<10 ; iter++)
			{
				int noOfGhosts = (int) Math.pow(2,iter);
				
				ResultantData resultantData = new ResultantData();
				
				resultantDataListForMap.add(resultantData);
				resultantData.setNumberOfGhosts(noOfGhosts);
				
				Runnable runnable = new AgentTraversalThread(eachMap, resultantData, noOfGhosts, agent);
				pool.execute(runnable);
//
				System.out.println("resultant data successs: " + resultantData.isSuccess());
//				if(resultantData.isSuccess())
//				{
//					totalSuccess++;
//				}
			}
			
			allResultantData.put(eachMap.getMapId(), resultantDataListForMap);
			//System.out.println("total success : " + totalSuccess);
			
		}
		
		switch (agent) {
		case AGENT1:{
			file = new File(Constants.AGENT1_RESULTANT_DATA_FILE_PATH);
		}
		break;
		case AGENT2: {
			file = new File(Constants.AGENT2_RESULTANT_DATA_FILE_PATH);

		}
		break;
		case AGENT3: {
			file = new File(Constants.AGENT3_RESULTANT_DATA_FILE_PATH);

		}
		break;
		case AGENT4:{
			file = new File(Constants.AGENT4_RESULTANT_DATA_FILE_PATH);

		}
		break;
		case AGENT5:{
			file = new File(Constants.AGENT5_RESULTANT_DATA_FILE_PATH);

		}
		break;
		}
		
		ObjectMapper mapper = new ObjectMapper();

		try {
            mapper.writeValue(file, allResultantData);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return successRate;
	}
	
	public static Boolean moveAgent1(Map validatedMap, int noOfGhosts) {
		int gridSize = validatedMap.getGridSize();
		List<Coordinates> pathToGoal = new ArrayList<>(validatedMap.getFinalPathToGoal());
//		Coordinates origin = new Coordinates(0, 0);
		Coordinates goalState = new Coordinates(gridSize-1, gridSize-1);
	
		Node agent1 = new Node(0,0);
		ArrayList<Node> ghosts = new ArrayList<>();
		List<Coordinates> allReachableNodes = new ArrayList<>(validatedMap.getAllReachableNodes());
		
		/**
		 * This ensures that the initial positions of the ghosts are 
		 * at reachable positions from the agent
		 */
		noOfGhosts = CommonNodeMethods.initializeGhosts(ghosts, allReachableNodes, noOfGhosts);
		
		for(Coordinates path: pathToGoal) {
			
			Boolean agentDead = CommonNodeMethods.ghostEatsAgent(ghosts, agent1);

			if(agentDead) {
				return Boolean.FALSE;
			}
			
			agent1.setCurPosition(new Coordinates(path.getX(), path.getY()));
			
			/**
			 * Update Ghost positions as soon as the ghosts move
			 */
			ghosts = CommonNodeMethods.updateGhostPositions(ghosts, validatedMap);
			
			if(agent1.getCurPosition().equals(goalState)) {
				return Boolean.TRUE;
			}
			
		}
		
		return Boolean.FALSE;
	}
	
	public static Boolean moveAgent2(Map validatedMap, int noOfGhosts) {
		
		List<Coordinates> pathToGoal = new ArrayList<>(validatedMap.getFinalPathToGoal());
		List<Coordinates> actualPathTaken = new ArrayList<>();
		
		Map newMap = new Map(validatedMap);
	
		Node agent2 = new Node(0,0);
		agent2.setPath(pathToGoal);
		actualPathTaken.add(agent2.getCurPosition());
		
		ArrayList<Node> ghosts = new ArrayList<>();
		List<Coordinates> allReachableNodes = new ArrayList<>(newMap.getAllReachableNodes());
		
		noOfGhosts = CommonNodeMethods.initializeGhosts(ghosts, allReachableNodes, noOfGhosts);
		
		Boolean goalReached = Boolean.FALSE;
		CommonTraverseMethods.moveAgent2(agent2, ghosts, newMap, pathToGoal, actualPathTaken, goalReached, null);
		
		if(agent2.getCurPosition().equals(Constants.GOAL_STATE)) {
			goalReached = Boolean.TRUE;
		}
		
		agent2.setActualPathTaken(actualPathTaken);
		
//		System.out.println("Initial Path Taken	: " + validatedMap.getFinalPathToGoal());
//		System.out.println("Actual Path Taken	: " + agent2.getActualPathTaken());
		
		return goalReached;
	}
	
	public static Boolean moveAgent3(Map validatedMap, int noOfGhosts) {
		
		int gridSize = validatedMap.getGridSize();
		
		//I dont need this, since there is no concept of a path in Agent 3
		List<Coordinates> actualPathTaken = new ArrayList<>();
		actualPathTaken.add(new Coordinates(0,0));
		
		Map newMap = new Map(validatedMap);
		System.out.println("moveAgent3: pathForEachNode: ");
		
		HashMap<String, List<Coordinates>> nodeWisePath = new HashMap<>();
		for(Entry<String, List<Coordinates>> pair: validatedMap.getReachableNodeWisePathToGoal().entrySet()) {
//			Coordinates key = CommonlyUsedMethods.getCoordinatesFromString(pair.getKey());			
			nodeWisePath.put(pair.getKey(), new ArrayList<Coordinates>(pair.getValue()));
		}
		newMap.setReachableNodeWisePathToGoal(nodeWisePath);
		for(Entry<String, List<Coordinates>> pair: newMap.getReachableNodeWisePathToGoal().entrySet()) {
			System.out.println(pair.getKey() + " " + pair.getValue());
		}
		
		Coordinates goalState = new Coordinates(gridSize-1, gridSize-1);
	
		Node agent3 = new Node(0,0);
		
		ArrayList<Node> ghosts = new ArrayList<>();
		List<Coordinates> allReachableNodes = new ArrayList<>(newMap.getAllReachableNodes());
		
		noOfGhosts = CommonNodeMethods.initializeGhosts(ghosts, allReachableNodes, noOfGhosts);
		
		Boolean goalReached = Boolean.FALSE;
		
		while(!agent3.getCurPosition().equals(goalState)) {
			
			Coordinates nextMove = new Coordinates();
			
			Boolean agentDead = CommonNodeMethods.ghostEatsAgent(ghosts, agent3);
			
			System.out.println("Is Agent Dead: " + agentDead);			
			if(agentDead) {
				goalReached = Boolean.FALSE;
				break;
			}
			
			System.out.println("GHOST POSITIONS		INITIAL: " + ghosts);
			ArrayList<Node> tempGhosts = CommonlyUsedMethods.createDeepCopy(ghosts);			
			nextMove = new Coordinates(CommonTraverseMethods.getNextMoveFromSimulation(agent3, tempGhosts, 
					newMap));
			System.out.println("GHOST POSITIONS		SAME?  : " + ghosts);
			
			agent3.setCurPosition(nextMove);
			
			System.out.println("Next MOVE: " + nextMove);
			
			actualPathTaken.add(nextMove);
			
			/**
//			 * Update Ghost positions as soon as the agent moves
//			 */
			ghosts = CommonNodeMethods.updateGhostPositions(ghosts, newMap);
			
		}
		
		if(agent3.getCurPosition().equals(goalState)) {
			goalReached = Boolean.TRUE;
			agent3.setActualPathTaken(actualPathTaken);
		}
		System.out.println("Initial Path Taken	: " + validatedMap.getFinalPathToGoal());
		System.out.println("Actual Path Taken	: " + actualPathTaken);
		
		agent3.setActualPathTaken(actualPathTaken);	
		return goalReached;
		
	}
	
	public static boolean moveAgent4(Map validatedMap, int noOfGhosts) {
		return false;
		
		/**
		 * Instead of recalculating the path every time I find a ghost on the path,
		 * We set up a radius of 1/4 the distance to the Goal from the agent's current position.
		 * 
		 * 
		 */
	
	
	}

}
