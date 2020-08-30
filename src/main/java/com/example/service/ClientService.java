package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.dto.TopClient;
import com.example.model.Client;
import com.example.repository.ClientRepository;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepo;

	public Client createClient(Client client) {
		if (client.getId() == null) {
			client.setId(UUID.randomUUID());
		}
		clientRepo.save(client);
		return client;
	}

	public Client getClient(UUID id) {
		return clientRepo.findById(id).orElseThrow();
	}

	public void deleteClient(UUID id) {
		clientRepo.deleteById(id);
	}

	public Client replaceClient(UUID id, Client client) {
		Client updatedClient = clientRepo.findById(id).orElseThrow();
		updatedClient.setFirstName(client.getFirstName());
		updatedClient.setLastName(client.getLastName());
		updatedClient.setEmail(client.getEmail());
		updatedClient.setGender(client.getGender());
		updatedClient.setPhone(client.getPhone());
		updatedClient.setBanned(client.getBanned());
		clientRepo.save(updatedClient);
		return updatedClient;
	}

	public List<TopClient> getTopClientsSince(Date since, Integer count) {
		return clientRepo.findTopSince(since, PageRequest.of(0, count));
	}
}
