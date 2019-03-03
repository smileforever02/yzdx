package com.yzdx.health.recovery;

import com.yzdx.health.recovery.entity.record.Record;
import com.yzdx.health.recovery.service.ExcelService;
import com.yzdx.health.recovery.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HealthApp implements ApplicationRunner {
    @Autowired
    ExcelService excelService;

    @Autowired
    RecordService recordService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String filePath = "/Users/yjiao/Documents/test.xlsx";

        Optional<Record> record = recordService.findById(163L);
        System.out.println(record.get().getRecordDetails().size());
        //  excelUtil.parseExcel(filePath);
    }
}
