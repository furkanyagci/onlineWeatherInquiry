package com.etiya.onlineWeatherInquiry.business.concretes;

import com.etiya.onlineWeatherInquiry.business.abstracts.OnlineWeatherInquireService;
import com.etiya.onlineWeatherInquiry.business.constants.messages.BusinessMessages;
import com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest.CreateOnlineWeatherInquireRequest;
import com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest.DeleteOnlineWeatherInquireRequest;
import com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest.SearchOnlineWeatherInquireRequest;
import com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest.UpdateOnlineWeatherInquireRequest;
import com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses.ListOnlineWeatherInquireDto;
import com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses.OnlineWeatherInquireDto;
import com.etiya.onlineWeatherInquiry.core.utilities.mapping.ModelMapperService;
import com.etiya.onlineWeatherInquiry.core.utilities.results.DataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.Result;
import com.etiya.onlineWeatherInquiry.core.utilities.results.SuccessDataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.SuccessResult;
import com.etiya.onlineWeatherInquiry.dataAccess.abstracts.WeatherInquiryReportRepository;
import com.etiya.onlineWeatherInquiry.entities.WeatherInquiryReport;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class OnlineWeatherInquireManager implements OnlineWeatherInquireService {

    private final RestTemplate restTemplate;
    private ModelMapperService modelMapperService;
    private final HttpServletRequest httpServletRequest;
    private WeatherInquiryReportRepository weatherInquiryReportRepository;
    private MongoTemplate mongoTemplate;

    public OnlineWeatherInquireManager(RestTemplate restTemplate, ModelMapperService modelMapperService, HttpServletRequest httpServletRequest, WeatherInquiryReportRepository weatherInquiryReportRepository, MongoTemplate mongoTemplate){
        this.restTemplate = restTemplate;
        this.modelMapperService = modelMapperService;
        this.httpServletRequest = httpServletRequest;
        this.weatherInquiryReportRepository = weatherInquiryReportRepository;
        this.mongoTemplate = mongoTemplate;
    }

    private SearchOnlineWeatherInquireRequest getCityWeatherInquiry(String city)
    {
        long startTime = System.currentTimeMillis();

        SearchOnlineWeatherInquireRequest searchOnlineWeatherInquireRequest = restTemplate
                .getForObject("http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=fe635f29b85cdc16fc0d47434bda63fe",SearchOnlineWeatherInquireRequest.class);

        long endTime = System.currentTimeMillis();
        long queryTime = this.timeMillis(startTime,endTime);

        searchOnlineWeatherInquireRequest.setQueryTime(queryTime);
        searchOnlineWeatherInquireRequest.setName(city);
        return searchOnlineWeatherInquireRequest;
    }

    private double kelvinToCelsiusConverter(double temp){
        double kelvinAbsoluteZeroTemperature=273.15;
        return Math.round(temp-kelvinAbsoluteZeroTemperature);
    }

    private long timeMillis(long startTime, long endTime){
        long timeElapsed = endTime - startTime;
        return timeElapsed;
    }

    @Override
    public DataResult<OnlineWeatherInquireDto> getByCityNameWeatherInquiry(String city, String userName) {
        SearchOnlineWeatherInquireRequest searchOnlineWeatherInquireRequest =this.getCityWeatherInquiry(city);

        OnlineWeatherInquireDto response= this.modelMapperService.forDto().map(searchOnlineWeatherInquireRequest,OnlineWeatherInquireDto.class);

        double celcius=kelvinToCelsiusConverter(response.getMain().getTemp());
        response.getMain().setTemp(celcius);

        CreateOnlineWeatherInquireRequest createOnlineWeatherInquireRequest =new CreateOnlineWeatherInquireRequest();
        createOnlineWeatherInquireRequest.setUserName(userName);
        createOnlineWeatherInquireRequest.setCity(response.getName());
        createOnlineWeatherInquireRequest.setQueryTime(searchOnlineWeatherInquireRequest.getQueryTime());

        this.add(createOnlineWeatherInquireRequest);

        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<List<ListOnlineWeatherInquireDto>> getAll() {
        List<WeatherInquiryReport> weatherInquiryReports = this.weatherInquiryReportRepository.findAll();

        List<ListOnlineWeatherInquireDto> response = weatherInquiryReports.stream()
                .map(weatherInquiryReport -> this.modelMapperService.forDto().map(weatherInquiryReport, ListOnlineWeatherInquireDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response, BusinessMessages.WeatherInquiryReportMessages.WEATHER_INQUIRY_REPORT_LISTED);
    }

    @Override
    public Result getBySearchWeatherInquiry(String city, String userName, LocalDate startDate, LocalDate endDate){
        /*
        Predicate<WeatherInquiryReport> predicate1 = element -> element.getCity().contains(city);
        Predicate<WeatherInquiryReport> predicate2 = element -> element.getUserName().contains(userName);
        Predicate<WeatherInquiryReport> predicate3 = element -> (element.getQueryDate() == startDate);
        Predicate<WeatherInquiryReport> predicate4 = element -> element.getQueryDate() == endDate;
        Predicate<WeatherInquiryReport> andPredicate = predicate3.and(predicate4);
        List<WeatherInquiryReport> weatherInquiryReports=  this.weatherInquiryReportRepository.findAll().stream().filter(andPredicate).collect(Collectors.toList());
        */

        return getQueryIncomingRequestValues(city,userName,startDate,endDate);
    }

    private Result getQueryIncomingRequestValues(String city, String userName, LocalDate startDate, LocalDate endDate){
        //city=null;
        startDate=null;
        endDate=null;
        userName=null;
        Criteria criteria = new Criteria();

        if (userName != null) {
            criteria = criteria.and("userName").is(userName);
        }

        if (city != null) {
            criteria = criteria.and("city").is(city);
        }

        if (startDate != null && endDate == null) {
            criteria = criteria.and("queryDate").gte(startDate);
        }

        if (startDate == null && endDate != null) {
            criteria = criteria.and("queryDate").lte(endDate);
        }

        if (startDate != null && endDate != null) {
            criteria = criteria.and("queryDate").gte(startDate).lte(endDate);
        }

        Query query = new Query(criteria);
        query.collation(Collation.of("tr").strength(Collation.ComparisonLevel.secondary()));
        /*
        case insensitive arama için ku kodu kullandım
        query.collation(Collation.of("tr").strength(Collation.ComparisonLevel.secondary()));
        */
        query.with(Sort.by(Sort.Direction.DESC, "queryDate"));
        List<WeatherInquiryReport> weatherInquiryReports = this.mongoTemplate.find(query,WeatherInquiryReport.class);
        List<ListOnlineWeatherInquireDto> response = weatherInquiryReports.stream().map(weatherInquiryReport -> modelMapperService.forDto()
                .map(weatherInquiryReport, ListOnlineWeatherInquireDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response);


        /*

        if (city == null && userName != null && startDate == null && endDate == null) {
            return this.getByUserNameWeatherInquiryReport(userName);
        }

        if (city != null && userName == null && startDate == null && endDate == null) {
            return this.getByCityWeatherInquiryReport(city);
        }

        if (city != null && userName != null && startDate != null && endDate != null) {
            return this.getByCityAndUserNameAndQueryDateBetweenWeatherInquiryReport(city,userName,startDate,endDate);
        }

        if (city == null && userName != null && startDate != null && endDate != null) {
            return this.getByUserNameAndQueryDateBetweenWeatherInquiryReport(userName,startDate,endDate);
        }

        if (city != null && userName == null && startDate != null && endDate != null) {
            return this.getByCityAndQueryDateBetweenWeatherInquiryReport(city,startDate,endDate);
        }
*/
       // return null;
    }
/*
    private Result getByUserNameWeatherInquiryReport(String userName){
        List<WeatherInquiryReport> weatherInquiryReports = this.weatherInquiryReportRepository.getByUserNameIgnoreCase(userName);
        List<ListOnlineWeatherInquireDto> response = weatherInquiryReports.stream()
                .map(weatherInquiryReport -> this.modelMapperService.forDto().map(weatherInquiryReport, ListOnlineWeatherInquireDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.WeatherInquiryReportMessages.WEATHER_INQUIRY_REPORT_LISTED);
    }

    private Result getByCityWeatherInquiryReport(String city){
        List<WeatherInquiryReport> weatherInquiryReports = this.weatherInquiryReportRepository.getByCityIgnoreCase(city);
        List<ListOnlineWeatherInquireDto> response = weatherInquiryReports.stream()
                .map(weatherInquiryReport -> this.modelMapperService.forDto().map(weatherInquiryReport, ListOnlineWeatherInquireDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.WeatherInquiryReportMessages.WEATHER_INQUIRY_REPORT_LISTED);
    }

    private Result getByCityAndUserNameAndQueryDateBetweenWeatherInquiryReport(String city, String userName, LocalDate startDate, LocalDate endDate){
        List<WeatherInquiryReport> weatherInquiryReports = this.weatherInquiryReportRepository.getByCityIgnoreCaseAndUserNameIgnoreCaseAndQueryDateBetween(city,userName,startDate,endDate);
        List<ListOnlineWeatherInquireDto> response = weatherInquiryReports.stream()
                .map(weatherInquiryReport -> this.modelMapperService.forDto().map(weatherInquiryReport, ListOnlineWeatherInquireDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.WeatherInquiryReportMessages.WEATHER_INQUIRY_REPORT_LISTED);
    }

    private Result getByUserNameAndQueryDateBetweenWeatherInquiryReport(String userName, LocalDate startDate, LocalDate endDate){
        List<WeatherInquiryReport> weatherInquiryReports = this.weatherInquiryReportRepository.getByUserNameIgnoreCaseAndQueryDateBetween(userName, startDate, endDate);
        List<ListOnlineWeatherInquireDto> response = weatherInquiryReports.stream()
                .map(weatherInquiryReport -> this.modelMapperService.forDto().map(weatherInquiryReport, ListOnlineWeatherInquireDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.WeatherInquiryReportMessages.WEATHER_INQUIRY_REPORT_LISTED);
    }

    private Result getByCityAndQueryDateBetweenWeatherInquiryReport(String city, LocalDate startDate, LocalDate endDate){
        List<WeatherInquiryReport> weatherInquiryReports = this.weatherInquiryReportRepository.getByCityIgnoreCaseAndQueryDateBetween(city,startDate,endDate);
        List<ListOnlineWeatherInquireDto> response = weatherInquiryReports.stream()
                .map(weatherInquiryReport -> this.modelMapperService.forDto().map(weatherInquiryReport, ListOnlineWeatherInquireDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.WeatherInquiryReportMessages.WEATHER_INQUIRY_REPORT_LISTED);
    }
*/
    @Override
    public Result add(CreateOnlineWeatherInquireRequest createOnlineWeatherInquireRequest) {
        //createOnlineWeatherInquireRequest.setCity(createOnlineWeatherInquireRequest.getCity().toLowerCase());
        createOnlineWeatherInquireRequest.setIpAddress(httpServletRequest.getRemoteAddr());
        createOnlineWeatherInquireRequest.setQueryDate(LocalDate.now());

        WeatherInquiryReport weatherInquiryReport = this.modelMapperService.forRequest()
                .map(createOnlineWeatherInquireRequest,WeatherInquiryReport.class);
        this.weatherInquiryReportRepository.save(weatherInquiryReport);

        return new SuccessResult();
    }

    @Override
    public Result delete(DeleteOnlineWeatherInquireRequest deleteOnlineWeatherInquireRequest) {
        return null;
    }

    @Override
    public Result update(UpdateOnlineWeatherInquireRequest updateOnlineWeatherInquireRequest) {
        return null;
    }
}
