package com.yzdx.chemistry.healthrecovery.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    RecordRepository repository;

    public Record createRecord(Record record) {
        return repository.save(record);
    }

    public Optional<Record> getRecord(Long recordId) {
        return repository.findById(recordId);
    }
}
