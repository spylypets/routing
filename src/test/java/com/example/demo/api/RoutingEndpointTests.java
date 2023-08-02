package com.example.demo.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RoutingEndpointTests {
	
	
	@Autowired
	private MockMvc mockMvc;
		
	@Test
	public void testFindRoute() throws Exception {
		
		mockMvc.perform(get("/routing/CZE/ITA"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testRouteNotFound() throws Exception {
		
		mockMvc.perform(get("/routing/CZE/Nowhere"))
		.andExpect(status().isBadRequest());
	}

}
