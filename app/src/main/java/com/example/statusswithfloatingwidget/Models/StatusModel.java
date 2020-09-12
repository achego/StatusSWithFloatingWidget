package com.example.statusswithfloatingwidget.Models;

import java.io.File;

public class StatusModel {

    String filePath, fileSize, videoDuration;
    File file;

    public StatusModel(String filePath, String fileSize, File file) {
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public File getFile() {
        return file;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }
}
