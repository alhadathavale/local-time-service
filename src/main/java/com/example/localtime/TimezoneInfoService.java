package com.example.localtime;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TimezoneInfoService {

    private final TimezoneInfoRepository timezoneInfoRepository;
    private final CityRepository cityRepository;

    public TimezoneInfoService(TimezoneInfoRepository timezoneInfoRepository,
                               CityRepository cityRepository) {
        this.timezoneInfoRepository = timezoneInfoRepository;
        this.cityRepository = cityRepository;
    }

    public List<TimezoneInfo> findAll() {
        return timezoneInfoRepository.findAll();
    }

    public Optional<TimezoneInfo> findById(Integer id) {
        return timezoneInfoRepository.findById(id);
    }

    public List<City> findCitiesByTimezoneId(Integer timezoneId) {
        return cityRepository.findByTimezone_Id(timezoneId);
    }
}
