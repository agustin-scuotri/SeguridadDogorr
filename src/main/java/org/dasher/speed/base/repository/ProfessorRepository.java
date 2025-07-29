package org.dasher.speed.base.repository;

import org.dasher.speed.base.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByUserId(Long userId);

    List<Professor> findByNameContainingIgnoreCase(String name);

    List<Professor> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String name, String email
    );

    @Query("""
           SELECT p
           FROM Professor p
           JOIN FETCH p.user u
           WHERE p.id = :id
           """)
    Optional<Professor> findWithUserById(@Param("id") Long id);
}
