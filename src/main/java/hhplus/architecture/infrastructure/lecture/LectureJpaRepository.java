package hhplus.architecture.infrastructure.lecture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
    @Query("SELECT l FROM LectureEntity l WHERE :dateTime BETWEEN l.startDateTime AND l.endDateTime")
    @EntityGraph(attributePaths = {"user"})
    List<LectureEntity> findLecturesContainingDateTime(LocalDateTime dateTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM LectureEntity l WHERE l.id = :id")
    Optional<LectureEntity> findLectureForUpdateById(Long id);
}
