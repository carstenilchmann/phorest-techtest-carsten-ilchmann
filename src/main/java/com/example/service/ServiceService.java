package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Service;
import com.example.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ServiceService {
	@Autowired
	private ServiceRepository serviceRepo;

	public Service getService(UUID id) {
		return serviceRepo.findById(id).orElseThrow();
	}

	public void deleteService(UUID id) {
		serviceRepo.deleteById(id);
	}
}
