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
import org.springframework.stereotype.Service;

import com.example.model.Appointment;
import com.example.model.Purchase;
import com.example.repository.PurchaseRepository;
import com.example.util.CsvUtils;

@Service
public class PurchaseService {
	private static Logger logger = LoggerFactory.getLogger(PurchaseService.class);

	@Autowired
	private PurchaseRepository purchaseRepo;

	public Purchase getPurchase(UUID id) {
		return purchaseRepo.findById(id).orElseThrow();
	}

	public void deletePurchase(UUID id) {
		purchaseRepo.deleteById(id);
	}

	public void importPurchases(InputStream stream) throws IOException {
		List<Map<String, String>> rows = CsvUtils.parseCsv(stream);
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
