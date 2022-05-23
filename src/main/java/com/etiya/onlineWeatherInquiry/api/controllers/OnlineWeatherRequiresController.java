package com.etiya.onlineWeatherInquiry.api.controllers;

import com.etiya.onlineWeatherInquiry.business.abstracts.OnlineWeatherInquireService;
import com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses.ListOnlineWeatherInquireDto;
import com.etiya.onlineWeatherInquiry.core.utilities.results.DataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/onlineweatherinquires")
@CrossOrigin
public class OnlineWeatherRequiresController {
    //private final OnlineWeatherInquireManager onlineWeatherInquireManager;
    private OnlineWeatherInquireService onlineWeatherInquireService;

    public OnlineWeatherRequiresController(OnlineWeatherInquireService onlineWeatherInquireService) {
        this.onlineWeatherInquireService = onlineWeatherInquireService;
    }

   /* @GetMapping("/getall")
    public DataResult<List<ListOnlineWeatherInquireDto>> getAll(){
        return this.onlineWeatherInquireService.getAll();
    }*/

    @GetMapping("/citynameweatherinquiry")
    public ResponseEntity<Result> getByCityNameWeatherInquiry(@RequestParam String city,String userName){//@PathVariable
        return ResponseEntity.ok(this.onlineWeatherInquireService.getByCityNameWeatherInquiry(city,userName));
    }

    @GetMapping("/cityandusernameandquerydatebetween")
    public Result getByCityAndUserNameAndQueryDateBetween(@RequestParam String city, String userName, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){//@PathVariable
        return this.onlineWeatherInquireService.getBySearchWeatherInquiry(city,userName,startDate,endDate);
    }

}
