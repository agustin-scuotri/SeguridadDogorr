package org.dasher.speed.base.repository;

import org.dasher.speed.base.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);
}
