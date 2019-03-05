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
import org.apache.commons.lang3.StringUtils;
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
    public AvgRecord genAvgRecordDetailByUser(JsonParam param) throws ParseException {
        return recordService.genAvgRecordDetailByUser(param.getUserId(), param.getBodyPart(), param.getFromDate(), param.getToDate(),
                param.getAvgRecordName(), param.getDescription());
    }

    //fromDate and toDate should be in format YYYY-MM-DD
    @PostMapping("/genAvgRecordDetail")
    public AvgRecord genAvgRecordDetail(@RequestBody JsonParam param) throws ParseException {
        if (StringUtils.isNotBlank(param.getUserId())) {
            return genAvgRecordDetailByUser(param);
        } else {
            return recordService.genAvgRecordDetail(param.getBodyPart(), param.getGender(), param.getFromAge(), param.getToAge(),
                    param.getFromDate(), param.getToDate(), param.getAvgRecordName(), param.getDescription());
        }
    }

    @GetMapping("/getRecords")
    public List<Record> getRecords(@RequestParam("userId") final String userId, @RequestParam("bodyPart") final String bodyPart,
                                   @RequestParam("gender") final String gender, @RequestParam("fromAge") final String fromAge,
                                   @RequestParam("toAge") final String toAge, @RequestParam("fromDate") final String fromDate,
                                   @RequestParam("toDate") final String toDate) {
        return recordService.findAll(userId, bodyPart, gender, fromAge, toAge, fromDate, toDate);
    }

    @GetMapping("/getRecordDetail")
    public List<RecordDetail> getRecordDetail(@RequestParam("recordId") Long recordId) {
        return recordDetailService.getRecordDetail(recordId);
    }

    @GetMapping("/getAvgRecords")
    public List<AvgRecord> getAvgRecords(@RequestParam("userId") final String userId, @RequestParam("bodyPart") final String bodyPart,
                                         @RequestParam("gender") final String gender, @RequestParam("fromAge") final String fromAge,
                                         @RequestParam("toAge") final String toAge, @RequestParam("fromDate") final String fromDate,
                                         @RequestParam("toDate") final String toDate, @RequestParam("avgRecordName") final String avgRecordName) {
        return avgRecordService.findAll(userId, bodyPart, gender, fromAge, toAge, fromDate, toDate, avgRecordName);
    }

    @GetMapping("/getAvgRecordDetail")
    public List<AvgRecordDetail> getAvgRecordDetail(@RequestParam("avgRecordId") Long avgRecordId) {
        return avgRecordDetailService.getAllByAvgRecordAvgRecordId(avgRecordId);
    }
}