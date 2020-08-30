package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {
	public static List<Map<String, String>> parseCsv(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Map<String, String>> rows = new ArrayList<>();
		String line = reader.readLine();
		if (line != null) {
			String[] header = line.split(",");
			line = reader.readLine();
			while (line != null) {
				String[] cols = line.split(",");
				Map<String, String> row = new HashMap<>();
				for (int i = 0; i < header.length; i++) {
					row.put(header[i], cols[i]);
				}
				rows.add(row);
				line = reader.readLine();
			}
		}
		return rows;
	}

}
