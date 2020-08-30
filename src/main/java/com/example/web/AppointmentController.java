package com.example.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Appointment;
import com.example.service.AppointmentService;

@RestController
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/appointment/{appointmentId}")
	public Appointment getAppointment(@PathVariable UUID appointmentId) {
		return appointmentService.getAppointment(appointmentId);
	}

	@DeleteMapping("/appointment/{appointmentId}")
	public ResponseEntity<Void> deleteAppointment(@PathVariable UUID appointmentId) {
		appointmentService.deleteAppointment(appointmentId);
		return ResponseEntity.noContent().build();
	}

}
