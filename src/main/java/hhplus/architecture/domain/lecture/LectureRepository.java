package hhplus.architecture.domain.lecture;

import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findLectureById(long lectureId);

}
