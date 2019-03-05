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
import java.text.SimpleDateFormat;
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
    public AvgRecord genAvgRecordDetailByUser(@RequestBody JsonParam param) throws ParseException {
        return recordService.genAvgRecordDetailByUser(param.getUserId(), param.getBodyPart(), param.getFromDate(), param.getToDate(),
                param.getAvgRecordName(), param.getDescription());
    }

    //fromDate and toDate should be in format YYYY-MM-DD
    @PostMapping("/genAvgRecordDetail")
    public AvgRecord genAvgRecordDetail(@RequestBody JsonParam param) throws ParseException {
        return recordService.genAvgRecordDetail(param.getBodyPart(), param.getGender(), param.getFromAge(), param.getToAge(),
                param.getFromDate(), param.getToDate(), param.getAvgRecordName(), param.getDescription());
    }

    @GetMapping("/getRecordsByUser")
    public List<Record> getRecordsByUser(@RequestBody JsonParam param) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
        Date fromDate = format.parse(param.getFromDate());
        Date toDate = format.parse(param.getToDate());
        return recordService.findAllByUserIdAndBodyPartAndTestDateBetween(param.getUserId(), param.getBodyPart(), fromDate, toDate);
    }

    @GetMapping("/getRecordDetail")
    public List<RecordDetail> getRecordDetail(@RequestParam("recordId") String recordId) {
        return recordDetailService.getRecordDetail(recordId);
    }

    @GetMapping("/getAvgRecordDetail")
    public List<AvgRecordDetail> getAvgRecordDetail(@RequestParam("avgRecordId") String avgRecordId) {
        return avgRecordDetailService.getAllByAvgRecordAvgRecordId(avgRecordId);
    }
}