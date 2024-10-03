package hhplus.architecture.infrastructure.lecture;

import java.time.LocalDateTime;

public record LectureParams(long lectureId, long userId, String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
}