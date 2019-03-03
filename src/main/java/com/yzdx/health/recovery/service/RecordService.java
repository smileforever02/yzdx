package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.Record;
import com.yzdx.health.recovery.entity.record.RecordDetailRepository;
import com.yzdx.health.recovery.entity.record.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    RecordRepository repository;

    @Autowired
    RecordDetailRepository detailRepository;

    public Record createRecord(Record record) {
        return repository.save(record);
    }

    public Optional<Record> findById(Long recordId) {
        return repository.findById(recordId);
    }

    public List<Record> findAllByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Record> findAllByUserIdAndBodyPart(String userId, String bodyPart) {
        return repository.findAllByUserIdAndBodyPart(userId, bodyPart);
    }


    public List<Record> findAllByUserIdAndBodyPartAndTestDateBetween(String userId, String bodyPart, Date fromDate, Date toDate) {
        return repository.findAllByUserIdAndBodyPartAndTestDateBetween(userId, bodyPart, fromDate, toDate);
    }

    public List<Record> findAllByBodyPartAndGenderAndAgeBetween(String bodyPart, String gender, double fromAge, double toAge) {
        return repository.findAllByBodyPartAndGenderAndAgeBetween(bodyPart, gender, fromAge, toAge);
    }

    public List<Record> findAllByBodyPartAndGenderAndAgeBetweenAndTestDateBetween(String bodyPart, String gender, double fromAge, double toAge, Date fromDate, Date toDate) {
        return repository.findAllByBodyPartAndGenderAndAgeBetweenAndTestDateBetween(bodyPart, gender, fromAge, toAge, fromDate, toDate);
    }

    // fromDate and toDate format should be YYYY-MM-DD
    public List<Object> genAvgRecordDetailByUser(String userId, String bodyPart, String fromDate, String toDate) {
        return detailRepository.genAvgRecordDetailByUser(userId, bodyPart, fromDate, toDate);
    }
}
