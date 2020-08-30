package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.example.dto.TopClient;
import com.example.model.Client;
import com.example.repository.ClientRepository;

@org.springframework.stereotype.Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepo;

	public Client getClient(UUID id) {
		return clientRepo.findById(id).orElseThrow();
	}

	public List<TopClient> getTopClientsSince(Date since, Integer count) {
		return clientRepo.findTopSince(since, PageRequest.of(0, count));
	}
}
