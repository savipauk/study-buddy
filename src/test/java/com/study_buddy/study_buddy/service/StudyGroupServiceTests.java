package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.repository.StudyGroupRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudyGroupServiceTests {

    @Mock
    private StudyGroupRepository studyGroupRepository;

    @InjectMocks
    private StudyGroupService studyGroupService;

    @Test
    public void StudyGroupService_GetPokemonById_ReturnsStudyGroup(){
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setGroupId(1L);
        studyGroup.setGroupName("testGroup");
        studyGroup.setLocation("New Zeland");
        studyGroup.setMaxMembers(10);
        studyGroup.setDate(LocalDate.now());
        studyGroup.setExpirationDate(LocalDate.now().minusDays(2));

        when(studyGroupRepository.findByGroupId(1L)).thenReturn(studyGroup);

        StudyGroup foundStudyGroup = studyGroupService.getStudyGroupById(1L);

        assertNotNull(foundStudyGroup);
        assertEquals(foundStudyGroup.getGroupName(), studyGroup.getGroupName());
    }


}
