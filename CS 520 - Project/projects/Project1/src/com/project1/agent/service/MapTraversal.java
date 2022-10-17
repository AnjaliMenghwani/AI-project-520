package com.project1.agent.service;

import java.util.HashMap;
import java.util.List;

import com.project1.map.model.Map;
import com.project1.map.utility.Agent;

public interface MapTraversal {
	
	public HashMap<Map, Boolean> Agent1(List<Map> generatedMaps);
	
	public HashMap<Map, Boolean> Agent2(List<Map> generatedMaps);
	
	public HashMap<Map, Boolean> Agent3(List<Map> generatedMaps);
	
	public HashMap<Map, Boolean> Agent4(List<Map> generatedMaps);

	public HashMap<Map, Boolean> moveAgent(List<Map> validatedMaps, Agent agent);

}
