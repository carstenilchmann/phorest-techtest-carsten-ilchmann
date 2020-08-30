package com.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Appointment;
import com.example.model.Client;
import com.example.repository.AppointmentRepository;
import com.example.util.CsvUtils;

@Service
@Transactional
public class AppointmentService {
	private static Logger logger = LoggerFactory.getLogger(AppointmentService.class);

	@Autowired
	private AppointmentRepository appointmentRepo;

	public Appointment getAppointment(UUID id) {
		return appointmentRepo.findById(id).orElseThrow();
	}

	public void deleteAppointment(UUID id) {
		appointmentRepo.deleteById(id);
	}
	
	public void importAppointments(InputStream stream) throws IOException, ParseException {
		List<Map<String, String>> rows = CsvUtils.parseCsv(stream);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		for (Map<String, String> row : rows) {
			Appointment appointment = new Appointment(UUID.fromString(row.get("id")));
			appointment.setClient(new Client(UUID.fromString(row.get("client_id"))));
			appointment.setStartTime(sdf.parse(row.get("start_time")));
			appointment.setEndTime(sdf.parse(row.get("end_time")));
			logger.debug("Saving appointment: " + appointment.getId());
			appointmentRepo.save(appointment);
		}
	}

}
