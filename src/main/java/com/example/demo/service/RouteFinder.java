package com.example.demo.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RouteFinder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RouteFinder.class);
	
	private static Map<?, ?> borderMap;
	
	
	private ResourceLoader resourceLoader;
	
	@Autowired
	public RouteFinder(ResourceLoader resourceLoader) {
		
		String countryData = null;
		Resource resource = resourceLoader.getResource("classpath:countries.json");
		
		try {
			countryData = resource.getContentAsString(StandardCharsets.UTF_8);
		} catch (IOException e) {
			LOGGER.error("Country source file is not found.", e);
		}
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, Object>> countries = null;
		try {
			countries = objectMapper.readValue(countryData, List.class);
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
