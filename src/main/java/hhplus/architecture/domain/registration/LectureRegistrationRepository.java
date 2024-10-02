package hhplus.architecture.domain.registration;

import java.util.Optional;

public interface LectureRegistrationRepository {
    Optional<LectureRegistration> findByLectureIdAndUserId(long userId, long lectureId);
}
