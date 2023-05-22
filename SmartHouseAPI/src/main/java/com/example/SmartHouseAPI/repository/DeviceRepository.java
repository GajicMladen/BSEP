package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository  extends JpaRepository<Device, Long> {

    @Query("select d from Device d where d.realestate.id = ?1")
    List<Device> getDevicesForRealestate(Long id);

}
