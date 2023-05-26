package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.dto.NewDeviceDTO;
import com.example.SmartHouseAPI.model.Device;
import com.example.SmartHouseAPI.model.Realestate;
import com.example.SmartHouseAPI.service.DeviceService;
import com.example.SmartHouseAPI.service.RealestateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/devices")
@CrossOrigin(origins = "http://localhost:4200")
public class DevicesController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RealestateService realestateService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Device>> getAllDevices(){
        return ResponseEntity.ok( this.deviceService.getAllDevices());
    }

    @GetMapping(value = "/forRealestate/{id}")
    public ResponseEntity<List<Device>> getDevicesForRealestate(@PathVariable Long id){
        return ResponseEntity.ok(this.deviceService.getDevicesForRealestate(id));
    }

    @GetMapping(value = "/turnOnOfNotifications/{id}")
    public ResponseEntity<Device> turnOnOfNotifications(@PathVariable Long id){
        return ResponseEntity.ok(this.deviceService.turnOnOfNotifications(id));
    }

    @PostMapping(value = "/addNew")
    public ResponseEntity<?> addNewDevice(@RequestBody NewDeviceDTO newDeviceDTO){
        Realestate realestate = realestateService.getByID(newDeviceDTO.getRealestateID());
        Device newDevice = this.deviceService.addNewDevice(newDeviceDTO,realestate);
        return ResponseEntity.ok().body(newDevice);
    }

    @PostMapping(value = "/editAlarms")
    public ResponseEntity<?> editAlarms(@RequestBody Device newDevice){
        Device newD = this.deviceService.editAlarms(newDevice);
        return ResponseEntity.ok().body(newD);
    }
}
