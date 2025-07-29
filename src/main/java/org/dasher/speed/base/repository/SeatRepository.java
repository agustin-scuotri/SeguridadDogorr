package org.dasher.speed.base.repository;

import org.dasher.speed.base.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByStudentUserId(Long userId);

    List<Seat> findByStudentNameContainingIgnoreCase(String name);

    List<Seat> findByStudentStudentNumber(UUID studentNumber);

    List<Seat> findByCourseId(Long courseId);

    List<Seat> findAllByOrderByYearDesc();

    long countByCourseId(Long courseId);

    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
}
