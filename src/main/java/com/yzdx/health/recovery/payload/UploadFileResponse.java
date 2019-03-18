package com.yzdx.health.recovery.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private String errMsg;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public UploadFileResponse(String fileName, String errMsg) {
        this.fileName = fileName;
        this.errMsg = errMsg;
    }
}
