package com.etiya.onlineWeatherInquiry.core.crossCuttingConcerns.exceptionHandling;

@SuppressWarnings("serial")//Serileştime hatasını bastırmka için yazdık. Serileştirme dosyaya loglama vs.
public class BusinessException extends RuntimeException{
	public BusinessException(String message) {
		super(message);
	}
}
