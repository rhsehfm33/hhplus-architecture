package hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;

public record Lecture(long id, long userId, String name, LocalDateTime startDate, LocalDateTime endDateTime) {
}
