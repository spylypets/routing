package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RouteFinder;

@RestController
public class RouteController {
	
	private RouteFinder routeFinder;
	
	@Autowired
	public RouteController(RouteFinder routeFinder) {
		this.routeFinder = routeFinder;
	}
	
	@GetMapping("/routing/{origin}/{destination}")
	public ResponseEntity<List<String>> findRoute(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {
		
		List<String> result = routeFinder.find(origin, destination);
		if(result != null && result.contains(destination)) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
