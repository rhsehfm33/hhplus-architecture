package hhplus.architecture.infrastructure.registration;

import java.time.LocalDateTime;

public record LectureRegistrationParams(long registrationId, long lectureId, long userId, LocalDateTime createdAt) {
}
