package hhplus.architecture.infrastructure.lecture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.architecture.domain.lecture.Lecture;
import hhplus.architecture.domain.lecture.LectureRepository;
import hhplus.architecture.infrastructure.user.UserEntity;
import hhplus.architecture.infrastructure.user.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
    private final UserJpaRepository userJpaRepository;
    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public void save(LectureParams lectureParams) {
        UserEntity userEntity = userJpaRepository.findById(lectureParams.userId()).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 유저입니다.")
        );
        lectureJpaRepository.save(LectureEntity.from(lectureParams, userEntity));
    }

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

    @Override
    public List<Lecture> findLecturesContainingDateTime(LocalDateTime time) {
        return lectureJpaRepository.findLecturesContainingDateTime(time)
            .stream().map(LectureEntity::to).toList();
    }

    @Override
    public Optional<Lecture> findLectureIdWithWriteLock(Long id) {
        return lectureJpaRepository.findLectureForUpdateById(id).map(LectureEntity::to);
    }
}
