package hhplus.architecture.domain.registration;

import java.time.LocalDateTime;

public record LectureRegistration(long registrationId, long lectureId, long userId, LocalDateTime createdAt) {
}