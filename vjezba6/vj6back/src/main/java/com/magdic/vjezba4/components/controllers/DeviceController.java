package com.magdic.vjezba4.components.controllers;

import com.magdic.vjezba4.components.entities.Client;
import com.magdic.vjezba4.components.entities.Device;
import com.magdic.vjezba4.components.entities.Record;
import com.magdic.vjezba4.components.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    public DeviceService deviceService;

    @GetMapping()
    public Iterable<Device> getAll() { return deviceService.getAllDevices(); }

    @PostMapping("/{clientId}")
    public ResponseEntity<Device> createDevice(@PathVariable Long clientId, @RequestBody Device newDevice) {
        ResponseEntity response;
        try{
            response = ResponseEntity.status(HttpStatus.CREATED).body(deviceService.createDevice(clientId, newDevice));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @GetMapping("/{id}")
    public Device getById(@PathVariable Long id) {
        Device device;
        try {
            device = deviceService.getDevicesById(id);
        } catch (Exception ex) {
            return null;
        }
        return device;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateClient(@PathVariable Long id, @RequestBody Device updatedDevice) {
        ResponseEntity<Device> response;
        try {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(deviceService.updateDevice(id, updatedDevice));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try{
            deviceService.deleteDevice(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Device");
    }

    @GetMapping("/{id}/measure")
    public Record measureCurrentUsage(@PathVariable Long id) {
        try {
            return deviceService.measure(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}/measurements")
    public List<Record> getMeasurements(@PathVariable Long id) {
        try {
            return deviceService.measurements(id);
        } catch (Exception ex){
            return null;
        }
    }

    @PutMapping("/{deviceId}/measurements/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long deviceId, @RequestBody Record updatedRecord, @PathVariable Long id) {
        ResponseEntity<Record> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(deviceService.updateRecord(deviceId, updatedRecord, id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return response;
    }

    @GetMapping("/{id}/measurements/{year}")
    public ResponseEntity<String> totalByYear(@PathVariable Long id, @PathVariable int year) {
        ResponseEntity<String> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(year + " :" + deviceService.totalByYear(id, year));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("/{id}/measurements/{year}/monthly")
    public ResponseEntity<String> totalByMonth(@PathVariable Long id, @PathVariable int year) {
        ResponseEntity<String> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(year + ": " + deviceService.totalByMonth(id, year));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("/{id}/measurements/{year}/{month}")
    public ResponseEntity<String> totalInMonth(@PathVariable Long id, @PathVariable int year, @PathVariable int month) {
        ResponseEntity<String> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(month + " :" + deviceService.totalInMonth(id, year, month).getMeasurement());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return response;
    }

    @DeleteMapping("/{deviceId}/measurements/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long deviceId, @PathVariable Long id) {
        try{
            deviceService.deleteRecord(deviceId, id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Record");

    }

    @GetMapping("/page")
    public List<Device> findPaginated(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo, @RequestParam(name = "pageSize", defaultValue = "3")int pageSize,
                                @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
                                Model model) {
        Page<Device> page = deviceService.findPageable(pageNo , pageSize, sortField, sortDirection);

        return page.getContent();
    }

}
