package com.example.web;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dto.TopClient;
import com.example.model.Client;
import com.example.service.ClientService;

@RestController
public class ClientController {
	@Autowired
	private ClientService clientService;

	@PostMapping("/client")
	public ResponseEntity<Void> createClient(@RequestBody Client client) {
		Client newClient = clientService.createClient(client);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				                                  .path("/{id}")
				                                  .buildAndExpand(newClient.getId())
				                                  .toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/client/{clientId}")
	public Client getClient(@PathVariable UUID clientId) {
		return clientService.getClient(clientId);
	}

	@DeleteMapping("/client/{clientId}")
	public ResponseEntity<Void> deleteClient(@PathVariable UUID clientId) {
		clientService.deleteClient(clientId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/client/{clientId}")
	public Client replaceClient(@PathVariable UUID clientId, @RequestBody Client client) {
		return clientService.replaceClient(clientId, client);
	}

	@GetMapping("/topclients")
	public List<TopClient> getTopClients(@RequestParam Integer count, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date since) {
		return clientService.getTopClientsSince(since, count);
	}

}
