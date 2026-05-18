package com.example.localtime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/world-clock")
@Tag(name = "World Clock", description = "Timezone, city, and DST reference data")
public class WorldClockController {

    private final TimezoneInfoService timezoneInfoService;
    private final CityService cityService;

    public WorldClockController(TimezoneInfoService timezoneInfoService,
                                CityService cityService) {
        this.timezoneInfoService = timezoneInfoService;
        this.cityService = cityService;
    }

    @Operation(summary = "List all timezones")
    @GetMapping("/timezones")
    public List<TimezoneInfo> getAllTimezones() {
        return timezoneInfoService.findAll();
    }

    @Operation(summary = "Get a timezone by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Timezone found"),
        @ApiResponse(responseCode = "404", description = "Timezone not found")
    })
    @GetMapping("/timezones/{id}")
    public TimezoneInfo getTimezoneById(
            @Parameter(description = "Timezone ID (1–19)") @PathVariable Integer id) {
        return timezoneInfoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Timezone not found with id: " + id));
    }

    @Operation(summary = "List cities in a timezone")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cities returned"),
        @ApiResponse(responseCode = "404", description = "Timezone not found")
    })
    @GetMapping("/timezones/{id}/cities")
    public List<City> getCitiesByTimezone(
            @Parameter(description = "Timezone ID (1–19)") @PathVariable Integer id) {
        timezoneInfoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Timezone not found with id: " + id));
        return timezoneInfoService.findCitiesByTimezoneId(id);
    }

    @Operation(summary = "List all cities, optionally filtered by country code")
    @GetMapping("/cities")
    public List<City> getCities(
            @Parameter(description = "ISO 3166-1 alpha-2 country code, e.g. US, DE, JP")
            @RequestParam(required = false) String country) {
        if (country != null && !country.isBlank()) {
            return cityService.findByCountryCode(country);
        }
        return cityService.findAll();
    }

    @Operation(summary = "Get a city by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "City found"),
        @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/cities/{id}")
    public City getCityById(
            @Parameter(description = "City ID") @PathVariable Integer id) {
        return cityService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "City not found with id: " + id));
    }

    @Operation(summary = "List timezones currently observing DST")
    @GetMapping("/dst/active")
    public List<TimezoneInfo> getActivelyObservingDst() {
        return cityService.findTimezonesWithActiveDst();
    }
}
