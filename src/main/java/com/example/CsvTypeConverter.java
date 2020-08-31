package com.example;

import java.util.Locale;

import org.springframework.core.convert.converter.Converter;

import com.example.web.ImportType;

public class CsvTypeConverter implements Converter<String, ImportType> {

	@Override
	public ImportType convert(String source) {
		return ImportType.valueOf(source.toUpperCase(Locale.US));
	}

}
