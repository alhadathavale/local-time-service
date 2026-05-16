package com.example.localtime;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "timezones")
public class TimezoneInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "zone_id", nullable = false, unique = true)
    private String zoneId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "utc_offset_minutes", nullable = false)
    private Integer utcOffsetMinutes;

    @Column(name = "observes_dst", nullable = false)
    private Boolean observesDst;

    @OneToMany(mappedBy = "timezone", fetch = FetchType.LAZY)
    private List<City> cities;

    @OneToMany(mappedBy = "timezone", fetch = FetchType.LAZY)
    private List<DstTransition> dstTransitions;

    public Integer getId() { return id; }
    public String getZoneId() { return zoneId; }
    public String getDisplayName() { return displayName; }
    public String getRegion() { return region; }
    public Integer getUtcOffsetMinutes() { return utcOffsetMinutes; }
    public Boolean getObservesDst() { return observesDst; }
    public List<City> getCities() { return cities; }
    public List<DstTransition> getDstTransitions() { return dstTransitions; }
}
