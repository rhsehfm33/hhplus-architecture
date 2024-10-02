package hhplus.architecture.infrastructure.registration;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.architecture.domain.registration.LectureRegistration;
import hhplus.architecture.domain.registration.LectureRegistrationRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LectureRegistrationRepositoryImpl implements LectureRegistrationRepository {
    private final LectureRegistrationJpaRepository lectureRegistrationJpaRepository;

    @Override
    public Optional<LectureRegistration> findByLectureIdAndUserId(long userId, long lectureId) {
        Optional<LectureRegistrationEntity> optionalLectureRegistrationEntity =
            lectureRegistrationJpaRepository.findByLectureIdAndUserId(lectureId, userId);
        if (optionalLectureRegistrationEntity.isPresent()) {
            LectureRegistration lectureRegistration =
                LectureRegistrationEntity.to(optionalLectureRegistrationEntity.get());
            return Optional.of(lectureRegistration);
        } else {
            return Optional.empty();
        }
    }
}
