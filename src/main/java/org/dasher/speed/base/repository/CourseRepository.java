package org.dasher.speed.base.repository;

import org.dasher.speed.base.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByProfessorId(Long professorId);

    List<Course> findByNameContainingIgnoreCase(String name);
}
