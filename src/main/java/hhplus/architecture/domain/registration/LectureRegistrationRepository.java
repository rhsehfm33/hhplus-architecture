package hhplus.architecture.domain.registration;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LectureRegistrationRepository {
    void save(long lectureId, long userId, LocalDateTime createdAt);
    Optional<LectureRegistration> findByLectureIdAndUserId(long userId, long lectureId);
    int countByLectureId(long lectureId);
}
