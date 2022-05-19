package com.etiya.onlineWeatherInquiry.core.utilities.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
	ModelMapper forRequest();
	ModelMapper forDto();
}
