package com.example.localtime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/local-time")
@Tag(name = "Local Time", description = "IP-based local time lookup and available IANA zones")
public class LocalTimeController {
    private final TimezoneLookupService timezoneLookupService;

    public LocalTimeController(TimezoneLookupService timezoneLookupService) {
        this.timezoneLookupService = timezoneLookupService;
    }

    @Operation(summary = "List all available IANA timezone zone IDs")
    @GetMapping("/zones")
    public List<String> getZones() {
        return ZoneId.getAvailableZoneIds().stream().sorted().collect(Collectors.toList());
    }

    @Operation(summary = "Get local time for the caller's IP address")
    @GetMapping
    public LocalTimeResponse getLocalTime(HttpServletRequest request) {
        String clientIp = ClientIpResolver.resolve(request);

        try {
            String timezone = timezoneLookupService.lookupTimezone(clientIp);
            ZonedDateTime localTime = ZonedDateTime.now(ZoneId.of(timezone));
            return new LocalTimeResponse(clientIp, timezone, localTime.toString());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Failed to determine timezone for client IP", ex);
        }
    }
}
