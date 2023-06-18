package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.NewDeviceDTO;
import com.example.SmartHouseAPI.enums.DeviceType;
import com.example.SmartHouseAPI.model.Device;
import com.example.SmartHouseAPI.model.Realestate;
import com.example.SmartHouseAPI.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public boolean isReadDataForDevice(Integer deviceID){

        Optional<Device> device = this.deviceRepository.findById(deviceID.longValue());
        return device.map(Device::isReadData).orElse(false);
    }

    public Device addNewDevice(NewDeviceDTO newDeviceDTO, Realestate realestate){
        Device newDevice = new Device();
        newDevice.setRealestate(realestate);
        newDevice.setReadData(true);
        newDevice.setDescription(newDeviceDTO.getDeviceDescription());
        newDevice.setDeviceType(newDeviceDTO.getDeviceType());
        if(newDeviceDTO.getDeviceType() == DeviceType.TEMPERATURE){
            newDevice.setUpLimit(30);
            newDevice.setDownLimit(16);
        }else{
            newDevice.setUpLimit(1);
            newDevice.setDownLimit(0);
        }
        newDevice.setOccurrencesNumber(1);
        newDevice.setTimeRangeMinutes(5);
        return this.deviceRepository.save(newDevice);
    }

    public Device editAlarms(Device newDevice){
        Optional<Device> old = this.deviceRepository.findById(newDevice.getDeviceID());
        if(old.isPresent()){
            old.get().setUpLimit(newDevice.getUpLimit());
            old.get().setDownLimit(newDevice.getDownLimit());
            old.get().setOccurrencesNumber(newDevice.getOccurrencesNumber());
            old.get().setTimeRangeMinutes(newDevice.getTimeRangeMinutes());
            return this.deviceRepository.save(old.get());
        }
        return null;
    }

}
