package com.example.localtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/LocalTime")
public class LocalTimeController {
    private final TimezoneLookupService timezoneLookupService;

    public LocalTimeController(TimezoneLookupService timezoneLookupService) {
        this.timezoneLookupService = timezoneLookupService;
    }

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
