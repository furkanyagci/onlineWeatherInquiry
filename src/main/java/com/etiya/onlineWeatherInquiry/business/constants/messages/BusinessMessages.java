package com.etiya.onlineWeatherInquiry.business.constants.messages;

public class BusinessMessages {
    public class UserMessages{
        public static final String USER_ADDED="Kullanıcı başarıyla eklendi.";
        public static final String USER_DELETED="Kullanıcı başarıyla silindi";
        public static final String USER_UPDATED="Kullanıcı başarıyla güncellendi";
        public static final String USER_LISTED="Kullanıcı başarıyla listelendi";
        public static final String LOGIN_FAILED="Kullanıcı giriş bilgileri hatalı";
        public static final String LOGIN_SUCCESSFUL="Kullanıcı girişi başarılı";
        public static final String USER_NOT_FOUND="Kullanıcı bulunamadı";
        public static final String NOT_AUTHORIZED="Kullanıcı şifresi değiştirmek için yetkiniz yok";
    }

    public class CityMessages{
        public static final String CITY_ADDED="Şehir başarıyla eklendi.";
        public static final String CITY_DELETED="Şehir başarıyla silindi";
        public static final String CITY_UPDATED="Şehir başarıyla güncellendi";
        public static final String CITY_LISTED="Şehir başarıyla listelendi";
        public static final String CITY_ALREADY_EXISTS="Şehir daha önce eklenmiş.";
        public static final String CITY_NOT_FOUND="Şehir bulunamadı.";
    }

    public class WeatherInquiryReportMessages{
        public static final String WEATHER_INQUIRY_REPORT_LISTED="Hava durumu raporları başarıyla listelendi.";
    }
}
