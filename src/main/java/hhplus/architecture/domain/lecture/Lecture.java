package hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;

public record Lecture(long lectureId, long userId, String name, LocalDateTime startDate, LocalDateTime endDateTime) {
}
