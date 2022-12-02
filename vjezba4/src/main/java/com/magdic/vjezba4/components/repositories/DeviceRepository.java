package com.magdic.vjezba4.components.repositories;

import com.magdic.vjezba4.components.entities.Client;
import com.magdic.vjezba4.components.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceByClient(Client client);
}
