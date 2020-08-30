package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.dto.TopClient;
import com.example.model.Client;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PhorestTechtestApplicationTests {
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}

	@Test
	void importClientsAndCheck() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		ClassPathResource inputFile = new ClassPathResource("clients.csv");
		body.add("data", inputFile);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv?type=client", requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		ResponseEntity<Client> clientsResponse = restTemplate.getForEntity("http://localhost:" + port + "/client/936a1aba-a8b2-4d3e-9872-06ff2fec8ab0", Client.class);
		assertEquals(HttpStatus.OK, clientsResponse.getStatusCode());
		Client client = clientsResponse.getBody(); 
		assertEquals(UUID.fromString("936a1aba-a8b2-4d3e-9872-06ff2fec8ab0"), client.getId());
		assertEquals("Katelyn", client.getFirstName());
		assertEquals("Hartmann", client.getLastName());
		assertEquals("chang@blick.net", client.getEmail());
		assertEquals("504.459.2434", client.getPhone());
		assertEquals("Male", client.getGender());
		assertFalse(client.getBanned());
	}

	@Test
	void unknownClientReturns404() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		ClassPathResource inputFile = new ClassPathResource("clients.csv");
		body.add("data", inputFile);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv?type=client", requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		ResponseEntity<Client> clientsResponse = restTemplate.getForEntity("http://localhost:" + port + "/client/936a1aba-dead-beef-9872-06ff2fec8ab0", Client.class);
		assertEquals(HttpStatus.NOT_FOUND, clientsResponse.getStatusCode());
	}

	@Test
	void uploadCsvsAndGetTopClients() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		ClassPathResource inputFile = new ClassPathResource("clients.csv");
		body.add("data", inputFile);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv?type=client", requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		body.clear();
		inputFile = new ClassPathResource("appointments.csv");
		body.add("data", inputFile);
		response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv?type=appointment", requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		body.clear();
		inputFile = new ClassPathResource("services.csv");
		body.add("data", inputFile);
		response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv?type=service", requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		body.clear();
		inputFile = new ClassPathResource("purchases.csv");
		body.add("data", inputFile);
		response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv?type=purchase", requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		ResponseEntity<TopClient[]> topClientsresponse = restTemplate.getForEntity("http://localhost:" + port + "/topclients?count=10&since=2018-01-01", TopClient[].class);
		assertEquals(HttpStatus.OK, topClientsresponse.getStatusCode());
		TopClient[] topClients = topClientsresponse.getBody(); 
		assertEquals(10, topClients.length);
		assertEquals("Tinisha", topClients[0].getFirstName());
		assertEquals("Roob", topClients[0].getLastName());
		assertEquals(730, topClients[0].getTotalLoyaltyPoints());
		assertEquals("Roberto", topClients[9].getFirstName());
		assertEquals("Turner", topClients[9].getLastName());
		assertEquals(505, topClients[9].getTotalLoyaltyPoints());

		topClientsresponse = restTemplate.getForEntity("http://localhost:" + port + "/topclients?count=50&since=2018-01-01", TopClient[].class);
		assertEquals(HttpStatus.OK, topClientsresponse.getStatusCode());
		topClients = topClientsresponse.getBody(); 
		assertEquals(50, topClients.length);
		assertEquals("Tinisha", topClients[0].getFirstName());
		assertEquals("Roob", topClients[0].getLastName());
		assertEquals(730, topClients[0].getTotalLoyaltyPoints());
		assertEquals("Roberto", topClients[9].getFirstName());
		assertEquals("Turner", topClients[9].getLastName());
		assertEquals(505, topClients[9].getTotalLoyaltyPoints());
		assertEquals("Angelic", topClients[49].getFirstName());
		assertEquals("Rosenbaum", topClients[49].getLastName());
		assertEquals(150, topClients[49].getTotalLoyaltyPoints());
	}

}

