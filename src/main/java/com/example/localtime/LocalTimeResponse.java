package com.example.localtime;

public class LocalTimeResponse {
    private final String salutation = "Hello World after Spring and Java upgrade and fixing deprecated APIs!";
    private final String clientIp;
    private final String timezone;
    private final String localTime;


    public LocalTimeResponse(String clientIp, String timezone, String localTime) {
        this.clientIp = clientIp;
        this.timezone = timezone;
        this.localTime = localTime;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getLocalTime() {
        return localTime;
    }
}
