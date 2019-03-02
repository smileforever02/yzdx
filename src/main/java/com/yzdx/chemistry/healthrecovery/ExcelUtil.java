package com.yzdx.chemistry.healthrecovery;

import com.yzdx.chemistry.healthrecovery.record.Record;
import com.yzdx.chemistry.healthrecovery.record.RecordDetail;
import com.yzdx.chemistry.healthrecovery.record.RecordDetailService;
import com.yzdx.chemistry.healthrecovery.record.RecordService;
import com.yzdx.chemistry.healthrecovery.user.User;
import com.yzdx.chemistry.healthrecovery.user.UserService;
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
import java.util.Optional;

@Service
public class ExcelUtil {

    @Autowired
    RecordService recordService;

    @Autowired
    RecordDetailService detailService;

    @Autowired
    UserService userService;

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

    public String parseExcel(InputStream is, String fileName, long size, String fileDownloadUri) throws Exception {
        Workbook workbook = getWorkBook(is, fileName);
        return parseExcel(workbook, fileName, size, fileDownloadUri);
    }

    private String parseExcel(Workbook workbook, String fileName, long size, String fileDownloadUri) {
        String errMsg = null;

        Sheet sheet = workbook.getSheetAt(0);
        int i = 0;
        Record record = new Record();
        User userToSave = new User();
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
                        userToSave.setUserId(secondCell.getStringCellValue().trim());
                        record.setUserId(secondCell.getStringCellValue().trim());
                    } else if ("姓名".equalsIgnoreCase(cellValue)) {
                        userToSave.setName(secondCell.getStringCellValue());
                    } else if ("测试人体部位".equalsIgnoreCase(cellValue)) {
                        record.setBodyPart(secondCell.getStringCellValue().trim());
                    } else if ("运动频率".equalsIgnoreCase(cellValue)) {
                        record.setFrequency(secondCell.getNumericCellValue());
                    } else if ("年龄".equalsIgnoreCase(cellValue)) {
                        record.setAge(secondCell.getNumericCellValue());
                    } else if ("性别".equalsIgnoreCase(cellValue)) {
                        userToSave.setGender(secondCell.getStringCellValue());
                        record.setGender(secondCell.getStringCellValue());
                    } else if ("出生日期".equalsIgnoreCase(cellValue) || "生日".equalsIgnoreCase(cellValue)) {
                        userToSave.setBirthday(secondCell.getDateCellValue());
                    } else if (cellValue.contains("时间")) {
                        i++;
                        break;
                    }
                }
            }
        }

        Optional<User> userOptional = userService.getUser(userToSave.getUserId());
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(! user.getName().equals(userToSave.getName())) {
                errMsg = fileName + "中，userId '" + userToSave.getUserId() + "'的姓名'"+userToSave.getName() + "'与数据库中的姓名'"+user.getName() + "'不匹配, 请修改后再上传";
                return errMsg;
            }
        } else {
            userService.registerUser(userToSave);
        }

        record.setFileName(fileName);
        record.setSize(size);
        record.setFileDownloadUri(fileDownloadUri);
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

        return errMsg;
    }
}