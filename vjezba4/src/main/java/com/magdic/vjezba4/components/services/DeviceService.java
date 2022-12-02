package com.magdic.vjezba4.components.services;

import com.magdic.vjezba4.components.entities.Client;
import com.magdic.vjezba4.components.entities.Device;
import com.magdic.vjezba4.components.entities.Record;
import com.magdic.vjezba4.components.repositories.ClientRepository;
import com.magdic.vjezba4.components.repositories.DeviceRepository;
import com.magdic.vjezba4.components.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

        return recordRepository.save(record);
    }

    public List<Record> measurements(Long id) throws Exception {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("Device not found");
        }

        return recordRepository.findRecordsbyDevice(id);
    }
}
