package com.yzdx.chemistry.healthrecovery.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordDetailService {

    @Autowired
    RecordDetailRepository repository;

    public List<RecordDetail> createRecordDetails(List<RecordDetail> details) {
        return repository.saveAll(details);
    }

}
