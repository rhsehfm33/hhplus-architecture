package hhplus.architecture.domain.registration;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hhplus.architecture.domain.lecture.Lecture;
import hhplus.architecture.domain.lecture.LectureRepository;
import hhplus.architecture.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureRegistrationService {
    public final int MAX_REGISTRATION = 30;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final LectureRegistrationRepository lectureRegistrationRepository;

    public void registerLecture(long lectureId, long userId) {
        // TODO: 비관적 락 구현 필요
        // 신청 가능한지 검사
        Lecture lecture = lectureRepository.findLectureById(lectureId).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 특강입니다.")
        );
        userRepository.findUserById(userId).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 유저입니다.")
        );

        Optional<LectureRegistration> lectureRegistration =
            lectureRegistrationRepository.findByLectureIdAndUserId(lectureId, userId);
        if (lectureRegistration.isPresent()) {
            throw new IllegalArgumentException("이미 신청한 특강입니다.");
        }
        if (lectureRegistrationRepository.countByLectureId(lectureId) > MAX_REGISTRATION) {
            throw new IllegalStateException("신청 마감된 특강입니다.");
        }

        LocalDateTime createdAt = LocalDateTime.now();
        if (createdAt.isBefore(lecture.startDate()) || createdAt.isAfter(lecture.endDateTime())) {
            throw new IllegalArgumentException("현재 해당 특강은 신청할 수 없습니다.");
        }

        // 특강 신청 저장
        lectureRegistrationRepository.save(lectureId, userId, createdAt);
    }
}
