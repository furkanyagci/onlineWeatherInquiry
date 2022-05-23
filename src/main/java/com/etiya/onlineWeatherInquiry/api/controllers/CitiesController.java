package com.etiya.onlineWeatherInquiry.api.controllers;

import com.etiya.onlineWeatherInquiry.business.abstracts.CityService;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.CreateCityRequest;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.DeleteCityRequest;
import com.etiya.onlineWeatherInquiry.business.requests.CityRequests.UpdateCityRequest;
import com.etiya.onlineWeatherInquiry.business.responses.CityResponses.CityDto;
import com.etiya.onlineWeatherInquiry.business.responses.CityResponses.ListCityDto;
import com.etiya.onlineWeatherInquiry.core.utilities.results.DataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//
@RequestMapping("/api/cities")
@CrossOrigin
public class CitiesController {
    private CityService cityService;

    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/getall")
    public DataResult<List<ListCityDto>> getAll(){
        return this.cityService.getAll();
    }

    @GetMapping("/getByCityName")
    public DataResult<CityDto> getByCityName(@RequestParam String cityName){
        return this.cityService.getByCityName(cityName);
    }


    @PostMapping("/add")
    public Result add(@RequestBody CreateCityRequest createCityRequest) {
        return this.cityService.add(createCityRequest);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateCityRequest updateCityRequest) {
        return this.cityService.update(updateCityRequest);
    }

    @PostMapping("/delete")//delete/{cityId}
    public Result delete(@RequestBody DeleteCityRequest deleteCityRequest) {//@PathVariable parametre değişir String cityId
        return this.cityService.delete(deleteCityRequest);
    }
}
