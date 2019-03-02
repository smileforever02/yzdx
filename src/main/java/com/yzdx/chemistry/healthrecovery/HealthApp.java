package com.yzdx.chemistry.healthrecovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class HealthApp implements ApplicationRunner {
    @Autowired
    ExcelUtil excelUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String filePath = "/Users/yjiao/Documents/test.xlsx";

      //  excelUtil.parseExcel(filePath);
    }
}
