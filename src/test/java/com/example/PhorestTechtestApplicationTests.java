package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
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
import com.example.web.ImportType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PhorestTechtestApplicationTests {
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}

	@Test
	void unknownClientReturns404() {
		ResponseEntity<Client> clientsResponse = restTemplate.getForEntity("http://localhost:" + port + "/client/936a1aba-dead-beef-9872-06ff2fec8ab0", Client.class);
		assertEquals(HttpStatus.NOT_FOUND, clientsResponse.getStatusCode());
	}

	void loadCsv(ImportType type, String filename) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		ClassPathResource inputFile = new ClassPathResource(filename);
		body.add("data", inputFile);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv?type=" + type.toString().toLowerCase(), requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void uploadCsvsAndGetTopClients() {
		loadCsv(ImportType.CLIENT, "clients.csv");
		loadCsv(ImportType.APPOINTMENT, "appointments.csv");
		loadCsv(ImportType.SERVICE, "services.csv");
		loadCsv(ImportType.PURCHASE, "purchases.csv");

		// top 10 clients
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

		// top 50 clients
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

	@Test
	void crudClient() {
		// POST
		UUID id = UUID.randomUUID();
		Client newClient = new Client(id);
		newClient.setFirstName("John");
		newClient.setLastName("Doe");
		newClient.setEmail("john.doe@example.com");
		newClient.setGender("Male");
		newClient.setPhone("555-12345");
		newClient.setBanned(false);
		URI uri = restTemplate.postForLocation("http://localhost:" + port + "/client", newClient);
		UUID returnedId = UUID.fromString(uri.toString().substring(uri.toString().length() - 36));
		assertEquals(id, returnedId);
		// GET
		ResponseEntity<Client> clientsResponse = restTemplate.getForEntity(uri, Client.class);
		assertEquals(HttpStatus.OK, clientsResponse.getStatusCode());
		Client client = clientsResponse.getBody();
		assertEquals(id, client.getId());
		assertEquals("John", client.getFirstName());
		assertEquals("Doe", client.getLastName());
		assertEquals("john.doe@example.com", client.getEmail());
		// PUT
		newClient.setEmail("new_email@example.com");
		newClient.setPhone(null);
		restTemplate.put(uri, newClient);
		// GET
		clientsResponse = restTemplate.getForEntity(uri, Client.class);
		assertEquals(HttpStatus.OK, clientsResponse.getStatusCode());
		client = clientsResponse.getBody();
		assertEquals(id, client.getId());
		assertEquals("John", client.getFirstName());
		assertEquals("Doe", client.getLastName());
		assertEquals("new_email@example.com", client.getEmail());
		assertNull(client.getPhone());
		// DELETE
		restTemplate.delete(uri);
		// GET
		clientsResponse = restTemplate.getForEntity(uri, Client.class);
		assertEquals(HttpStatus.NOT_FOUND, clientsResponse.getStatusCode());
	}

	@Test
	void badClientPost() {
		URI uri = restTemplate.postForLocation("http://localhost:" + port + "/client", "garbage");
		assertNull(uri);
	}

	@Test
	void badCsvImportPostMissingTypeAndFile() {
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/import_csv", null, Void.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

}

