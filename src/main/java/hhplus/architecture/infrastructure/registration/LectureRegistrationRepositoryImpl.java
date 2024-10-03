package hhplus.architecture.infrastructure.registration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.architecture.domain.registration.LectureRegistration;
import hhplus.architecture.domain.registration.LectureRegistrationRepository;
import hhplus.architecture.infrastructure.lecture.LectureEntity;
import hhplus.architecture.infrastructure.lecture.LectureJpaRepository;
import hhplus.architecture.infrastructure.user.UserEntity;
import hhplus.architecture.infrastructure.user.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LectureRegistrationRepositoryImpl implements LectureRegistrationRepository {
    private final LectureRegistrationJpaRepository lectureRegistrationJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public void save(long lectureId, long userId, LocalDateTime createdAt) {
        LectureEntity lectureEntity = lectureJpaRepository.findById(lectureId).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 특강입니다.")
        );
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 유저입니다.")
        );
        lectureRegistrationJpaRepository.save(
            LectureRegistrationEntity.from(lectureEntity, userEntity, createdAt)
        );
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

    @Override
    public List<LectureRegistration> findAllByUserId(long userId) {
        return lectureRegistrationJpaRepository.findAllByUserId(userId).stream()
            .map(lectureRegistrationEntity -> new LectureRegistration(
                lectureRegistrationEntity.getId(),
                LectureEntity.to(lectureRegistrationEntity.getLecture()),
                UserEntity.to(lectureRegistrationEntity.getUser()),
                lectureRegistrationEntity.getCreatedAt()
            )).toList();
    }
}
