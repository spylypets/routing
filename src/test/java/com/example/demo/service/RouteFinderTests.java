package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RouteFinderTests {
	
	private RouteFinder finder = new RouteFinder();
	
	@Test
	public void testRouteExists() {
		List<String> route = finder.find("CZE", "UKR");
		assertNotNull(route);
		assertTrue(route.contains("CZE"));
		assertTrue(route.contains("UKR"));
	}
	
	@Test
	public void testRouteNotExists() {
		List<String> route = finder.find("CZE", "Nowhere");
		assertNotNull(route);
		assertTrue(route.contains("CZE"));
		assertFalse(route.contains("Nowhere"));
	}
	
	@Test
	public void testDestinationIsStart() {
		List<String> route = finder.find("CZE", "CZE");
		assertNotNull(route);
		assertTrue(route.contains("CZE"));
		assertTrue(route.size() == 1);
	}
	
	@Test
	public void testWrongStartParameter() {
		List<String> route = finder.find("CZK", "Nowhere");
		assertNull(route);
	}

}
