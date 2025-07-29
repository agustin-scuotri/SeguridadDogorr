package org.dasher.speed.base.service;

import org.dasher.speed.base.domain.Person;
import org.dasher.speed.base.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public List<Person> findAll()               { return repo.findAll(); }
    public Optional<Person> findById(Long id)   { return repo.findById(id); }
    public Person save(Person person)           { return repo.save(person); }
    public void deleteById(Long id)             { repo.deleteById(id); }

    public List<Person> findByLastName(String name) { return repo.findByName(name); }
}
