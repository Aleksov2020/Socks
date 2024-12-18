package com.yeel.socks.repo;

import com.yeel.socks.model.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {
    Optional<Sock> findByColorAndPercentage(String color, int percentage);

    @Query("SELECT SUM(s.count) FROM Sock as s WHERE " +
            "(:color IS NULL OR s.color = :color) AND " +
            "(:operator = 'equal' AND s.percentage = :cottonPercentage OR :operator = 'moreThan' AND s.percentage > :cottonPercentage OR :operator = 'lessThan' AND s.percentage < :cottonPercentage)")
    Optional<Integer> findSocksByCriteria(@Param("color") String color,
                                @Param("operator") String operator,
                                @Param("cottonPercentage") Integer cottonPercentage);
}
