package hhplus.architecture.infrastructure.lecture;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.architecture.domain.lecture.Lecture;
import hhplus.architecture.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<Lecture> findLectureById(long lectureId) {
        Optional<LectureEntity> optionalLectureEntity = lectureJpaRepository.findById(lectureId);
        if (optionalLectureEntity.isPresent()) {
            Lecture lecture = LectureEntity.to(optionalLectureEntity.get());
            return Optional.of(lecture);
        } else {
            return Optional.empty();
        }
    }
}
