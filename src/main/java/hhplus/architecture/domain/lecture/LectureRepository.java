package hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import hhplus.architecture.infrastructure.lecture.LectureParams;

public interface LectureRepository {
    void save(LectureParams lectureParams);
    Optional<Lecture> findLectureById(long lectureId);
    List<Lecture> findLecturesContainingDateTime(LocalDateTime time);
    Optional<Lecture> findLectureIdWithWriteLock(Long id);
}
