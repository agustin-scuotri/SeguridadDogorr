package org.dasher.speed.base.service;

import org.dasher.speed.base.domain.Administrator;
import org.dasher.speed.base.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdministratorRepository repo;

    public AdministratorService(AdministratorRepository repo) {
        this.repo = repo;
    }

    public List<Administrator> findAll()               { return repo.findAllWithUser(); }
    public Optional<Administrator> findById(Long id)   { return repo.findById(id); }
    public Administrator save(Administrator a)         { return repo.save(a); }
    public void deleteById(Long id)                    { repo.deleteById(id); }

    public Optional<Administrator> findByUserId(Long userId) {
        return repo.findAllWithUser()
                   .stream()
                   .filter(a -> a.getUser()!=null && a.getUser().getId().equals(userId))
                   .findFirst();
    }

    public List<Administrator> search(String term) {
        return term == null || term.isBlank()
               ? repo.findAllWithUser()
               : repo.searchWithUser(term);
    }
}
