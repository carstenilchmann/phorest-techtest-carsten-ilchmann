package com.example.web;

import java.io.IOException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.AppointmentService;
import com.example.service.ClientService;
import com.example.service.PurchaseService;
import com.example.service.ServiceService;

@RestController
public class CsvImportController {
	private static Logger logger = LoggerFactory.getLogger(CsvImportController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private ServiceService serviceService;

	@Autowired
	private PurchaseService purchaseService;

	@PostMapping("/import_csv")
	public ResponseEntity<Void> importCsv(@RequestParam(value = "type") ImportType type,
			                              @RequestParam(value = "data") MultipartFile file) throws IOException, ParseException {
		logger.info("Importing CSV");
		logger.info("type: " + type);
		switch (type) {
		case CLIENT:
			clientService.importClients(file.getInputStream());
			break;
		case APPOINTMENT:
			appointmentService.importAppointments(file.getInputStream());
			break;
		case SERVICE:
			serviceService.importServices(file.getInputStream());
			break;
		case PURCHASE:
			purchaseService.importPurchases(file.getInputStream());
		}
		return ResponseEntity.noContent().build();
	}
}
