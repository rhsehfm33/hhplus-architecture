package hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;

public record Lecture(long id, long userId, String lectureName, String userName, LocalDateTime startTime, LocalDateTime endTime) {
}
