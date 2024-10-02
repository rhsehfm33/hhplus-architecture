package hhplus.architecture.domain.registration;

import java.time.LocalDateTime;

import hhplus.architecture.domain.lecture.Lecture;
import hhplus.architecture.domain.user.User;

public record LectureRegistration(long id, Lecture lecture, User user, LocalDateTime createdAt) {
}