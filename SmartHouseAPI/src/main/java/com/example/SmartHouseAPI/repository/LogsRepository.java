package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.Log;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface LogsRepository extends CassandraRepository<Log,String> {

}
