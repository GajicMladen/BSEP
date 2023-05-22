package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.NewDeviceDTO;
import com.example.SmartHouseAPI.model.Device;
import com.example.SmartHouseAPI.model.Realestate;
import com.example.SmartHouseAPI.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> getDevicesForRealestate(Long realestateID){
        return deviceRepository.getDevicesForRealestate(realestateID);
    }

    public Device turnOnOfNotifications(Long deviceID){
        Device device = this.deviceRepository.getReferenceById(deviceID);
        device.setReadData( ! device.isReadData() );
        return this.deviceRepository.save(device);
    }

    public Device addNewDevice(NewDeviceDTO newDeviceDTO, Realestate realestate){
        Device newDevice = new Device();
        newDevice.setRealestate(realestate);
        newDevice.setReadData(true);
        newDevice.setDescription(newDeviceDTO.getDeviceDescription());
        newDevice.setDeviceType(newDeviceDTO.getDeviceType());
        return this.deviceRepository.save(newDevice);
    }

}
