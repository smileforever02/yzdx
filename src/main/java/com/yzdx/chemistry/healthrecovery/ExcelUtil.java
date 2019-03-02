package com.yzdx.chemistry.healthrecovery;

import com.yzdx.chemistry.healthrecovery.record.Record;
import com.yzdx.chemistry.healthrecovery.record.RecordDetail;
import com.yzdx.chemistry.healthrecovery.record.RecordDetailService;
import com.yzdx.chemistry.healthrecovery.record.RecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExcelUtil {

    @Autowired
    RecordService recordService;

    @Autowired
    RecordDetailService detailService;

    private static Workbook getWorkBook(String filePath) throws Exception {
        //xls-2003, xlsx-2007
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);
            if (filePath.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (filePath.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            } else {
                //  抛出自定义的业务异常
                throw new Exception("excel格式文件错误");
            }
        } catch (IOException e) {
            //  抛出自定义的业务异常
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private static Workbook getWorkBook(InputStream is, String fileName) throws Exception {
        //xls-2003, xlsx-2007
        try {
            if (fileName.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            } else {
                //  抛出自定义的业务异常
                throw new Exception("excel格式文件错误");
            }
        } catch (IOException e) {
            //  抛出自定义的业务异常
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }



    public void parseExcel(InputStream is, String fileName, long size) throws Exception {
        Workbook workbook = getWorkBook(is, fileName);
        parseExcel(workbook, fileName, size);
    }

    public void parseExcel(Workbook workbook, String fileName, long size) {
        Sheet sheet = workbook.getSheetAt(0);
        int i = 0;
        Record record = new Record();
        int lastRowNum = sheet.getLastRowNum();
        for (i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                Cell secondCell = row.getCell(1);
                if (cell != null && secondCell != null) {
                    String cellValue = cell.getStringCellValue();
                    if ("测试日期".equalsIgnoreCase(cellValue)) {
                        record.setTestDate(secondCell.getDateCellValue());
                    } else if ("用户id".equalsIgnoreCase(cellValue)) {
                        record.setUserId(secondCell.getStringCellValue().trim());
                    } else if ("测试人体部位".equalsIgnoreCase(cellValue)) {
                        record.setBodyPart(secondCell.getStringCellValue().trim());
                    } else if ("运动频率".equalsIgnoreCase(cellValue)) {
                        record.setFrequency(secondCell.getNumericCellValue());
                    } else if ("年龄".equalsIgnoreCase(cellValue)) {
                        record.setAge(secondCell.getNumericCellValue());
                    } else if (cellValue.contains("时间")) {
                        i++;
                        break;
                    }
                }
            }
        }
        record.setFileName(fileName);
        record.setSize(size);
        record.setCreatedDate(new Date());

        List<RecordDetail> details = new ArrayList<>();
        for (; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                RecordDetail detail = new RecordDetail();
                detail.setTime(row.getCell(0).getNumericCellValue());
                detail.setCap(row.getCell(1).getNumericCellValue());
                detail.setInitCap(row.getCell(2).getNumericCellValue());
                detail.setDeltaCapPerc(row.getCell(3).getNumericCellValue());
                detail.setPower(row.getCell(4).getNumericCellValue());
                detail.setCreatedDate(new Date());

                details.add(detail);
            } else {
                break;
            }
        }

        record = recordService.createRecord(record);
        Long recordId = record.getRecordId();
        System.out.println("record saved with recordId:" + recordId);

        for(RecordDetail detail : details) {
            detail.setRecordId(recordId);
        }
        detailService.createRecordDetails(details);
        System.out.println(details.size() + " record details are saved for recordId " + recordId);
    }
}
