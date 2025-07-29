package org.dasher.speed.base.service;

import org.dasher.speed.base.domain.Professor;
import org.dasher.speed.base.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository repo;

    public ProfessorService(ProfessorRepository repo) {
        this.repo = repo;
    }

    public List<Professor> findAll()                  { return repo.findAll(); }
    public Optional<Professor> findById(Long id)      { return repo.findById(id); }
    public Professor save(Professor professor)        { return repo.save(professor); }
    public void deleteById(Long id)                   { repo.deleteById(id); }

    public Optional<Professor> findByUserId(Long id)  { return repo.findByUserId(id); }
    public Optional<Professor> findWithUserById(Long id) {
        return repo.findWithUserById(id);
    }

    public List<Professor> search(String term) {
        return term == null || term.isBlank()
               ? repo.findAll()
               : repo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }
}
