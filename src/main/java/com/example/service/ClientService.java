package com.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.dto.TopClient;
import com.example.model.Client;
import com.example.repository.ClientRepository;
import com.example.util.CsvUtils;

@Service
public class ClientService {
	private static Logger logger = LoggerFactory.getLogger(ClientService.class);

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

	public void importClients(InputStream stream) throws IOException {
		List<Map<String, String>> rows = CsvUtils.parseCsv(stream);
		for (Map<String, String> row : rows) {
			Client client = new Client(UUID.fromString(row.get("id")));
			client.setFirstName(row.get("first_name"));
			client.setLastName(row.get("last_name"));
			client.setEmail(row.get("email"));
			client.setPhone(row.get("phone"));
			client.setGender(row.get("gender"));
			client.setBanned(Boolean.valueOf(row.get("banned")));
			logger.debug("Saving client: " + client.getId());
			clientRepo.save(client);
		}
	}

	public List<TopClient> getTopClientsSince(Date since, Integer count) {
		return clientRepo.findTopSince(since, PageRequest.of(0, count));
	}
}
