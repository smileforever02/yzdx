package com.yzdx.health.recovery.control;

import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class RecordController {


    @Autowired
    RecordService recordService;

    //fromDate and toDate should be in format YYYY-MM-DD
    @GetMapping("/genAvgRecordDetailByUser")
    public AvgRecord genAvgRecordDetailByUser(@RequestParam("userId") String userId, @RequestParam("bodyPart") String bodyPart,
                                              @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
                                              @RequestParam("name") String name, @RequestParam("description") String description) throws ParseException {
        return recordService.genAvgRecordDetailByUser(userId, bodyPart, fromDate, toDate, name, description);
    }

    //fromDate and toDate should be in format YYYY-MM-DD
    @GetMapping("/genAvgRecordDetail")
    public AvgRecord genAvgRecordDetail(@RequestParam("bodyPart") String bodyPart, @RequestParam("gender") String gender,
                                        @RequestParam("fromAge") double fromAge, @RequestParam("toAge") double toAge,
                                        @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
                                        @RequestParam("name") String name, @RequestParam("description") String description) throws ParseException {
        return recordService.genAvgRecordDetail(bodyPart, gender, fromAge, toAge, fromDate, toDate, name, description);
    }
}
