package com.yzdx.health.recovery.control;

import com.yzdx.health.recovery.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordController {


    @Autowired
    RecordService recordService;

    //fromDate and toDate should be in format YYYY-MM-DD
    @GetMapping("/getAvgRecordDetailByUser")
    public List<Object> genAvgRecordDetailByUser(@RequestParam("userId") String userId, @RequestParam("bodyPart") String bodyPart, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        return recordService.genAvgRecordDetailByUser(userId, bodyPart, fromDate, toDate);
    }
}
