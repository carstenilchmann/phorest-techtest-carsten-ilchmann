package com.example.web;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.TopClient;
import com.example.model.Client;
import com.example.service.ClientService;

@RestController
public class ClientController {
	@Autowired
	private ClientService clientService;

	@GetMapping("/client/{clientId}")
	public Client getClient(@PathVariable UUID clientId) {
		return clientService.getClient(clientId);
	}
	
	@GetMapping("/topclients")
	public List<TopClient> getTopClients(@RequestParam Integer count, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date since) {
		return clientService.getTopClientsSince(since, count);
	}

}
