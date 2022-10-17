package com.project1.map.utility;

import com.project1.map.model.Coordinates;
import com.project1.map.model.Node;

public class Constants {
	
	public static final int NO_OF_VALID_MAPS = 100;
	public static final int GRIDSIZE = 51;
	public static final Coordinates GOAL_STATE = new Coordinates(GRIDSIZE-1, GRIDSIZE-1); 
	public static final Node GOAL_NODE = new Node(GRIDSIZE-1, GRIDSIZE-1);
	public static final int BLOCKED = 1;
	public static final int UNBLOCKED = 0;
	public static final double PROB_OF_BLOCKED = 0.28;
	
	public static final int NO_OF_GHOSTS = 2;
	public static final int GHOST_MIN_DISTANCE_FROM_AGENT = 4;
	public static final double PROB_OF_GHOST_IN_WALL = 0.5;
	public static final int NO_OF_SIMULATIONS_PER_MOVE_FOR_AGENT_3 = 10;
	
	public static final double PROB_OF_SELECTING_X_OR_Y = 0.5;
	
	public static final String FILE_NAME = "Agent Traversal Data";
	public static final String FILE_PATH = "C:\\Studies and Work\\Rutgers\\Semester 1\\CS-520\\Projects\\Project 1\\Logs\\";
	public static final String ENV_FILE_PATH = "/Users/anjalimenghwani/Desktop/CS 520 - Project/ENV/Map Data.json";
	
	public static final String AGENT1_RESULTANT_DATA_FILE_PATH = "/Users/anjalimenghwani/Desktop/CS 520 - Project/Result/resultantDataForAgent1.json";
	public static final String AGENT2_RESULTANT_DATA_FILE_PATH = "/Users/anjalimenghwani/Desktop/CS 520 - Project/Result/resultantDataForAgent2.json";
	public static final String AGENT3_RESULTANT_DATA_FILE_PATH = "/Users/anjalimenghwani/Desktop/CS 520 - Project/Result/resultantDataForAgent3.json";
	public static final String AGENT4_RESULTANT_DATA_FILE_PATH = "/Users/anjalimenghwani/Desktop/CS 520 - Project/Result/resultantDataForAgent4.json";
	public static final String AGENT5_RESULTANT_DATA_FILE_PATH = "/Users/anjalimenghwani/Desktop/CS 520 - Project/Result/resultantDataForAgent5.json";

	public static final String AGENT_1_DATA_FILE_PATH = "C:\\Studies and Work\\Rutgers\\Semester 1\\CS-520\\Projects\\Project 1\\DATA\\AGENT_1_Data.json";
	public static final String AGENT_2_DATA_FILE_PATH = "C:\\Studies and Work\\Rutgers\\Semester 1\\CS-520\\Projects\\Project 1\\DATA\\AGENT_2_Data.json";
	public static final String AGENT_3_DATA_FILE_PATH = "C:\\Studies and Work\\Rutgers\\Semester 1\\CS-520\\Projects\\Project 1\\DATA\\AGENT_3_Data.json";
	public static final String AGENT_4_DATA_FILE_PATH = "C:\\Studies and Work\\Rutgers\\Semester 1\\CS-520\\Projects\\Project 1\\DATA\\AGENT_4_Data.json";
	public static final String[] HEADER = {"Agent", "Map", "No. of Ghosts", "Destination Reached"};
}
