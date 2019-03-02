package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvgRecordService {

    @Autowired
    AvgRecordRepository repository;

    public AvgRecord generateAvgRecord(AvgRecord avgRecord) {
        return repository.save(avgRecord);
    }

    public AvgRecord getAvgRecord(Long avgRecordId) {
        Optional<AvgRecord> avgRecord = repository.findById(avgRecordId);
        if(avgRecord.isPresent()) {
            return avgRecord.get();
        } else {
            return null;
        }
    }
}
