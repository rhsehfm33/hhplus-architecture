package hhplus.architecture.infrastructure.registration;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRegistrationJpaRepository extends JpaRepository<LectureRegistrationEntity, Long> {
    Optional<LectureRegistrationEntity> findByLectureIdAndUserId(long lectureId, long userId);
    int countByLectureId(long lectureId);
}
