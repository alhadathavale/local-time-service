package com.example.localtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.ZoneId;

@Service
public class TimezoneLookupService {
    private final RestTemplate restTemplate;
    private final String lookupBaseUrl;

    public TimezoneLookupService(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${geo.lookup.base-url:https://ipwho.is}") String lookupBaseUrl) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build();
        this.lookupBaseUrl = lookupBaseUrl;
    }

    public String lookupTimezone(String ipAddress) {
        if (ipAddress == null || ipAddress.trim().isEmpty() || isLoopback(ipAddress)) {
            return ZoneId.systemDefault().getId();
        }

        try {
            ResponseEntity<IpWhoIsResponse> response = restTemplate.getForEntity(
                    lookupBaseUrl + "/{ip}", IpWhoIsResponse.class, ipAddress);
            IpWhoIsResponse body = response.getBody();

            if (body != null && Boolean.TRUE.equals(body.getSuccess())
                    && body.getTimezone() != null
                    && body.getTimezone().getId() != null
                    && !body.getTimezone().getId().trim().isEmpty()) {
                return body.getTimezone().getId();
            }
        } catch (RestClientException ignored) {
            // Fall through to server default timezone.
        }

        return ZoneId.systemDefault().getId();
    }

    private boolean isLoopback(String ipAddress) {
        return "127.0.0.1".equals(ipAddress)
                || "::1".equals(ipAddress)
                || "0:0:0:0:0:0:0:1".equals(ipAddress);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IpWhoIsResponse {
        private Boolean success;
        private Timezone timezone;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public Timezone getTimezone() {
            return timezone;
        }

        public void setTimezone(Timezone timezone) {
            this.timezone = timezone;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Timezone {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
