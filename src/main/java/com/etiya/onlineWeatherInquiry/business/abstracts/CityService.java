package com.etiya.onlineWeatherInquiry.business.abstracts;

import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.CreateCityRequest;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.DeleteCityRequest;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.UpdateCityRequest;
import com.etiya.onlineWeatherInquiry.business.responses.CityResponses.CityDto;
import com.etiya.onlineWeatherInquiry.business.responses.CityResponses.ListCityDto;
import com.etiya.onlineWeatherInquiry.core.utilities.results.DataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.Result;

import java.util.List;

public interface CityService {
    DataResult<List<ListCityDto>> getAll();
    DataResult<CityDto> getByCityName(String cityName);

    Result add(CreateCityRequest createCityRequest);
    Result delete(DeleteCityRequest deleteCityRequest);
    Result update(UpdateCityRequest updateCityRequest);
}
