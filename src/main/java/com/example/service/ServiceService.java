package com.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Appointment;
import com.example.model.Service;
import com.example.repository.ServiceRepository;
import com.example.util.CsvUtils;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService {
	private static Logger logger = LoggerFactory.getLogger(ServiceService.class);

	@Autowired
	private ServiceRepository serviceRepo;

	public Service getService(UUID id) {
		return serviceRepo.findById(id).orElseThrow();
	}

	public void deleteService(UUID id) {
		serviceRepo.deleteById(id);
	}
	
	public void importServices(InputStream stream) throws IOException {
		List<Map<String, String>> rows = CsvUtils.parseCsv(stream);
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

}
