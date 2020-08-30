package com.example.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Service;
import com.example.service.ServiceService;

@RestController
public class ServiceController {
	@Autowired
	private ServiceService serviceService;

	@GetMapping("/service/{serviceId}")
	public Service getAppointment(@PathVariable UUID serviceId) {
		return serviceService.getService(serviceId);
	}

	@DeleteMapping("/service/{serviceId}")
	public ResponseEntity<Void> deleteService(@PathVariable UUID serviceId) {
		serviceService.deleteService(serviceId);
		return ResponseEntity.noContent().build();
	}

}
