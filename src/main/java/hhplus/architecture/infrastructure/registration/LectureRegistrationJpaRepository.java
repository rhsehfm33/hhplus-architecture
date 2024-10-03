package hhplus.architecture.infrastructure.registration;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRegistrationJpaRepository extends JpaRepository<LectureRegistrationEntity, Long> {
    Optional<LectureRegistrationEntity> findByLectureIdAndUserId(long lectureId, long userId);
    int countByLectureId(long lectureId);

    @EntityGraph(attributePaths = {"user", "lecture"})
    List<LectureRegistrationEntity> findAllByUserId(long userId);
}
