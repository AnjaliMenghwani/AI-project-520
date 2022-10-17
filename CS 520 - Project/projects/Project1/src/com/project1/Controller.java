package com.project1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.agent.service.MapTraversalImpl;
import com.project1.map.DTO.MapReaderDTO;
import com.project1.map.model.Map;
import com.project1.map.service.MapGeneratorImpl;
import com.project1.map.utility.CommonlyUsedMethods;
import com.project1.map.utility.Constants;

public class Controller {

	Constants constants = new Constants();

	public static void main(String args[]) {

		
		/**
		 * Reading the input file for the list of maps and their respective paths
		 */
		
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(Constants.ENV_FILE_PATH);
		MapReaderDTO mapReader = new MapReaderDTO();
		
		try {
			String content = Files.readString(file.toPath());
			mapper = new ObjectMapper();
			
			mapReader = mapper.readValue(content, MapReaderDTO.class); 
		} catch (IOException e) {
		
			e.printStackTrace(); 
		}
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in); 
		MapTraversalImpl traverse = new MapTraversalImpl();
		do
		{
			System.out.println("Enter Agent number: 1,2,3,4,5 or 0 to generate new maps");
			int agent = scanner.nextInt();
			switch(agent)
			{
			case 0: {
				MapGeneratorImpl gen = new MapGeneratorImpl();
				gen.generateMap();
			}
			break;
			case 1: {
				HashMap<Map, Boolean> Agent1 = traverse.Agent1(mapReader.getValidatedMap());
				System.out.println("---------------------------AGENT 1 Complete--------------------------------------------------");
				Agent1.forEach((k,v) -> {
					System.out.println("Map: " + CommonlyUsedMethods.Array2DToString(k.getGrid()) + " T/F: " + v);
				});
				System.out.println();
			}
				break;
			case 2: {
				System.out.println("---------------------------AGENT 2 Complete--------------------------------------------------");
				HashMap<Map, Boolean> Agent2 = traverse.Agent2(mapReader.getValidatedMap());
				System.out.println();
				Agent2.forEach((k,v) -> {
					System.out.println("Map: " + CommonlyUsedMethods.Array2DToString(k.getGrid()) + " T/F: " + v);
				});
				System.out.println();
			}
				break;
			case 3: {
				System.out.println("---------------------------AGENT 3 Complete--------------------------------------------------");
				HashMap<Map, Boolean> Agent3 = traverse.Agent3(mapReader.getValidatedMap());
				System.out.println();
				Agent3.forEach((k,v) -> {
					System.out.println("Map: " + CommonlyUsedMethods.Array2DToString(k.getGrid()) + " T/F: " + v);
				});
			}
				break;
			case 4: {
			}
				break;
			}
		}
		while(true);

	}

}
