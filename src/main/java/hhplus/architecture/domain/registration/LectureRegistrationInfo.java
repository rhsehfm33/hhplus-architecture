package hhplus.architecture.domain.registration;

import java.time.LocalDateTime;

public record LectureRegistrationInfo(
    long registrationId,
    long lectureId,
    long userId,
    String lectureName,
    String userName,
    LocalDateTime createdAt
) {
}
