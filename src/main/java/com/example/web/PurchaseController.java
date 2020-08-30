package com.example.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Purchase;
import com.example.service.PurchaseService;

@RestController
public class PurchaseController {
	@Autowired
	private PurchaseService purchaseService;

	@GetMapping("/purchase/{purchaseId}")
	public Purchase getPurchase(@PathVariable UUID purchaseId) {
		return purchaseService.getPurchase(purchaseId);
	}

	@DeleteMapping("/purchase/{purchaseId}")
	public ResponseEntity<Void> deletePurchase(@PathVariable UUID purchaseId) {
		purchaseService.deletePurchase(purchaseId);
		return ResponseEntity.noContent().build();
	}

}
