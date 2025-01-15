package com.study_buddy.study_buddy.dto;

import java.time.LocalDateTime;

import com.study_buddy.study_buddy.model.Material;

public class MaterialDto {
    private Long userId;
    private Long groupId;
    private Long lessonId;
    private Long materialId;
    private String fileName;
    private String mimeType;
    private byte[] fileData;
    private Long fileSize;
    private String description;
    private LocalDateTime uploadDate;

    public MaterialDto() {}

    public MaterialDto(Long userId, Long groupId, Long lessonId, Long materialId, String fileName, String mimeType, byte[] fileData, Long fileSize, String description, LocalDateTime uploadDate) {
        this.userId = userId;
        this.groupId = groupId;
        this.lessonId = lessonId;
        this.materialId = materialId;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.description = description;
        this.uploadDate = uploadDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public static MaterialDto convertToDto(Material material) {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setMaterialId(material.getMaterialId());
        materialDto.setUserId(material.getUser().getUserId());
        materialDto.setGroupId(material.getGroup() != null ? material.getGroup().getGroupId() : null);
        materialDto.setLessonId(material.getLesson() != null ? material.getLesson().getLessonId() : null);
        materialDto.setFileName(material.getFileName());
        materialDto.setMimeType(material.getMimeType());
        materialDto.setFileData(material.getFileData());
        materialDto.setFileSize(material.getFileSize());
        materialDto.setDescription(material.getDescription());
        materialDto.setUploadDate(material.getUploadDate());
        return materialDto;
    }
}

