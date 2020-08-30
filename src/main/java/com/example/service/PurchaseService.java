package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Purchase;
import com.example.repository.PurchaseRepository;

@Service
public class PurchaseService {
	@Autowired
	private PurchaseRepository purchaseRepo;

	public Purchase getPurchase(UUID id) {
		return purchaseRepo.findById(id).orElseThrow();
	}

	public void deletePurchase(UUID id) {
		purchaseRepo.deleteById(id);
	}

}
