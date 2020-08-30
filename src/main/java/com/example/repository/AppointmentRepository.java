package com.example.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Appointment;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {

}
