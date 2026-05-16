package com.example.localtime;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByCountryCodeIgnoreCase(String countryCode);
    List<City> findByTimezone_Id(Integer timezoneId);
}
