package com.etiya.onlineWeatherInquiry;

import com.etiya.onlineWeatherInquiry.core.crossCuttingConcerns.exceptionHandling.BusinessException;
import com.etiya.onlineWeatherInquiry.core.utilities.results.ErrorDataResult;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestControllerAdvice
public class OnlineWeatherInquiryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineWeatherInquiryApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public MongoClient mongo() {
		ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/OWIDb");
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();
		return MongoClients.create(mongoClientSettings);
	}

	/*@Bean
	public MongoTemplate getMongoTemplate(){
		return new MongoTemplate();
	}*/

	@ExceptionHandler
	@ResponseStatus(code= HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException){
		Map<String,String> validationErrors = new HashMap<String, String>();
		for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(validationErrors,"VALIDATION_ERROR(S)");
		return errorDataResult;
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleBusinessException(BusinessException businessException) {
		ErrorDataResult<Object> errorResults = new ErrorDataResult<>(businessException.getMessage(), "Business.Error");
		return errorResults;
	}
}
