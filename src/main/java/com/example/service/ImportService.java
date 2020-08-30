package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Appointment;
import com.example.model.Client;
import com.example.model.Purchase;
import com.example.model.Service;
import com.example.repository.AppointmentRepository;
import com.example.repository.ClientRepository;
import com.example.repository.PurchaseRepository;
import com.example.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ImportService {
	private static Logger logger = LoggerFactory.getLogger(ImportService.class);

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private AppointmentRepository appointmentRepo;

	@Autowired
	private ServiceRepository serviceRepo;

	@Autowired
	private PurchaseRepository purchaseRepo;

	private List<Map<String, String>> parseCsv(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Map<String, String>> rows = new ArrayList<>();
		String line = reader.readLine();
		if (line != null) {
			String[] header = line.split(",");
			line = reader.readLine();
			while (line != null) {
				String[] cols = line.split(",");
				Map<String, String> row = new HashMap<>();
				for (int i = 0; i < header.length; i++) {
					row.put(header[i], cols[i]);
				}
				rows.add(row);
				line = reader.readLine();
			}
		}
		return rows;
	}

	public void importClients(InputStream stream) throws IOException {
		List<Map<String, String>> rows = parseCsv(stream);
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

	public void importAppointments(InputStream stream) throws IOException, ParseException {
		List<Map<String, String>> rows = parseCsv(stream);
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

	public void importServices(InputStream stream) throws IOException {
		List<Map<String, String>> rows = parseCsv(stream);
		for (Map<String, String> row : rows) {
			Service service = new Service();
			service.setId(UUID.fromString(row.get("id")));
			service.setAppointment(new Appointment(UUID.fromString(row.get("appointment_id"))));
			service.setName(row.get("name"));
			service.setPrice(new BigDecimal(row.get("price")));
			service.setLoyaltyPoints(Integer.valueOf(row.get("loyalty_points")));
			logger.debug("Saving service: " + service.getId());
			serviceRepo.save(service);
		}
	}

	public void importPurchases(InputStream stream) throws IOException {
		List<Map<String, String>> rows = parseCsv(stream);
		for (Map<String, String> row : rows) {
			Purchase purchase = new Purchase();
			purchase.setId(UUID.fromString(row.get("id")));
			purchase.setAppointment(new Appointment(UUID.fromString(row.get("appointment_id"))));
			purchase.setName(row.get("name"));
			purchase.setPrice(new BigDecimal(row.get("price")));
			purchase.setLoyaltyPoints(Integer.valueOf(row.get("loyalty_points")));
			logger.debug("Saving purchase: " + purchase.getId());
			purchaseRepo.save(purchase);
		}
	}
}
