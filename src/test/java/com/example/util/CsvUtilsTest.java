package com.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CsvUtilsTest {

	@Test
	void parseCsv() throws IOException {
		String csvInput = "header1,header2\nvalue1,value2\nanother_value1,another_value2\n";
		InputStream is = new ByteArrayInputStream(csvInput.getBytes());
		List<Map<String, String>> rows = CsvUtils.parseCsv(is);
		assertEquals(2, rows.size());
		assertEquals("value1", rows.get(0).get("header1"));
		assertEquals("value2", rows.get(0).get("header2"));
		assertEquals("another_value1", rows.get(1).get("header1"));
		assertEquals("another_value2", rows.get(1).get("header2"));
	}
}
