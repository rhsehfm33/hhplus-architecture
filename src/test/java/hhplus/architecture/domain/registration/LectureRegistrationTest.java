package hhplus.architecture.domain.registration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hhplus.architecture.domain.lecture.Lecture;
import hhplus.architecture.domain.lecture.LectureRepository;
import hhplus.architecture.domain.user.User;
import hhplus.architecture.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class LectureRegistrationTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureRegistrationRepository lectureRegistrationRepository;

    @InjectMocks
    private LectureRegistrationService lectureRegistrationService;

    // 더미 데이터
    private LocalDateTime time;
    private User user;
    private Lecture lecture;
    private LectureRegistration lectureRegistration;

    @BeforeEach
    public void setUp() {
        time = LocalDateTime.now();

        // lecture 더미 데이터 세팅
        lecture = new Lecture(1L, 1L, "l1", "u1", time.minusMinutes(30), time.plusMinutes(30));
        when(lectureRepository.findLectureIdWithWriteLock(anyLong())).thenReturn(Optional.empty());
        lenient().when(lectureRepository.findLectureIdWithWriteLock(1L)).thenReturn(Optional.of(lecture));

        // user 더미 데이터 세팅
        user = new User(1L, "user1@gmail.com", "user1_password", "user1");
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.empty());
        lenient().when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));

        // 특강 신청 더미 데이터 세팅
        lectureRegistration = new LectureRegistration(lecture, user, time);
    }

    @Test
    public void registerLecture_LectureIdAbsent_EntityNotFoundException() {
        assertThrows(EntityNotFoundException.class,
            () -> lectureRegistrationService.registerLecture(1234, user.id()));
    }

    @Test
    public void registerLecture_UserIdAbsent_EntityNotFoundException() {
        assertThrows(EntityNotFoundException.class,
            () -> lectureRegistrationService.registerLecture(lecture.id(), 1234));
    }

    @Test
    public void registerLecture_AlreadyRegistered_IllegalArgumentException() {
        when(lectureRegistrationRepository.findByLectureIdAndUserId(lecture.id(), user.id()))
            .thenReturn(Optional.of(lectureRegistration));

        assertThrows(IllegalArgumentException.class,
            () -> lectureRegistrationService.registerLecture(lecture.id(), user.id()));
    }

    @Test
    public void registerLecture_FullyRegistered_IllegalStateException() {
        when(lectureRegistrationRepository.findByLectureIdAndUserId(lecture.id(), user.id()))
            .thenReturn(Optional.empty());
        when(lectureRegistrationRepository.countByLectureId(lecture.id()))
            .thenReturn(LectureRegistrationService.MAX_REGISTRATION);

        assertThrows(IllegalStateException.class,
            () -> lectureRegistrationService.registerLecture(lecture.id(), user.id()));
    }

    @Test
    public void registerLecture_LectureOutdated_IllegalArgumentException() {
        Lecture lecture2 = new Lecture(2L, user.id(), "l1", "u1", time.minusDays(2), time.minusSeconds(1));
        lenient().when(lectureRepository.findLectureIdWithWriteLock(lecture2.id())).thenReturn(Optional.of(lecture2));

        when(lectureRegistrationRepository.countByLectureId(lecture2.id()))
            .thenReturn(0);

        assertThrows(IllegalArgumentException.class,
            () -> lectureRegistrationService.registerLecture(lecture2.id(), user.id()));
    }

    @Test
    public void registerLecture_Success() {
        when(lectureRegistrationRepository.findByLectureIdAndUserId(lecture.id(), user.id()))
            .thenReturn(Optional.empty());
        when(lectureRegistrationRepository.countByLectureId(lecture.id()))
            .thenReturn(LectureRegistrationService.MAX_REGISTRATION - 1);

        assertDoesNotThrow(() -> lectureRegistrationService.registerLecture(lecture.id(), user.id()));
        verify(lectureRegistrationRepository, times(1)).save(anyLong(), anyLong(), any());
    }

    @Test
    public void getRegisteredLecturesByUserId_UserIdAbsent_EmptyList() {
        assertEquals(0, lectureRegistrationService.getRegisteredLecturesByUserId(1234).size());
    }

    @Test
    public void getRegisteredLecturesByUserId_Success() {
        when(lectureRegistrationRepository.findAllByUserId(user.id()))
            .thenReturn(List.of(new LectureRegistration(lecture, user, time)));
        assertEquals(1, lectureRegistrationService.getRegisteredLecturesByUserId(user.id()).size());
    }
}
