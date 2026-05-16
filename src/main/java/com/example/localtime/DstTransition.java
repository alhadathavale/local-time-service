package com.example.localtime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dst_transitions")
public class DstTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timezone_id", nullable = false)
    private TimezoneInfo timezone;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "dst_start", nullable = false)
    private LocalDateTime dstStart;

    @Column(name = "dst_end", nullable = false)
    private LocalDateTime dstEnd;

    public Integer getId() { return id; }
    public TimezoneInfo getTimezone() { return timezone; }
    public Integer getTimezoneId() { return timezone != null ? timezone.getId() : null; }
    public Integer getYear() { return year; }
    public LocalDateTime getDstStart() { return dstStart; }
    public LocalDateTime getDstEnd() { return dstEnd; }
}
