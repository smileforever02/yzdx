package com.yzdx.chemistry.healthrecovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.Collection;

@RestController
public class UploadFileController {

    @Autowired
    ExcelUtil excelUtil;

/*    @PostMapping(value = "/uploadExcel")
    public ResponseEntity<?> uploadExcel(HttpServletRequest req) {
        try {
            Collection<Part> excels = req.getParts();
            if (excels != null) {
                for (Part excel : excels) {
                    InputStream is = excel.getInputStream();
                    excelUtil.parseExcel(is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(null);
    }*/

    @PostMapping("/uploadExcels")
    public ResponseEntity<?> uploadFile(@RequestParam("excels") MultipartFile[] multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            try {
                excelUtil.parseExcel(multipartFile.getInputStream(), fileName, multipartFile.getSize());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("file %s is saved", fileName));
        }

        return ResponseEntity.ok(null);
    }
}
