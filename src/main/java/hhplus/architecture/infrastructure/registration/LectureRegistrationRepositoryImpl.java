package hhplus.architecture.infrastructure.registration;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.architecture.domain.registration.LectureRegistration;
import hhplus.architecture.domain.registration.LectureRegistrationRepository;
import hhplus.architecture.infrastructure.lecture.LectureEntity;
import hhplus.architecture.infrastructure.lecture.LectureJpaRepository;
import hhplus.architecture.infrastructure.user.UserEntity;
import hhplus.architecture.infrastructure.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LectureRegistrationRepositoryImpl implements LectureRegistrationRepository {
    private final LectureRegistrationJpaRepository lectureRegistrationJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public void save(long lectureId, long userId, LocalDateTime createdAt) {
        LectureRegistrationEntity lectureRegistrationEntity =
            new LectureRegistrationEntity(lectureId, userId, createdAt);
        lectureRegistrationJpaRepository.save(lectureRegistrationEntity);
    }

    @Override
    public Optional<LectureRegistration> findByLectureIdAndUserId(long lectureId, long userId) {
        Optional<UserEntity> userEntity = userJpaRepository.findById(userId);
        Optional<LectureEntity> lectureEntity = lectureJpaRepository.findById(lectureId);
        Optional<LectureRegistrationEntity> lectureRegistrationEntity =
            lectureRegistrationJpaRepository.findByLectureIdAndUserId(lectureId, userId);

        if (userEntity.isEmpty() || lectureEntity.isEmpty() || lectureRegistrationEntity.isEmpty()) {
            return Optional.empty();
        }

        LectureRegistration lectureRegistration = LectureRegistrationEntity.to(
            lectureRegistrationEntity.get(), lectureEntity.get(), userEntity.get()
        );

        return Optional.of(lectureRegistration);
    }

    @Override
    public int countByLectureId(long lectureId) {
        return lectureRegistrationJpaRepository.countByLectureId(lectureId);
    }
}
