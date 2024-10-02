package hhplus.architecture.domain.lecture;

import java.util.Optional;

public interface LectureRepository {
    public Optional<Lecture> findLectureById(long lectureId);
}
