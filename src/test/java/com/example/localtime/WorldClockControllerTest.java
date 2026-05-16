package com.example.localtime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorldClockController.class)
class WorldClockControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TimezoneInfoService timezoneInfoService;

    @MockitoBean
    CityService cityService;

    // ── GET /world-clock/timezones ────────────────────────────────────────────

    @Test
    void getAllTimezones_returns200WithList() throws Exception {
        TimezoneInfo tz = timezone(1, "America/New_York");
        when(timezoneInfoService.findAll()).thenReturn(List.of(tz));

        mockMvc.perform(get("/world-clock/timezones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].zoneId").value("America/New_York"));
    }

    @Test
    void getAllTimezones_returns200WithEmptyList() throws Exception {
        when(timezoneInfoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/world-clock/timezones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ── GET /world-clock/timezones/{id} ───────────────────────────────────────

    @Test
    void getTimezoneById_returns200WhenFound() throws Exception {
        TimezoneInfo tz = timezone(7, "Europe/London");
        when(timezoneInfoService.findById(7)).thenReturn(Optional.of(tz));

        mockMvc.perform(get("/world-clock/timezones/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zoneId").value("Europe/London"));
    }

    @Test
    void getTimezoneById_returns404WhenNotFound() throws Exception {
        when(timezoneInfoService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/world-clock/timezones/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTimezoneById_returns404ForId37BecauseTimezoneTableOnlyHasIds1To19() throws Exception {
        when(timezoneInfoService.findById(37)).thenReturn(Optional.empty());

        mockMvc.perform(get("/world-clock/timezones/37"))
                .andExpect(status().isNotFound());
    }

    // ── GET /world-clock/timezones/{id}/cities ────────────────────────────────

    @Test
    void getCitiesByTimezone_returns200WithCities() throws Exception {
        TimezoneInfo tz = timezone(2, "America/New_York");
        City c = city(1, "New York City", "US");
        when(timezoneInfoService.findById(2)).thenReturn(Optional.of(tz));
        when(timezoneInfoService.findCitiesByTimezoneId(2)).thenReturn(List.of(c));

        mockMvc.perform(get("/world-clock/timezones/2/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("New York City"));
    }

    @Test
    void getCitiesByTimezone_returns404WhenTimezoneNotFound() throws Exception {
        when(timezoneInfoService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/world-clock/timezones/999/cities"))
                .andExpect(status().isNotFound());
    }

    // ── GET /world-clock/cities ───────────────────────────────────────────────

    @Test
    void getCities_returnsAllWith200() throws Exception {
        City c = city(33, "Tokyo", "JP");
        when(cityService.findAll()).thenReturn(List.of(c));

        mockMvc.perform(get("/world-clock/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tokyo"));
    }

    @Test
    void getCities_filtersByCountryCode() throws Exception {
        City c = city(23, "Berlin", "DE");
        when(cityService.findByCountryCode("DE")).thenReturn(List.of(c));

        mockMvc.perform(get("/world-clock/cities?country=DE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].countryCode").value("DE"));
    }

    // ── GET /world-clock/cities/{id} ──────────────────────────────────────────

    @Test
    void getCityById_returns200WhenFound() throws Exception {
        City c = city(19, "Paris", "FR");
        when(cityService.findById(19)).thenReturn(Optional.of(c));

        mockMvc.perform(get("/world-clock/cities/19"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paris"));
    }

    @Test
    void getCityById_returns404WhenNotFound() throws Exception {
        when(cityService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/world-clock/cities/999"))
                .andExpect(status().isNotFound());
    }

    // ── GET /world-clock/dst/active ───────────────────────────────────────────

    @Test
    void getActiveDst_returns200WithActiveZones() throws Exception {
        TimezoneInfo tz = timezone(2, "America/New_York");
        when(cityService.findTimezonesWithActiveDst()).thenReturn(List.of(tz));

        mockMvc.perform(get("/world-clock/dst/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].zoneId").value("America/New_York"));
    }

    @Test
    void getActiveDst_returns200WithEmptyListWhenNoneActive() throws Exception {
        when(cityService.findTimezonesWithActiveDst()).thenReturn(List.of());

        mockMvc.perform(get("/world-clock/dst/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private TimezoneInfo timezone(int id, String zoneId) {
        TimezoneInfo tz = mock(TimezoneInfo.class);
        when(tz.getId()).thenReturn(id);
        when(tz.getZoneId()).thenReturn(zoneId);
        when(tz.getDisplayName()).thenReturn("Display Name");
        when(tz.getRegion()).thenReturn("Region");
        when(tz.getUtcOffsetMinutes()).thenReturn(0);
        when(tz.getObservesDst()).thenReturn(false);
        when(tz.getCities()).thenReturn(List.of());
        when(tz.getDstTransitions()).thenReturn(List.of());
        return tz;
    }

    private City city(int id, String name, String countryCode) {
        City c = mock(City.class);
        when(c.getId()).thenReturn(id);
        when(c.getName()).thenReturn(name);
        when(c.getCountryCode()).thenReturn(countryCode);
        when(c.getCountryName()).thenReturn("Country");
        when(c.getPopulation()).thenReturn(1_000_000L);
        when(c.getTimezoneId()).thenReturn(1);
        when(c.getTimezoneZoneId()).thenReturn("UTC");
        return c;
    }
}
