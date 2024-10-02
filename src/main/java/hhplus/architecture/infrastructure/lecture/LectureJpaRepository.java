package hhplus.architecture.infrastructure.lecture;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
    @Query("SELECT l FROM LectureEntity l WHERE :dateTime BETWEEN l.startDateTime AND l.endDateTime")
    @EntityGraph(attributePaths = {"user"})
    List<LectureEntity> findLecturesContainingDateTime(LocalDateTime dateTime);
}
