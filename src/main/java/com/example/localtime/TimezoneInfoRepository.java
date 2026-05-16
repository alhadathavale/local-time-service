package com.example.localtime;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TimezoneInfoRepository extends JpaRepository<TimezoneInfo, Integer> {
    Optional<TimezoneInfo> findByZoneId(String zoneId);
}
