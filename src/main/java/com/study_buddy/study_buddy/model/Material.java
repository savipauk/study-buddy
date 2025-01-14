package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private StudyGroup group;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "mime_type")
    private String mimeType;

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(name = "description")
    private String description;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public Material () {}

    public Material(Long materialId, User user, StudyGroup group, Lesson lesson,String fileName, String mimeType, byte[] fileData, String description, LocalDateTime uploadDate) {
        this.materialId = materialId;
        this.user = user;
        this.group = group;
        this.lesson = lesson;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileData = fileData;
        this.description = description;
        this.uploadDate = uploadDate;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StudyGroup getGroup() {
        return group;
    }

    public void setGroup(StudyGroup group) {
        this.group = group;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public byte[] getFileData(){
        return fileData;
    }

    public void setFileData(byte[] fileData){
        this.fileData = fileData;
    }

    public String getMimeType(){
        return mimeType;
    }

    public void setMimeType(String mimeType){
        this.mimeType = mimeType;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
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
}
