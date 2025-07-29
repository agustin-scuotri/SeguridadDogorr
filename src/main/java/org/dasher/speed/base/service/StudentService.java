package org.dasher.speed.base.service;

import org.dasher.speed.base.domain.Student;
import org.dasher.speed.base.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> findAll()                   { return repo.findAll(); }
    public Optional<Student> findById(Long id)       { return repo.findById(id); }
    public Student save(Student student)             { return repo.save(student); }
    public void deleteById(Long id)                  { repo.deleteById(id); }

    public Optional<Student> findByUserId(Long id)   { return repo.findByUserId(id); }
    public Optional<Student> findWithUserById(Long id){
        return repo.findWithUserById(id);
    }

    public List<Student> search(String term) {
        return term == null || term.isBlank()
               ? repo.findAll()
               : repo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }
}
