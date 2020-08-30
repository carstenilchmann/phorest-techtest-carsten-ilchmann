package com.example;

import org.springframework.core.convert.converter.Converter;

import com.example.web.ImportType;

public class CsvTypeConverter implements Converter<String, ImportType> {

	@Override
	public ImportType convert(String source) {
		return ImportType.valueOf(source.toUpperCase());
	}

}
