package com.magdic.vjezba4.components.services;

import com.magdic.vjezba4.components.entities.Client;
import com.magdic.vjezba4.components.entities.Device;
import com.magdic.vjezba4.components.entities.Record;
import com.magdic.vjezba4.components.repositories.ClientRepository;
import com.magdic.vjezba4.components.repositories.DeviceRepository;
import com.magdic.vjezba4.components.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private ClientRepository clientRepository;

    public Iterable<Device> getAllDevices() {return deviceRepository.findAll(); }

    public Device getDevicesById(Long id) throws Exception{

        Device Device = deviceRepository.findById(id).orElse(null);
        if(Device == null) {
            throw new Exception("Could not find Device");
        }

        return Device;
    }

    public Device createDevice(Long clientId, Device newDevice) throws Exception{
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new Exception("Could not find client");
        }
        Device device = deviceRepository.findDeviceByClient(client);
        if (device != null){
            throw new Exception("Client already has a device");
        }

        newDevice.setClient(client);

        return deviceRepository.save(newDevice);
    }

    public Device updateDevice(Long id, Device updatedDevice) throws Exception {
        Device oldDevice = deviceRepository.findById(id).orElse(null);
        if(oldDevice == null) {
            throw new Exception("Could not find Device");
        }
        updatedDevice.setId(oldDevice.getId());
        return deviceRepository.save(updatedDevice);
    }

    public void deleteDevice(Long id) throws Exception{
        Device deleteDevice = deviceRepository.findById(id).orElse(null);
        if(deleteDevice == null) {
            throw new Exception("Could not find Device");
        }

        deviceRepository.delete(deleteDevice);
    }

    public Record measure(Long id) throws Exception{
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("Device not found");
        }
        Record record = new Record();

        record.setDevice(device);
        record.setMeasurement((int) ((Math.random() * (device.getUpperBound() - device.getLowerBound())) + device.getLowerBound()));
        record.setDateOfMeasurement(new Date());

        List<Record> allRecords = recordRepository.findRecordsbyDevice(id);
        int month = record.getDateOfMeasurement().getMonth();
        if(allRecords.stream().anyMatch(r -> r.getDateOfMeasurement().getMonth() == month))
        {
            throw new Exception("Measurement already taken this month");
        }


        return recordRepository.save(record);
    }

    public List<Record> measurements(Long id) throws Exception {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("Device not found");
        }

        return recordRepository.findRecordsbyDevice(id);
    }

    public Record updateRecord(Long deviceId, Record updatedRecord, Long id) throws Exception{
        Record oldRecord = recordRepository.findById(id).orElse(null);
        if (oldRecord == null) {
            throw new Exception("Record not found");
        }
        List<Record> allRecords = recordRepository.findRecordsbyDevice(deviceId);

        int month = updatedRecord.getDateOfMeasurement().getMonth();
        if(allRecords.stream().anyMatch(r -> r.getDateOfMeasurement().getMonth() == month)) {
            throw new Exception("Measurement already taken this month");
        }
        updatedRecord.setId(id);
        updatedRecord.setDevice(oldRecord.getDevice());

        return recordRepository.save(updatedRecord);
    }

    public int totalByYear(long id, int year) throws Exception{
        List<Record> allRecordsForYear = recordRepository.findRecordsbyDevice(id).stream()
                .filter(r -> r.getDateOfMeasurement().getYear()+1900 == year).toList();

        if(allRecordsForYear.isEmpty()) {
            throw new Exception("No measurements for this year");
        }
        return allRecordsForYear.stream().map(Record::getMeasurement).reduce(0, Integer::sum);
    }

    public String totalByMonth(long id, int year) throws Exception{
        List<Record> allRecordsForYear = recordRepository.findRecordsbyDevice(id).stream()
                .filter(r -> r.getDateOfMeasurement().getYear()+1900 == year).toList();
        if(allRecordsForYear.isEmpty()) {
            throw new Exception("No measurements for this year");
        }
        return allRecordsForYear.stream().map(r -> r.getDateOfMeasurement().getMonth() + ": " + r.getMeasurement() + " ")
                .collect(Collectors.joining());
    }

    public Record totalInMonth(long id, int year, int month) throws Exception{
        Record monthRecord = recordRepository.findRecordsbyDevice(id).stream()
                .filter(r -> r.getDateOfMeasurement().getYear()+1900 == year)
                .filter(r -> r.getDateOfMeasurement().getMonth() == month).findAny().orElse(null);
        if(monthRecord == null) {
            throw new Exception("No measurements for this month");
        }
        return monthRecord;
    }

    public void deleteRecord(Long deviceId, Long id) throws Exception{
        Device device = deviceRepository.findById(deviceId).orElse(null);
        if(device == null) {
            throw new Exception("Could not find Device");
        }

        Record deleteRecord = recordRepository.findById(id).orElse(null);
        if(deleteRecord == null) {
            throw new Exception("Could not find Record");
        }

        recordRepository.delete(deleteRecord);
    }
}
