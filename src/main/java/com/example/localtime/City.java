package com.example.localtime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "population")
    private Long population;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timezone_id", nullable = false)
    private TimezoneInfo timezone;

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getCountryCode() { return countryCode; }
    public String getCountryName() { return countryName; }
    public Long getPopulation() { return population; }
    public TimezoneInfo getTimezone() { return timezone; }
    public Integer getTimezoneId() { return timezone != null ? timezone.getId() : null; }
    public String getTimezoneZoneId() { return timezone != null ? timezone.getZoneId() : null; }
}
