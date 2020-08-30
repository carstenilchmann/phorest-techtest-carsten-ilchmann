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

import com.example.service.ImportService;

@RestController
public class CsvImportController {
	private static Logger logger = LoggerFactory.getLogger(CsvImportController.class);

	@Autowired
	private ImportService importService;

	@PostMapping("/import_csv")
	public ResponseEntity<Void> importCsv(@RequestParam(value = "type") ImportType type,
			                              @RequestParam(value = "data") MultipartFile file) throws IOException, ParseException {
		logger.info("importing CSV");
		logger.info("type: " + type);
		switch (type) {
		case CLIENT:
			importService.importClients(file.getInputStream());
			break;
		case APPOINTMENT:
			importService.importAppointments(file.getInputStream());
			break;
		case SERVICE:
			importService.importServices(file.getInputStream());
			break;
		case PURCHASE:
			importService.importPurchases(file.getInputStream());
		}
		return ResponseEntity.noContent().build();
	}
}
