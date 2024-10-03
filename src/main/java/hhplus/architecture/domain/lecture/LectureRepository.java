package hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findLectureById(long lectureId);
    List<Lecture> findLecturesContainingDateTime(LocalDateTime time);
    Optional<Lecture> findLectureIdWithWriteLock(Long id);
}
