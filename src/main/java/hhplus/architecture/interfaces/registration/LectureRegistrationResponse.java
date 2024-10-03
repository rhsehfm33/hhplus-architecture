package hhplus.architecture.interfaces.registration;

import java.time.LocalDateTime;

public record LectureRegistrationResponse(
    long lectureId, long userId, String lectureName, String userName, LocalDateTime registeredAt
) {
}
