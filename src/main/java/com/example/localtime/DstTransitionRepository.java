package com.example.localtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface DstTransitionRepository extends JpaRepository<DstTransition, Integer> {

    @Query("SELECT d FROM DstTransition d WHERE :now BETWEEN d.dstStart AND d.dstEnd")
    List<DstTransition> findActiveAt(LocalDateTime now);
}
