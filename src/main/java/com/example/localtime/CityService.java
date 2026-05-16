package com.example.localtime;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final DstTransitionRepository dstTransitionRepository;

    public CityService(CityRepository cityRepository,
                       DstTransitionRepository dstTransitionRepository) {
        this.cityRepository = cityRepository;
        this.dstTransitionRepository = dstTransitionRepository;
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public List<City> findByCountryCode(String countryCode) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode);
    }

    public Optional<City> findById(Integer id) {
        return cityRepository.findById(id);
    }

    public List<TimezoneInfo> findTimezonesWithActiveDst() {
        return dstTransitionRepository.findActiveAt(LocalDateTime.now())
                .stream()
                .map(DstTransition::getTimezone)
                .distinct()
                .collect(Collectors.toList());
    }
}
