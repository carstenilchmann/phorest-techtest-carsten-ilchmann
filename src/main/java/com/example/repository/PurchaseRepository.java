package com.example.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, UUID> {

}
