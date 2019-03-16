package com.yzdx.health.recovery.control;

import com.yzdx.health.recovery.entity.record.Record;
import com.yzdx.health.recovery.entity.record.RecordDetail;
import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordDetail;
import com.yzdx.health.recovery.http.JsonParam;
import com.yzdx.health.recovery.service.AvgRecordDetailService;
import com.yzdx.health.recovery.service.AvgRecordService;
import com.yzdx.health.recovery.service.RecordDetailService;
import com.yzdx.health.recovery.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    @PostMapping("/genAvgRecordDetail")
    public AvgRecord genAvgRecordDetail(@RequestBody JsonParam param) throws ParseException {
        return recordService.genAvgRecordDetail(param.getUserId(), param.getBodyPart(), param.getGender(), param.getFromAge(), param.getToAge(),
                param.getFromDate(), param.getToDate(), param.getAvgRecordName(), param.getDescription());
    }

    @GetMapping("/getRecords")
    public List<Record> getRecords(@RequestParam(value = "userId", required = false) final String userId, @RequestParam(value = "bodyPart", required = false) final String bodyPart,
                                   @RequestParam(value = "gender", required = false) final String gender, @RequestParam(value = "fromAge", required = false) final String fromAge,
                                   @RequestParam(value = "toAge", required = false) final String toAge, @RequestParam(value = "fromDate", required = false) final String fromDate,
                                   @RequestParam(value = "toDate", required = false) final String toDate) {
        return recordService.findAll(userId, bodyPart, gender, fromAge, toAge, fromDate, toDate);
    }

    @GetMapping("/getRecordDetail")
    public List<RecordDetail> getRecordDetail(@RequestParam("recordId") Long recordId) {
        return recordDetailService.getRecordDetail(recordId);
    }

    @GetMapping("/getAvgRecords")
    public List<AvgRecord> getAvgRecords(@RequestParam(value = "userId", required = false) final String userId, @RequestParam(value = "bodyPart", required = false) final String bodyPart,
                                         @RequestParam(value = "gender", required = false) final String gender, @RequestParam(value = "fromAge", required = false) final String fromAge,
                                         @RequestParam(value = "toAge", required = false) final String toAge, @RequestParam(value = "fromDate", required = false) final String fromDate,
                                         @RequestParam(value = "toDate", required = false) final String toDate, @RequestParam(value = "avgRecordName", required = false) final String avgRecordName) {
        return avgRecordService.findAll(userId, bodyPart, gender, fromAge, toAge, fromDate, toDate, avgRecordName);
    }

    @GetMapping("/getAvgRecordDetail")
    public List<AvgRecordDetail> getAvgRecordDetail(@RequestParam("avgRecordId") Long avgRecordId) {
        return avgRecordDetailService.getAllByAvgRecordAvgRecordId(avgRecordId);
    }
}