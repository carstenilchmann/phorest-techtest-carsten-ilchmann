package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Appointment;
import com.example.repository.AppointmentRepository;

@Service
public class AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepo;

	public Appointment getAppointment(UUID id) {
		return appointmentRepo.findById(id).orElseThrow();
	}

	public void deleteAppointment(UUID id) {
		appointmentRepo.deleteById(id);
	}
}
