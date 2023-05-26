package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.Log;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogsRepository extends CassandraRepository<Log,String> {
    @Query("select l from Log l where l.house_id = ?1")
    List<Log> houseID(int realestateID);

    @Query("select l from Log l where l.house_id = ?1 and l.exact_time >= ?2 and l.exact_time <= ?3")
    List<Log> houseIDAndExactTimeGreaterThanEqualAndExactTimeLessThanEqual(int realestateID, LocalDateTime startDate, LocalDateTime endDate);
}
