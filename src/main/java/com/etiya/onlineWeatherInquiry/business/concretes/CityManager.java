package com.etiya.onlineWeatherInquiry.business.concretes;

import com.etiya.onlineWeatherInquiry.business.abstracts.CityService;
import com.etiya.onlineWeatherInquiry.business.constants.messages.BusinessMessages;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.CreateCityRequest;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.DeleteCityRequest;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.UpdateCityRequest;
import com.etiya.onlineWeatherInquiry.business.responses.CityResponses.CityDto;
import com.etiya.onlineWeatherInquiry.business.responses.CityResponses.ListCityDto;
import com.etiya.onlineWeatherInquiry.core.crossCuttingConcerns.exceptionHandling.BusinessException;
import com.etiya.onlineWeatherInquiry.core.utilities.mapping.ModelMapperService;
import com.etiya.onlineWeatherInquiry.core.utilities.results.*;
import com.etiya.onlineWeatherInquiry.dataAccess.abstracts.CityRepository;
import com.etiya.onlineWeatherInquiry.entities.City;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {

    private CityRepository cityRepository;
    private ModelMapperService modelMapperService;

    public CityManager(CityRepository cityRepository, ModelMapperService modelMapperService) {
        this.cityRepository = cityRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<ListCityDto>> getAll() {
        List<City> cities = this.cityRepository.findAll();
        List<ListCityDto> response = cities.stream()
                .map(city -> this.modelMapperService.forDto().map(city, ListCityDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response,BusinessMessages.CityMessages.CITY_LISTED);
    }

    @Override
    public DataResult<CityDto> getByCityName(String cityName) {
        cityName = cityName.toLowerCase();
        City city = this.cityRepository.getByCityNameIgnoreCase(cityName);
        if (city == null) {
            return new ErrorDataResult<>(BusinessMessages.CityMessages.CITY_NOT_FOUND);
        }
        CityDto response = this.modelMapperService.forDto().map(city, CityDto.class);
        return new SuccessDataResult<>(response,BusinessMessages.CityMessages.CITY_LISTED);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {
        createCityRequest.setCityName(createCityRequest.getCityName().toLowerCase());
        checkIfBrandNameExists(createCityRequest.getCityName());
        City city = this.modelMapperService.forRequest().map(createCityRequest,City.class);
        city.setCreateDate(LocalDateTime.now());
        this.cityRepository.save(city);
        return new SuccessDataResult<>(city.getId(), BusinessMessages.CityMessages.CITY_ADDED);
    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) {
        this.cityRepository.deleteById(deleteCityRequest.getId());
        return new SuccessResult(BusinessMessages.CityMessages.CITY_DELETED);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {
        City city = this.modelMapperService.forRequest().map(updateCityRequest,City.class);
        this.cityRepository.save(city);
        return new SuccessResult(BusinessMessages.CityMessages.CITY_UPDATED);
    }

    private void checkIfBrandNameExists(String cityName) {
        if (this.cityRepository.existsByCityNameIgnoreCase(cityName)) {
            throw new BusinessException(BusinessMessages.CityMessages.CITY_ALREADY_EXISTS);
        }
    }
}
