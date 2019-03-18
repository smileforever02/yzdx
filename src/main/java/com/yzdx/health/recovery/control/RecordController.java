package com.yzdx.health.recovery.control;

import com.yzdx.health.recovery.entity.record.Record;
import com.yzdx.health.recovery.entity.record.RecordDetail;
import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordDetail;
import com.yzdx.health.recovery.entity.record.delta.DeltaRecord;
import com.yzdx.health.recovery.entity.record.delta.DeltaRecordDetail;
import com.yzdx.health.recovery.http.AvgRecordJsonParam;
import com.yzdx.health.recovery.http.DeltaRecordJsonParam;
import com.yzdx.health.recovery.service.*;
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

    @Autowired
    DeltaRecordService deltaRecordService;

    @GetMapping("/getRecords")
    public List<Record> getRecords(@RequestParam(value = "userId", required = false) final String userId, @RequestParam(value = "bodyPart", required = false) final String bodyPart,
                                   @RequestParam(value = "gender", required = false) final String gender, @RequestParam(value = "fromAge", required = false) final String fromAge,
                                   @RequestParam(value = "toAge", required = false) final String toAge, @RequestParam(value = "fromDate", required = false) final String fromDate,
                                   @RequestParam(value = "toDate", required = false) final String toDate) {
        return recordService.getRecords(userId, bodyPart, gender, fromAge, toAge, fromDate, toDate);
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
        return avgRecordService.getAvgRecords(userId, bodyPart, gender, fromAge, toAge, fromDate, toDate, avgRecordName);
    }

    //fromDate and toDate should be in format YYYY-MM-DD
    @PostMapping("/genAvgRecordDetail")
    public AvgRecord genAvgRecordDetail(@RequestBody AvgRecordJsonParam param) throws ParseException {
        return recordService.genAvgRecordDetail(param.getUserId(), param.getBodyPart(), param.getGender(), param.getFromAge(), param.getToAge(),
                param.getFromDate(), param.getToDate(), param.getAvgRecordName(), param.getDescription());
    }

    @GetMapping("/getAvgRecordDetail")
    public List<AvgRecordDetail> getAvgRecordDetail(@RequestParam("avgRecordId") final Long avgRecordId) {
        return avgRecordDetailService.getAvgRecordDetail(avgRecordId);
    }

    @PostMapping("/genDeltaRecordDetail")
    public DeltaRecord genDeltaRecordDetail(@RequestBody DeltaRecordJsonParam param) {
        return deltaRecordService.genDeltaRecordDetail(param.getRecordId(), param.getAvgRecordId(), param.getDeltaRecordName(), param.getDescription());
    }

    @GetMapping("/getDeltaRecords")
    public List<DeltaRecord> getDeltaRecords(@RequestParam("userId") final String userId) {
        return deltaRecordService.getDeltaRecords(userId);
    }

    @GetMapping("/getDeltaRecordDetail")
    public List<DeltaRecordDetail> getDeltaRecordDetail(@RequestParam("deltaRecordId") final Long deltaRecordId) {
        return deltaRecordService.getDeltaRecordDetail(deltaRecordId);
    }
}