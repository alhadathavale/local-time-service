package com.example.localtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/world-clock")
public class WorldClockController {

    private final TimezoneInfoService timezoneInfoService;
    private final CityService cityService;

    public WorldClockController(TimezoneInfoService timezoneInfoService,
                                CityService cityService) {
        this.timezoneInfoService = timezoneInfoService;
        this.cityService = cityService;
    }

    // GET /world-clock/timezones
    @GetMapping("/timezones")
    public List<TimezoneInfo> getAllTimezones() {
        return timezoneInfoService.findAll();
    }

    // GET /world-clock/timezones/{id}
    @GetMapping("/timezones/{id}")
    public TimezoneInfo getTimezoneById(@PathVariable Integer id) {
        return timezoneInfoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Timezone not found with id: " + id));
    }

    // GET /world-clock/timezones/{id}/cities
    @GetMapping("/timezones/{id}/cities")
    public List<City> getCitiesByTimezone(@PathVariable Integer id) {
        timezoneInfoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Timezone not found with id: " + id));
        return timezoneInfoService.findCitiesByTimezoneId(id);
    }

    // GET /world-clock/cities?country=US
    @GetMapping("/cities")
    public List<City> getCities(@RequestParam(required = false) String country) {
        if (country != null && !country.isBlank()) {
            return cityService.findByCountryCode(country);
        }
        return cityService.findAll();
    }

    // GET /world-clock/cities/{id}
    @GetMapping("/cities/{id}")
    public City getCityById(@PathVariable Integer id) {
        return cityService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "City not found with id: " + id));
    }

    // GET /world-clock/dst/active
    @GetMapping("/dst/active")
    public List<TimezoneInfo> getActivelyObservingDst() {
        return cityService.findTimezonesWithActiveDst();
    }
}
