package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Professor;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor createProfessor(User user){
        Professor professor = new Professor();
        professor.setUser(user);
        professorRepository.save(professor);
        return professor;
    }

    public Professor getProfessorById(Long id) { return professorRepository.findByProfessorId(id); }

    public Professor getProfessorByUserId(Long id) { return professorRepository.findByUserId(id); }
}