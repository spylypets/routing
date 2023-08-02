package com.example.demo.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RouteFinder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RouteFinder.class);
	
	private static Map<?, ?> borderMap;
	
	public RouteFinder() {
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(Paths.get("countries.json"), StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			LOGGER.error("Country source file is not found.", e);
		}
		String content = scanner.useDelimiter("\\A").next();
		scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, Object>> countries = null;
		try {
			countries = objectMapper.readValue(content, List.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        borderMap = countries.stream().map(c -> {return Map.entry(c.get("cca3"), c.get("borders"));}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	public List<String> find(String origin, String destination) {
		
		List<String> result = find(origin, destination, new ArrayList<String>());
		return result;
		//return result != null && result.contains(destination) ? result : null; Now we check the result in the controller
	}

	public static void main(String[] args) {

		RouteFinder finder = new RouteFinder();
		List<String> route = finder.find("CZE", "ITA");
		LOGGER.info("Found route: " + route);
	}
	
	private List<String> find(String country, String destination, List<String> route) {

		LOGGER.debug("country: " + country);
		LOGGER.debug("route: " + route.toString());
		List<String> curRoute = new ArrayList<String>(route);
		curRoute.add(country);
		if(!destination.equals(country)){
			List<String> countryBorders = (List<String>) borderMap.get(country);
			if (countryBorders == null) {
				return null;
			} else if(countryBorders.contains(destination)) {
				curRoute.add(destination);
				return curRoute;
			}
			for(String border : countryBorders) {
				
				if(curRoute.contains(border)) continue;
				curRoute = find(border, destination, new ArrayList<String>(curRoute));
				if(curRoute.contains(destination)) break;
			}
		}
		return curRoute;
	}
}
