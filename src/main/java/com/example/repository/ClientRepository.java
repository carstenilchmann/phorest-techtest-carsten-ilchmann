package com.example.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dto.TopClient;
import com.example.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {
	@Query("select new com.example.dto.TopClient(c.id, c.firstName, c.lastName, c.email, c.phone, c.gender, sum(s.loyaltyPoints) + sum(p.loyaltyPoints) as totalLoyalty) "
			+ "from Client c "
			+ "join c.appointments a "
			+ "join a.services s "
			+ "left join a.purchases p "
			+ "where a.startTime > :since "
			+ "group by c.id, c.firstName, c.lastName, c.email, c.phone, c.gender "
			+ "order by totalLoyalty desc, c.id asc")
	List<TopClient> findTopSince(@Param("since") Date since, Pageable pageable);
}
