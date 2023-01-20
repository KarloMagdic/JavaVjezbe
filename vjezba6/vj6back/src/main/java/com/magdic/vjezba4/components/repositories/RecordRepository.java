package com.magdic.vjezba4.components.repositories;

import com.magdic.vjezba4.components.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT r FROM Record r LEFT JOIN r.device device WHERE device.id = :device_id")
    List<Record> findRecordsbyDevice(@Param("device_id") Long deviceId);
}
