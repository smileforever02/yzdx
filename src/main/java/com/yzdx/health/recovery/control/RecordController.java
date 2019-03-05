package com.yzdx.health.recovery.control;

import com.yzdx.health.recovery.entity.record.Record;
import com.yzdx.health.recovery.entity.record.RecordDetail;
import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordDetail;
import com.yzdx.health.recovery.service.AvgRecordDetailService;
import com.yzdx.health.recovery.service.AvgRecordService;
import com.yzdx.health.recovery.service.RecordDetailService;
import com.yzdx.health.recovery.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class RecordController {


    @Autowired
    RecordService recordService;

    @Autowired
    RecordDetailService recordDetailService;

    @Autowired
    AvgRecordService avgRecordService;

    @Autowired
    AvgRecordDetailService avgRecordDetailService;

    //fromDate and toDate should be in format YYYY-MM-DD
    @PostMapping("/genAvgRecordDetailByUser")
    public AvgRecord genAvgRecordDetailByUser(@RequestParam("userId") String userId, @RequestParam("bodyPart") String bodyPart,
                                              @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
                                              @RequestParam("name") String name, @RequestParam("description") String description) throws ParseException {
        return recordService.genAvgRecordDetailByUser(userId, bodyPart, fromDate, toDate, name, description);
    }

    //fromDate and toDate should be in format YYYY-MM-DD
    @PostMapping("/genAvgRecordDetail")
    public AvgRecord genAvgRecordDetail(@RequestParam("bodyPart") String bodyPart, @RequestParam("gender") String gender,
                                        @RequestParam("fromAge") double fromAge, @RequestParam("toAge") double toAge,
                                        @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
                                        @RequestParam("name") String name, @RequestParam("description") String description) throws ParseException {
        return recordService.genAvgRecordDetail(bodyPart, gender, fromAge, toAge, fromDate, toDate, name, description);
    }

    @GetMapping("/getRecordsByUser")
    public List<Record> getRecordsByUser(@RequestParam("userId") String userId, @RequestParam("bodyPart") String bodyPart,
                                         @RequestParam("fromDate") Date fromDate, @RequestParam("toDate") Date toDate) {
        return recordService.findAllByUserIdAndBodyPartAndTestDateBetween(userId, bodyPart, fromDate, toDate);
    }


    @GetMapping("/getRecordDetails")
    public List<RecordDetail> getRecordDetails(@RequestParam("recordId") String recordId) {
        return recordDetailService.getRecordDetails(recordId);
    }

    @GetMapping("/getAvgRecordDetails")
    public List<AvgRecordDetail> getAvgRecordDetails(@RequestParam("avgRecordId") String avgRecordId) {
        return avgRecordDetailService.getAllByAvgRecordAvgRecordId(avgRecordId);
    }
}
