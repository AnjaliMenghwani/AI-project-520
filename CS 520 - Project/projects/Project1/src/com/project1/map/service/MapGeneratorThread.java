package com.project1.map.service;

import com.project1.agent.utility.CommonTraverseMethods;
import com.project1.map.model.Map;

public class MapGeneratorThread implements Runnable {

	MapGeneratorImpl mapGeneratorImpl = new MapGeneratorImpl();
	Map outputMap = new Map();
	
	boolean validationSuccessful = false;
	
	public MapGeneratorThread(MapGeneratorImpl mapGeneratorImpl, Map outputMap, boolean validationSuccessful) {
		
		this.mapGeneratorImpl = mapGeneratorImpl;
		this.outputMap = outputMap;
		this.validationSuccessful = validationSuccessful;
	}
	
	@Override
	public void run() {
		
		mapGeneratorImpl.generate(outputMap);
		if(CommonTraverseMethods.validate(outputMap)) {
			validationSuccessful = true;
		}
		
	}

}
 