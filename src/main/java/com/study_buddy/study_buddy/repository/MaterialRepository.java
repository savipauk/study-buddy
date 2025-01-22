package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Material;
import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MaterialRepository extends JpaRepository<Material, Long>{

    Material findByMaterialId(Long materialId);

    List<Material> findByUser(User userId);
    
    List<Material> findByLesson(Lesson lessonId);

    List<Material> findByGroup(StudyGroup groupId);

    Material findByFileName(String fileName);
    
}
