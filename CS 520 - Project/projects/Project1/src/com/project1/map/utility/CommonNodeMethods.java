package com.project1.map.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.project1.agent.service.MapTraversalImpl;
import com.project1.map.model.Coordinates;
import com.project1.map.model.Map;
import com.project1.map.model.Node;

public class CommonNodeMethods {
	
	private static final Logger logger = Logger.getLogger(MapTraversalImpl.class
            .getName());
    private FileHandler fh = null;
	
	public static Random randVar = new Random();
	
	public CommonNodeMethods() {
		//just to make our log file nicer :)
        SimpleDateFormat format = new SimpleDateFormat("MMM-d_HHmmss");
        try {
            fh = new FileHandler(Constants.FILE_PATH
                + format.format(Calendar.getInstance().getTime()) + ".log");
        } catch (Exception e) {
            e.printStackTrace();
        }

        fh.setFormatter(new Formatter() {
			
        	@Override
            public String format(LogRecord record) {
                SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(record.getMillis());
                return record.getLevel()
                        + logTime.format(cal.getTime())
                        + " || "
                        + record.getSourceClassName().substring(
                                record.getSourceClassName().lastIndexOf(".")+1,
                                record.getSourceClassName().length())
                        + "."
                        + record.getSourceMethodName()
                        + "() : "
                        + record.getMessage() + "\n";
            }
        });
            
        logger.addHandler(fh);
	}
	
	public static Boolean ghostEatsAgent(List<Node> ghosts, Node agent) {
		for(Node ghost: ghosts) {
			if(ghost.getCurPosition().equals(agent.getCurPosition())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
//	public static List<Coordinates> updateAgentGoalPath(Node agent, ArrayList<Node> ghosts) {
//		
//		
//		List<Coordinates> newGoalPathForAgent = CommonTraverseMethods.findThePathToGoal(agent, null, null, null);
//		
//		
//		return null;
//	}
	
	public static ArrayList<Node> updateGhostPositions(ArrayList<Node> ghosts, Map mapData) {
		int[][] map = mapData.getGrid();
		
		for(Node ghost: ghosts) {
//			System.out.println("INITIAL Ghost-> " + " X: " + ghost.getCurPosition().getX() + " Y: " + ghost.getCurPosition().getY());
			
			ArrayList<Node> ghostNextPossiblePositions = getValidGhostChildren(ghost);
			int randomIndx = randVar.nextInt(ghostNextPossiblePositions.size());
			Node ghostNextPosition = ghostNextPossiblePositions.get(randomIndx);
			
//			System.out.println("Ghost CHOICE OF MOVE -> " + " X: " + ghostNextPosition.getCurPosition().getX() + " Y: " + ghostNextPosition.getCurPosition().getY());
//			System.out.println("MAP VALUE FOR GHOST CHOICE: " + map[ghostNextPosition.getCurPosition().getX()][ghostNextPosition.getCurPosition().getY()]);
			/**
			 * if the probability is less than 0.5 then the ghosts do not enter the new position which is the wall
			 */
			if(map[ghostNextPosition.getCurPosition().getX()][ghostNextPosition.getCurPosition().getY()] == Constants.BLOCKED) {
				double probOfGhostInWall = randVar.nextDouble();
//				System.out.println("Probability of Going into the wall: " + probOfGhostInWall);
				if(probOfGhostInWall > Constants.PROB_OF_GHOST_IN_WALL) {
//					System.out.println("GHOST INSIDE WALL");
					ghost.setCurPosition(ghostNextPosition.getCurPosition());
				}
			}
			else {
				ghost.setCurPosition(ghostNextPosition.getCurPosition());
			}
			
//			System.out.println("UPDATED Ghost -> " + " X: " + ghost.getCurPosition().getX() + " Y: " + ghost.getCurPosition().getY());	
//			System.out.println();
		}
		
		return ghosts;
	}
	
	public static ArrayList<Node> getValidGhostChildren(Node ghost) {
		ArrayList<Node> ghostChildren = new ArrayList<>();
		Coordinates currPos = new Coordinates(ghost.getCurPosition().getX(), ghost.getCurPosition().getY());
		int x = currPos.getX();
		int y = currPos.getY();
		
		if(x - 1 >= 0) {
			ghostChildren.add(new Node(x-1, y));
		}
		if(x + 1 < Constants.GRIDSIZE) {
			ghostChildren.add(new Node(x+1, y));
		}
		if(y - 1 >= 0) {
			ghostChildren.add(new Node(x, y-1));
		}
		if(y + 1 < Constants.GRIDSIZE) {
			ghostChildren.add(new Node(x, y+1));
		}
		
		return ghostChildren;
	}
	
	public static int initializeGhosts(ArrayList<Node> ghosts, List<Coordinates> allReachableNodes, int totalNoOfGhosts) {
		
		if(totalNoOfGhosts == 0) {
			return 0;
		}
		int noOfGhosts = 0;
		while(noOfGhosts  < totalNoOfGhosts) {
			int randomIndx = randVar.nextInt(allReachableNodes.size());
			Coordinates ghostPosition = allReachableNodes.get(randomIndx) != new Coordinates(0, 0) ? 
					allReachableNodes.get(randomIndx) : allReachableNodes.get(randVar.nextInt(allReachableNodes.size()));
			Node ghost = new Node(ghostPosition.getX(), ghostPosition.getY());
			ghosts.add(ghost);
			noOfGhosts++;
		}	
		return noOfGhosts;
	}
	
	public static ArrayList<Coordinates> getGhostPosition(ArrayList<Node> ghosts) {
		
		ArrayList<Coordinates> ghostPositions = new ArrayList<>();
		if(ghosts != null && !ghosts.isEmpty()) {
			for(Node ghost: ghosts) {
				Coordinates gPos = new Coordinates(ghost.getCurPosition().getX(), ghost.getCurPosition().getY());
				ghostPositions.add(gPos);
			}
			return ghostPositions;
		}
		return new ArrayList<>();		
	}

	public static ArrayList<Node> findAllVisibleGhosts(ArrayList<Node> ghosts, int[][] mapGrid) {
		// TODO Auto-generated method stub
		ArrayList<Node> visibleGhosts = new ArrayList<>();
		for(Node ghost: ghosts) {
			Coordinates gPos = ghost.getCurPosition();
			if(mapGrid[gPos.getX()][gPos.getY()] == Constants.UNBLOCKED) {
				visibleGhosts.add(ghost);
			}
		}
		return visibleGhosts;
	}
	
	public static ArrayList<Node> getValidNodeChildren(Node agent, int[][] mapGrid) {
		ArrayList<Node> nodeChildren = new ArrayList<>();
		Coordinates currPos = new Coordinates(agent.getCurPosition().getX(), agent.getCurPosition().getY());
		int x = currPos.getX();
		int y = currPos.getY();
		
		//This adds my current position as a possible move to be taken
		nodeChildren.add(new Node(x,y));
		
		if(x - 1 >= 0 && mapGrid[x-1][y] == Constants.UNBLOCKED) {
			nodeChildren.add(new Node(x-1, y));
		}
		if(x + 1 < Constants.GRIDSIZE && mapGrid[x + 1][y] == Constants.UNBLOCKED) {
			nodeChildren.add(new Node(x+1, y));
		}
		if(y - 1 >= 0 && mapGrid[x][y - 1] == Constants.UNBLOCKED) {
			nodeChildren.add(new Node(x, y-1));
		}
		if(y + 1 < Constants.GRIDSIZE && mapGrid[x][y + 1] == Constants.UNBLOCKED) {
			nodeChildren.add(new Node(x, y+1));
		}
		
		return nodeChildren;
	}
	
	public static Boolean compareCurrPosWithGhostPositions(int x, int y, ArrayList<Coordinates> ghostPositions) {
		if(ghostPositions.isEmpty()) {
			return Boolean.FALSE;
		}
		
		for(Coordinates gPos: ghostPositions) {
			if(gPos.getX() == x && gPos.getY() == y) {
				return Boolean.TRUE;
			}
		}	
		return Boolean.FALSE;
	}

}
