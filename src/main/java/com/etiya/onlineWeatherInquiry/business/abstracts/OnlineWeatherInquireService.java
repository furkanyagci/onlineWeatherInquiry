package com.etiya.onlineWeatherInquiry.business.abstracts;

import com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest.CreateOnlineWeatherInquireRequest;
import com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest.DeleteOnlineWeatherInquireRequest;
import com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest.UpdateOnlineWeatherInquireRequest;
import com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses.ListOnlineWeatherInquireDto;
import com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses.OnlineWeatherInquireDto;
import com.etiya.onlineWeatherInquiry.core.utilities.results.DataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.Result;

import java.time.LocalDate;
import java.util.List;

public interface OnlineWeatherInquireService {

    DataResult<OnlineWeatherInquireDto> getByCityNameWeatherInquiry(String city,String userName);
    Result getBySearchWeatherInquiry(String city, String userName, LocalDate startDate, LocalDate endDate);
    DataResult<List<ListOnlineWeatherInquireDto>> getAll();


    Result add(CreateOnlineWeatherInquireRequest createOnlineWeatherInquireRequest);
    Result delete(DeleteOnlineWeatherInquireRequest deleteOnlineWeatherInquireRequest);
    Result update(UpdateOnlineWeatherInquireRequest updateOnlineWeatherInquireRequest);
}
